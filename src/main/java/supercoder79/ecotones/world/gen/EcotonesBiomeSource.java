package supercoder79.ecotones.world.gen;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;
import supercoder79.ecotones.Ecotones;

public class EcotonesBiomeSource extends BiomeSource {
    public static Codec<EcotonesBiomeSource> CODEC =  RecordCodecBuilder.create((instance) -> {
        return instance.group(RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((source) -> source.biomeRegistry),
                Codec.LONG.fieldOf("seed").stable().forGetter((source) -> source.seed))
                .apply(instance, instance.stable(EcotonesBiomeSource::new));
    });

    public final Registry<Biome> biomeRegistry;
    private final BiomeLayerSampler biomeSampler;
    private final long seed;

    public EcotonesBiomeSource(Registry<Biome> biomeRegistry, long seed) {
        super(BiomeGenData.LOOKUP.keySet().stream().map((k) -> () -> biomeRegistry.getOrThrow(k)));
        this.biomeRegistry = biomeRegistry;
        this.biomeSampler = EcotonesBiomeLayers.build(seed);
        this.seed = seed;

        for (Biome biome : this.biomeRegistry) {
            int id = this.biomeRegistry.getRawId(biome);
            if (!BuiltinBiomes.BY_RAW_ID.containsKey(id)) {
                BuiltinBiomes.BY_RAW_ID.put(id, this.biomeRegistry.getKey(biome).get());
            }
        }

        Ecotones.REGISTRY = this.biomeRegistry;
    }

    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        try {
            // TODO: no crash sampler
            return this.biomeSampler.sample(this.biomeRegistry, biomeX, biomeZ);
        } catch (Exception e) {
            e.printStackTrace();
            return this.biomeRegistry.get(BiomeKeys.PLAINS);
        }
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new EcotonesBiomeSource(this.biomeRegistry, seed);
    }
}
