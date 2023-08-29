package com.jaskarth.ecotones.world.worldgen.gen;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.api.BiomeIdManager;
import com.jaskarth.ecotones.api.CaveBiome;
import com.jaskarth.ecotones.api.ModCompatRunner;
import com.jaskarth.ecotones.util.noise.OpenSimplexNoise;
import com.jaskarth.ecotones.world.worldgen.biome.cave.WrappedCaveBiome;
import com.jaskarth.ecotones.world.worldgen.layers.system.BiomeLayerSampler;

import java.util.*;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EcotonesBiomeSource extends BiomeSource implements CaveBiomeSource {
    public static Codec<EcotonesBiomeSource> CODEC =  RecordCodecBuilder.create((instance) -> {
        return instance.group(RegistryCodecs.entryList(RegistryKeys.BIOME, Biome.CODEC).fieldOf("biomes").forGetter((source) -> source.entries),
                Codec.LONG.fieldOf("seed").stable().forGetter((source) -> source.seed),
                Codec.BOOL.fieldOf("real").stable().forGetter((source) -> source.isReal))
                .apply(instance, instance.stable(EcotonesBiomeSource::new));
    });

    public final Registry<Biome> biomeRegistry;
    public final RegistryEntryList<Biome> entries;
    private final BiomeLayerSampler biomeSampler;
    private final long seed;

    private final OpenSimplexNoise caveBiomeNoise;
    private final boolean isReal;
    private final StampedLock lock = new StampedLock();

    public EcotonesBiomeSource(Registry<Biome> biomeRegistry, long seed, boolean isReal) {
        superSetup(isReal, biomeRegistry);
        this.biomeSampler = EcotonesBiomeLayers.build(seed);
        this.seed = seed;
        this.caveBiomeNoise = new OpenSimplexNoise(seed);
        this.isReal = isReal;
        this.entries = new SyntheticRegistryEntryList(biomeRegistry.streamEntries().map(RegistryEntry.class::cast));

        this.biomeRegistry = biomeRegistry;

        if (isReal) {
            long stamp = this.lock.writeLock();
            Ecotones.REGISTRY = this.biomeRegistry;
            ModCompatRunner.run();

            BiomeIdManager.clear();

            for (Biome biome : this.biomeRegistry) {
                BiomeIdManager.register(this.biomeRegistry.getKey(biome).get(), this.biomeRegistry.getRawId(biome));
            }

            this.lock.unlockWrite(stamp);
        }
    }

    public EcotonesBiomeSource(RegistryEntryList<Biome> registryEntries, long seed, boolean isReal) {
        this.biomeRegistry = null;
        this.entries = registryEntries;
        this.biomeSampler = EcotonesBiomeLayers.build(seed);
        this.seed = seed;
        this.caveBiomeNoise = new OpenSimplexNoise(seed);
        this.isReal = isReal;
    }

    @NotNull
    private static Stream<RegistryEntry<Biome>> superSetup(boolean real, Registry<Biome> biomeRegistry) {
        if (real) {
            ModCompatRunner.runEarly();
        }

        return BiomeGenData.LOOKUP
                .keySet()
                .stream()
                .map(k -> biomeRegistry.entryOf(k));
    }

    public RegistryEntry<Biome> getBiome(int biomeX, int biomeY, int biomeZ, MultiNoiseUtil.MultiNoiseSampler sampler) {
        if (!isReal) {
            throw new RuntimeException("Asked a phantom biome source to generate in the real sense!");
        }

        if (biomeRegistry == null) {
            throw new RuntimeException("There's no biome registry here for me to use!");
        }

        long stamp = this.lock.readLock();
        try {
            // TODO: no crash sampler
            return this.biomeRegistry.entryOf(this.biomeSampler.sampleKey(biomeX, biomeZ));
        } catch (Exception e) {
            e.printStackTrace();
            return new RegistryEntry.Direct<>(this.biomeRegistry.get(BiomeKeys.PLAINS));
        } finally {
            this.lock.unlockRead(stamp);
        }
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    protected Stream<RegistryEntry<Biome>> biomeStream() {
        return superSetup(isReal, biomeRegistry);
    }

    @Override
    public CaveBiome getCaveBiomeForNoiseGen(int x, int z) {
        double noise = this.caveBiomeNoise.sample(x / 192.0, z / 192.0);

        if (noise < -0.4) {
            return WrappedCaveBiome.DEEP_DARK;
        } else if (noise > 0.5) {
            return WrappedCaveBiome.LUSH;
        } else if (noise > 0.2 && noise < 0.5) {
            return WrappedCaveBiome.DRIPSTONE;
        }

        return CaveBiome.DEFAULT;
    }

    @Nullable
    public Pair<BlockPos, RegistryEntry<Biome>> locateBiome(
            BlockPos origin,
            int radius,
            int horizontalBlockCheckInterval,
            int verticalBlockCheckInterval,
            Predicate<RegistryEntry<Biome>> predicate,
            MultiNoiseUtil.MultiNoiseSampler noiseSampler,
            WorldView world
    ) {
        Set<RegistryEntry<Biome>> set = this.getBiomes().stream().filter(predicate).collect(Collectors.toUnmodifiableSet());
        if (set.isEmpty()) {
            return null;
        } else {
            int i = Math.floorDiv(radius, horizontalBlockCheckInterval);
            int[] is = MathHelper.stream(origin.getY(), world.getBottomY() + 1, world.getTopY(), verticalBlockCheckInterval).toArray();

            for(BlockPos.Mutable mutable : BlockPos.iterateInSquare(BlockPos.ORIGIN, i, Direction.EAST, Direction.SOUTH)) {
                int j = origin.getX() + mutable.getX() * horizontalBlockCheckInterval;
                int k = origin.getZ() + mutable.getZ() * horizontalBlockCheckInterval;
                int l = BiomeCoords.fromBlock(j);
                int m = BiomeCoords.fromBlock(k);

                for(int n : is) {
                    int o = BiomeCoords.fromBlock(n);
                    RegistryEntry<Biome> registryEntry = this.getBiome(l, o, m, noiseSampler);
                    if (set.contains(registryEntry)) {
                        return Pair.of(new BlockPos(j, n, k), registryEntry);
                    }
                }
            }

            return null;
        }
    }
}
