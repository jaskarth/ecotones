package supercoder79.ecotones.world.gen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.util.TopologicalSorts;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.apache.commons.lang3.mutable.MutableInt;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeIdManager;
import supercoder79.ecotones.api.CaveBiome;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.ModCompat;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.world.biome.cave.LimestoneCaveBiome;
import supercoder79.ecotones.world.layers.system.BiomeLayerSampler;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EcotonesBiomeSource extends BiomeSource implements CaveBiomeSource {
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

        BiomeIdManager.clear();

        for (Biome biome : this.biomeRegistry) {
            System.out.println(this.biomeRegistry.getKey(biome).get());
            BiomeIdManager.register(this.biomeRegistry.getKey(biome).get(), this.biomeRegistry.getRawId(biome));
        }
    }

    @Override
    public List<BiomeSource.class_6827> method_39525(List<Biome> biomes, boolean bl) {
        // Not relevant for ecotones
        return new ArrayList<>();
    }

    public Biome getBiome(int biomeX, int biomeY, int biomeZ, MultiNoiseUtil.MultiNoiseSampler sampler) {
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
