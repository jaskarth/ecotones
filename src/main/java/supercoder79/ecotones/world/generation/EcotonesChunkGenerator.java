package supercoder79.ecotones.world.generation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.ZombieSiegeManager;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import supercoder79.ecotones.util.ImprovedChunkRandom;
import supercoder79.ecotones.util.noise.OctaveNoiseSampler;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.world.biome.EcotonesBiome;

import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.IntStream;

public class EcotonesChunkGenerator extends BaseEcotonesChunkGenerator {
    public static final Codec<EcotonesChunkGenerator> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BiomeSource.field_24713.fieldOf("biome_source").forGetter((generator) -> generator.biomeSource),
                    Codec.LONG.fieldOf("seed").stable().forGetter((generator) -> generator.seed))
                    .apply(instance, instance.stable(EcotonesChunkGenerator::new)));

    private static final float[] BIOME_WEIGHT_TABLE = Util.make(new float[25], (table) -> {
        for(int x = -2; x <= 2; ++x) {
            for(int z = -2; z <= 2; ++z) {
                float weight = 10.0F / MathHelper.sqrt((float)(x * x + z * z) + 0.2F);
                table[x + 2 + (z + 2) * 5] = weight;
            }
        }
    });

    private final OctaveNoiseSampler<OpenSimplexNoise> soilDrainageNoise;

    //TODO: convert this to soil depth somehow
    private final OctaveNoiseSampler<OpenSimplexNoise> soilRockinessNoise;

    private final OctavePerlinNoiseSampler hillinessNoise;
    private final OctaveNoiseSampler<OpenSimplexNoise> scaleNoise;

    private final PhantomSpawner phantomSpawner = new PhantomSpawner();
    private final PillagerSpawner pillagerSpawner = new PillagerSpawner();
    private final CatSpawner catSpawner = new CatSpawner();
    private final ZombieSiegeManager zombieSiegeManager = new ZombieSiegeManager();
    private final long seed;

    public EcotonesChunkGenerator(BiomeSource biomeSource, long seed) {
        super(biomeSource, seed);
        this.seed = seed;
        this.hillinessNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));

        this.scaleNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, this.random, 8, 256, 0.2, -0.2);
        soilDrainageNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, this.random, 4, 1536, 1.7, 1.7);
        soilRockinessNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, this.random, 4, 1024, 2, -2);
    }

    @Override
    protected double[] computeNoiseRange(int x, int z) {
        double[] buffer = new double[3];
        float weightedScale = 0.0F;
        float weightedDepth = 0.0F;
        double weightedHilliness = 0.0F;
        double weightedVolatility = 0.0F;
        float weights = 0.0F;
        int seaLevel = this.getSeaLevel();
        float centerDepth = this.biomeSource.getBiomeForNoiseGen(x, seaLevel, z).getDepth();

        for(int x1 = -2; x1 <= 2; ++x1) {
            for(int z1 = -2; z1 <= 2; ++z1) {
                Biome biome = this.biomeSource.getBiomeForNoiseGen(x + x1, seaLevel, z + z1);
                //vanilla attributes
                float depth = biome.getDepth();
                float scale = biome.getScale();

                //hilliness and volatility
                double hilliness = 1;
                double volatility = 1;
                if (biome instanceof EcotonesBiome) {
                    hilliness = ((EcotonesBiome)biome).getHilliness();
                    volatility = ((EcotonesBiome)biome).getVolatility();
                }

                float weight = BIOME_WEIGHT_TABLE[x1 + 2 + (z1 + 2) * 5] / (depth + 2.0F);
                if (biome.getDepth() > centerDepth) {
                    weight /= 2.0F;
                }

                weightedScale += scale * weight;
                weightedDepth += depth * weight;
                weightedHilliness += hilliness * weight;
                weightedVolatility += volatility * weight;
                weights += weight;
            }
        }

        weightedScale /= weights;
        weightedDepth /= weights;
        weightedHilliness /= weights;
        weightedVolatility /= weights;

        weightedScale = weightedScale * 0.9F + 0.1F;
        weightedDepth = (weightedDepth * 4.0F - 1.0F) / 8.0F;
        buffer[0] = (double)weightedDepth + (this.sampleNoise(x, z) * weightedHilliness);
        buffer[1] = weightedScale + scaleNoise.sample(x, z);
        buffer[2] = weightedVolatility;
        return buffer;
    }

    @Override
    protected void sampleNoiseColumn(double[] buffer, int x, int z, double horizontalScale, double verticalScale, double horizontalStretch, double verticalStretch, int interpolationSize, int interpolateTowards) {
        double[] noiseRange = this.computeNoiseRange(x, z);
        double depth = noiseRange[0];
        double scale = noiseRange[1];
        double volatility = noiseRange[2];

        double upperInterpolationStart = this.upperInterpolationStart();
        double lowerInterpolationStart = this.lowerInterpolationStart();

        for(int y = 0; y < this.getNoiseSizeY(); ++y) {
            double noise = this.sampleNoise(x, y, z, horizontalScale, verticalScale, horizontalStretch, verticalStretch) + (scaleNoise.sample(x, y, z) * 5);
            //modify the noise for special reasons
            Biome biome = this.biomeSource.getBiomeForNoiseGen(x, y, z);
            if (biome instanceof EcotonesBiome) {
                noise = ((EcotonesBiome)biome).modifyNoise(x, y, z, noise);
            }

            //calculate volatility
            noise -= this.computeNoiseFalloff(depth, scale, y) * volatility;

            if ((double)y > upperInterpolationStart) {
                noise = MathHelper.clampedLerp(noise, (double)interpolateTowards, ((double)y - upperInterpolationStart) / (double)interpolationSize);
            } else if ((double)y < lowerInterpolationStart) {
                noise = MathHelper.clampedLerp(noise, -30.0D, (lowerInterpolationStart - (double)y) / (lowerInterpolationStart - 1.0D));
            }

            buffer[y] = noise;
        }

    }

    @Override
    protected double computeNoiseFalloff(double depth, double scale, int y) {
        double falloff = ((double)y - (8.5D + depth * 8.5D / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / scale;
        if (falloff < 0.0D) {
            falloff *= 4.0D;
        }

        return falloff;
    }

    @Override
    protected void sampleNoiseColumn(double[] buffer, int x, int z) {
        this.sampleNoiseColumn(buffer, x, z, 684.4119873046875D, 684.4119873046875D, 8.555149841308594D, 4.277574920654297D, 3, -10);
    }

    @Override
    public void populateEntities(ChunkRegion region) {
        int i = region.getCenterChunkX();
        int j = region.getCenterChunkZ();
        Biome biome = region.getBiome((new ChunkPos(i, j)).getCenterBlockPos());
        ChunkRandom chunkRandom = new ChunkRandom();
        chunkRandom.setPopulationSeed(region.getSeed(), i << 4, j << 4);
        SpawnHelper.populateEntities(region, biome, i, j, chunkRandom);
    }

    @Override
    public int getSpawnHeight() {
        return 63;
    }

    //height additions - makes the terrain a bit hillier
    private double sampleNoise(int x, int y) {
        double noise = this.hillinessNoise.sample((double)(x * 200), 10.0D, (double)(y * 200), 1.0D, 0.0D, true) * 65535.0D / 8000.0D;
        if (noise < 0.0D) {
            noise = -noise * 0.3D;
        }

        noise = noise * 3.0D - 2.0D;
        if (noise < 0.0D) {
            noise /= 28.0D;
        } else {
            if (noise > 1.0D) {
                noise = 1.0D;
            }

            noise /= 40.0D;
        }

        return noise;
    }

    // stupid entity spawning
    public List<Biome.SpawnEntry> getEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos) {
        if (accessor.method_28388(pos, true, StructureFeature.SWAMP_HUT).hasChildren()) {
            if (group == SpawnGroup.MONSTER) {
                return StructureFeature.SWAMP_HUT.getMonsterSpawns();
            }

            if (group == SpawnGroup.CREATURE) {
                return StructureFeature.SWAMP_HUT.getCreatureSpawns();
            }
        }

        if (group == SpawnGroup.MONSTER) {
            if (accessor.method_28388(pos, false, StructureFeature.PILLAGER_OUTPOST).hasChildren()) {
                return StructureFeature.PILLAGER_OUTPOST.getMonsterSpawns();
            }

            if (accessor.method_28388(pos, false, StructureFeature.MONUMENT).hasChildren()) {
                return StructureFeature.MONUMENT.getMonsterSpawns();
            }

            if (accessor.method_28388(pos, true, StructureFeature.FORTRESS).hasChildren()) {
                return StructureFeature.FORTRESS.getMonsterSpawns();
            }
        }

        return super.getEntitySpawnList(biome, accessor, group, pos);
    }

    public void spawnEntities(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        this.phantomSpawner.spawn(world, spawnMonsters, spawnAnimals);
        this.pillagerSpawner.spawn(world, spawnMonsters, spawnAnimals);
        this.catSpawner.spawn(world, spawnMonsters, spawnAnimals);
        this.zombieSiegeManager.spawn(world, spawnMonsters, spawnAnimals);
    }

    @Override
    protected Codec<? extends ChunkGenerator> method_28506() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new EcotonesChunkGenerator(new EcotonesBiomeSource(seed), seed);
    }

    // Vanilla random improvements
    @Override
    public void generateFeatures(ChunkRegion world, StructureAccessor structureAccessor) {
        int centerX = world.getCenterChunkX();
        int centerZ = world.getCenterChunkZ();
        int topX = centerX * 16;
        int topZ = centerZ * 16;
        BlockPos pos = new BlockPos(topX, 0, topZ);
        Biome biome = this.biomeSource.getBiomeForNoiseGen((centerX << 2) + 2, 2, (centerZ << 2) + 2);
        ImprovedChunkRandom random = new ImprovedChunkRandom();
        long populationSeed = random.setPopulationSeed(world.getSeed(), topX, topZ, biome.getScale() + scaleNoise.sample(topX + 8, topZ + 8));

        for (GenerationStep.Feature step : GenerationStep.Feature.values()) {
            try {
                biome.generateFeatureStep(step, structureAccessor, this, world, populationSeed, random, pos);
            } catch (Exception var18) {
                CrashReport crashReport = CrashReport.create(var18, "Biome decoration");
                crashReport.addElement("Generation")
                        .add("CenterX", centerX)
                        .add("CenterZ", centerZ)
                        .add("Step", step)
                        .add("Seed", populationSeed)
                        .add("Biome", Registry.BIOME.getId(biome));
                throw new CrashException(crashReport);
            }
        }
    }

    @Override
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
        BiomeAccess biomeAccess = access.withSource(this.biomeSource);
        ImprovedChunkRandom chunkRandom = new ImprovedChunkRandom();
        ChunkPos chunkPos = chunk.getPos();
        int j = chunkPos.x;
        int k = chunkPos.z;
        Biome biome = this.biomeSource.getBiomeForNoiseGen(chunkPos.x << 2, 0, chunkPos.z << 2);
        BitSet bitSet = ((ProtoChunk)chunk).method_28510(carver);

        for(int l = j - 8; l <= j + 8; ++l) {
            for(int m = k - 8; m <= k + 8; ++m) {
                List<ConfiguredCarver<?>> list = biome.getCarversForStep(carver);
                ListIterator listIterator = list.listIterator();

                while(listIterator.hasNext()) {
                    int n = listIterator.nextIndex();
                    ConfiguredCarver<?> configuredCarver = (ConfiguredCarver)listIterator.next();
                    chunkRandom.setCarverSeed(seed + (long)n, l, m);
                    if (configuredCarver.shouldCarve(chunkRandom, l, m)) {
                        configuredCarver.carve(chunk, biomeAccess::getBiome, chunkRandom, this.getSeaLevel(), l, m, j, k, bitSet);
                    }
                }
            }
        }
    }

    // data getters

    public OctaveNoiseSampler<OpenSimplexNoise> getSoilDrainageNoise() {
        return soilDrainageNoise;
    }

    public OctaveNoiseSampler<OpenSimplexNoise> getSoilRockinessNoise() {
        return soilRockinessNoise;
    }

    public double getSoilQualityAt(double x, double z) {
        return 1 - Math.abs(soilDrainageNoise.sample(x, z));
    }
}
