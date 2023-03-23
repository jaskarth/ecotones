package supercoder79.ecotones.world.gen;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeIdManager;
import supercoder79.ecotones.api.CaveBiome;
import supercoder79.ecotones.api.ModCompatRunner;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.world.biome.cave.WrappedCaveBiome;
import supercoder79.ecotones.world.layers.system.BiomeLayerSampler;

import java.util.*;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EcotonesBiomeSource extends BiomeSource implements CaveBiomeSource {
    public static Codec<EcotonesBiomeSource> CODEC =  RecordCodecBuilder.create((instance) -> {
        return instance.group(RegistryOps.createRegistryCodec(Registry.BIOME_KEY).forGetter((source) -> source.biomeRegistry),
                Codec.LONG.fieldOf("seed").stable().forGetter((source) -> source.seed),
                Codec.BOOL.fieldOf("real").stable().forGetter((source) -> source.isReal))
                .apply(instance, instance.stable(EcotonesBiomeSource::new));
    });

    public final Registry<Biome> biomeRegistry;
    private final BiomeLayerSampler biomeSampler;
    private final long seed;

    private final OpenSimplexNoise caveBiomeNoise;
    private final boolean isReal;
    private final StampedLock lock = new StampedLock();

    public EcotonesBiomeSource(Registry<Biome> biomeRegistry, long seed, boolean isReal) {
        super(
                BiomeGenData.LOOKUP
                        .keySet()
                        .stream()
                        .map(k -> biomeRegistry.entryOf(k))
        );
        this.biomeRegistry = biomeRegistry;
        this.biomeSampler = EcotonesBiomeLayers.build(seed);
        this.seed = seed;
        this.caveBiomeNoise = new OpenSimplexNoise(seed);
        this.isReal = isReal;

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

//    @Override
//    public List<BiomeSource.IndexedFeatures> method_39525(List<RegistryEntry<Biome>> biomes, boolean bl) {
//        // Not relevant for ecotones
//        return new ArrayList<>();
//    }

    public RegistryEntry<Biome> getBiome(int biomeX, int biomeY, int biomeZ, MultiNoiseUtil.MultiNoiseSampler sampler) {
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

//    @Override
//    public BiomeSource withSeed(long seed) {
//        return new EcotonesBiomeSource(this.biomeRegistry, seed);
//    }

    @Override
    public CaveBiome getCaveBiomeForNoiseGen(int x, int z) {
        double noise = this.caveBiomeNoise.sample(x / 192.0, z / 192.0);
        if (noise > 0.6) {
            return WrappedCaveBiome.LUSH;
        } else if (noise > 0.2 && noise < 0.6) {
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
