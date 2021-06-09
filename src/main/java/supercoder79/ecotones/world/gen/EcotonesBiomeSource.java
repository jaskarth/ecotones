package supercoder79.ecotones.world.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.CaveBiome;
import supercoder79.ecotones.api.ModCompat;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.world.biome.cave.LimestoneCaveBiome;

public class EcotonesBiomeSource extends BiomeSource implements CaveBiomeSource{
    public static Codec<EcotonesBiomeSource> CODEC =  RecordCodecBuilder.create((instance) -> {
        return instance.group(RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((source) -> source.biomeRegistry),
                Codec.LONG.fieldOf("seed").stable().forGetter((source) -> source.seed))
                .apply(instance, instance.stable(EcotonesBiomeSource::new));
    });

    public final Registry<Biome> biomeRegistry;
    private final BiomeLayerSampler biomeSampler;
    private final long seed;

    private final OpenSimplexNoise caveBiomeNoise;

    public EcotonesBiomeSource(Registry<Biome> biomeRegistry, long seed) {
        super(BiomeGenData.LOOKUP.keySet().stream().map((k) -> () -> biomeRegistry.getOrThrow(k)));
        this.biomeRegistry = biomeRegistry;
        this.biomeSampler = EcotonesBiomeLayers.build(seed);
        this.seed = seed;
        this.caveBiomeNoise = new OpenSimplexNoise(seed);

        Ecotones.REGISTRY = this.biomeRegistry;
        ModCompat.run();

        for (Biome biome : this.biomeRegistry) {
            int id = this.biomeRegistry.getRawId(biome);
            if (!BuiltinBiomes.BY_RAW_ID.containsKey(id)) {
                BuiltinBiomes.BY_RAW_ID.put(id, this.biomeRegistry.getKey(biome).get());
            }
        }
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

    @Override
    public CaveBiome getCaveBiomeForNoiseGen(int x, int z) {
        return this.caveBiomeNoise.sample(x / 256.0, z / 256.0) > 0.2 ? LimestoneCaveBiome.INSTANCE : CaveBiome.DEFAULT;
    }
}
