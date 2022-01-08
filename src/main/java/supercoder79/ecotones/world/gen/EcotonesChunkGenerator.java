package supercoder79.ecotones.world.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.*;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.CarvingMask;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;
import net.minecraft.world.gen.random.RandomSeed;
import net.minecraft.world.gen.random.SimpleRandom;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.CaveBiome;
import supercoder79.ecotones.util.BiomeCache;
import supercoder79.ecotones.util.ImprovedChunkRandom;
import supercoder79.ecotones.util.LayerRandom;
import supercoder79.ecotones.util.noise.OctaveNoiseSampler;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.world.blend.CachingBlender;
import supercoder79.ecotones.world.blend.LinkedBiomeWeightMap;
import supercoder79.ecotones.world.carver.EcotonesCarverContext;
import supercoder79.ecotones.world.data.DataFunction;
import supercoder79.ecotones.world.data.DataHolder;
import supercoder79.ecotones.world.data.EcotonesData;
import supercoder79.ecotones.world.features.EcotonesFeatures;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class EcotonesChunkGenerator extends BaseEcotonesChunkGenerator implements DataHolder {
    public static final Codec<EcotonesChunkGenerator> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> generator.biomeSource),
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
    private final OctaveNoiseSampler<OpenSimplexNoise> soilPhNoise;
    private final OctaveNoiseSampler<OpenSimplexNoise> grassNoise;

    //TODO: convert this to soil depth somehow
    private final OctaveNoiseSampler<OpenSimplexNoise> soilRockinessNoise;

    private final OctavePerlinNoiseSampler hillinessNoise;
    private final OctaveNoiseSampler<OpenSimplexNoise> scaleNoise;
    private final long seed;
    private final Optional<Registry<Biome>> registry;
    private final Map<Identifier, DataFunction> data = new HashMap<>();
    private final CachingBlender blender = new CachingBlender(0.24, 6, 4);
    private final NoiseChunkGenerator shim;

    public EcotonesChunkGenerator(BiomeSource biomeSource, long seed) {
        super(biomeSource, seed);
        this.seed = seed;
        this.hillinessNoise = OctavePerlinNoiseSampler.createLegacy(this.random, IntStream.rangeClosed(-15, 0));
        if (biomeSource instanceof EcotonesBiomeSource) {
            this.registry = Optional.of(((EcotonesBiomeSource)biomeSource).biomeRegistry);
        } else {
            this.registry = Optional.empty();
        }

        this.shim = new NoiseChunkGenerator(BuiltinRegistries.NOISE_PARAMETERS, biomeSource, seed, ChunkGeneratorSettings::getInstance);

        this.scaleNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, this.random, 4, 256, 0.2, -0.2);
        this.soilDrainageNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, this.random, 2, 1600, 1.75, 0.75);
        this.soilRockinessNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, this.random, 4, 1024, 2, -2);
        this.soilPhNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, this.random, 2, 1600, 0.9, 0.9);
        this.grassNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, this.random, 2, 800, 0.9, 0.9);

        this.data.put(EcotonesData.SOIL_QUALITY, (x, z) -> MathHelper.clamp((this.soilDrainageNoise.sample(x, z) / 2) + 0.5, 0, 1));
        this.data.put(EcotonesData.SOIL_DRAINAGE, this.soilDrainageNoise::sample);
        this.data.put(EcotonesData.SOIL_ROCKINESS, this.soilRockinessNoise::sample);
        this.data.put(EcotonesData.SOIL_PH, this.soilPhNoise::sample);
        this.data.put(EcotonesData.GRASS_NOISE, this.grassNoise::sample);
    }

    protected RegistryKey<Biome> key(Biome biome) {
        return this.registry.map(registry -> registry.getKey(biome)).get().orElse(BiomeKeys.PLAINS);
    }

    @Override
    protected double[] computeNoiseData(int x, int z) {
        double[] buffer = new double[3];
        double weightedScale = 0.0F;
        double weightedDepth = 0.0F;
        double weightedHilliness = 0.0F;
        double weightedVolatility = 0.0F;
        float weights = 0.0F;

        BiomeCache cache = biomeCache.get();

        BiomeGenData centerData = BiomeGenData.LOOKUP.getOrDefault(key(cache.get(x, z)), BiomeGenData.DEFAULT);

        double centerDepth = centerData.depth();

        boolean isMountain = false;
        for (int x1 = -2; x1 <= 2; ++x1) {
            for (int z1 = -2; z1 <= 2; ++z1) {
                Biome biome = cache.get(x + x1, z + z1);
                //vanilla attributes
                double depth = 0.1;
                double scale = 0.05;

                //hilliness and volatility
                double hilliness = 1;
                double volatility = 1;

                if (this.registry.isPresent()) {
                    RegistryKey<Biome> key = this.registry.get().getKey(biome).get();
                    if (BiomeRegistries.MOUNTAIN_BIOMES.containsKey(key)) {
                        isMountain = true;
                    }
                    BiomeGenData data = BiomeGenData.LOOKUP.getOrDefault(key, BiomeGenData.DEFAULT);

                    depth = data.depth();
                    scale = data.scale();
                    hilliness = data.hilliness();
                    volatility = data.volatility();
                }

                double weight = BIOME_WEIGHT_TABLE[x1 + 2 + (z1 + 2) * 5] / (depth + 2.0F);
                if (depth > centerDepth) {
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

        // Scattered blender for mountains- makes it much much smoother
        if (isMountain) {
            int rad = (int) Math.ceil(this.blender.getInternal().getInternalBlendRadius());
            double count = 0;

            // TODO: rivers are still broken
            for (int x1 = -2; x1 <= 2; x1++) {
                for (int z1 = -2; z1 <= 2; z1++) {
                    RegistryKey<Biome> key = this.registry.get().getKey(cache.get(x + x1, z + z1)).get();

                    if (BiomeRegistries.MOUNTAIN_BIOMES.containsKey(key)) {
                        count++;
                    }
                }
            }

            double amt = count / (5 * 5);

            LinkedBiomeWeightMap weightMap = this.blender.blend(this.seed, (x >> 2) << 2, (z >> 2) << 2, (x0, z0) -> biomeSource.getBiome((int) x0, 0, (int) z0, this.getMultiNoiseSampler()));
            int idx = ((z & 3) * 4) + (x & 3);

            double nWeightedScale = 0;
            double nWeightedDepth = 0;
            double nWeightedHilliness = 0;
            double nWeightedVolatility = 0;

            double scatteredWeight = 0;
            for (LinkedBiomeWeightMap entry = weightMap; entry != null; entry = entry.getNext()) {
                Biome biome = entry.getBiome();
                RegistryKey<Biome> key = this.registry.get().getKey(biome).get();
                double weight = entry.getWeights()[idx] * BiomeRegistries.MOUNTAIN_BIOMES.getOrDefault(key, 1.0);
                scatteredWeight += weight;

                BiomeGenData data = BiomeGenData.LOOKUP.getOrDefault(key, BiomeGenData.DEFAULT);

                nWeightedScale += data.scale() * weight;
                nWeightedDepth += data.depth() * weight;


                nWeightedHilliness += data.hilliness() * weight;
                nWeightedVolatility += data.volatility() * weight;
            }

            nWeightedScale /= scatteredWeight;
            nWeightedDepth /= scatteredWeight;
            nWeightedHilliness /= scatteredWeight;
            nWeightedVolatility /= scatteredWeight;

            // Interpolate the 2 interpolated weights. Yes, I know.
            weightedScale = MathHelper.clampedLerp(weightedScale, nWeightedScale, amt);
            weightedDepth = MathHelper.clampedLerp(weightedDepth, nWeightedDepth, amt);
            weightedHilliness = MathHelper.clampedLerp(weightedHilliness, nWeightedHilliness, amt);
            weightedVolatility = MathHelper.clampedLerp(weightedVolatility, nWeightedVolatility, amt);
        }

        weightedScale = weightedScale * 0.9F + 0.1F;
        weightedDepth = (weightedDepth * 4.0F - 1.0F) / 8.0F;
        buffer[0] = weightedDepth + (this.sampleHilliness(x, z) * weightedHilliness);
        buffer[1] = weightedScale + scaleNoise.sample(x, z);
        buffer[2] = weightedVolatility;
        return buffer;
    }

    @Override
    protected void sampleNoiseColumn(double[] buffer, int x, int z, double horizontalScale, double verticalScale, double horizontalStretch, double verticalStretch, int interpolationSize, int interpolateTowards) {
        double[] noiseData = this.computeNoiseData(x, z);
        double depth = noiseData[0];
        double scale = noiseData[1];
        double volatility = noiseData[2];

        double upperInterpolationStart = this.upperInterpolationStart();
        double lowerInterpolationStart = this.lowerInterpolationStart();

        for(int y = 0; y < this.getNoiseSizeY(); ++y) {
            double noise = this.sampleTerrainNoise(x, y, z, horizontalScale, verticalScale, horizontalStretch, verticalStretch) + (scaleNoise.sample(x, y, z) * 5);

            //calculate volatility
            noise -= this.computeNoiseFalloff(depth, scale, y) * volatility;

            if ((double)y > upperInterpolationStart) {
                noise = MathHelper.clampedLerp(noise, interpolateTowards, ((double)y - upperInterpolationStart) / (double)interpolationSize);
            } else if ((double)y < lowerInterpolationStart) {
                // Never occurs, only exists for vanilla consistency
                noise = MathHelper.clampedLerp(noise, -30.0D, (lowerInterpolationStart - (double)y) / (lowerInterpolationStart - 1.0D));
            }

            //modify the noise for special reasons
            Biome biome = biomeCache.get().get(x, z);
//            if (biome instanceof EcotonesBiome) {
//                noise = ((EcotonesBiome)biome).modifyNoise(x, y, z, noise);
//            }

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
    protected void fillNoiseColumn(double[] buffer, int x, int z) {
        this.sampleNoiseColumn(buffer, x, z, 684.4119873046875D, 684.4119873046875D, 8.555149841308594D, 4.277574920654297D, 3, -10);
    }

    @Override
    public void populateEntities(ChunkRegion region) {
        ChunkPos chunkPos = region.getCenterPos();
        Biome biome = region.getBiome(chunkPos.getStartPos());
        ChunkRandom chunkRandom = new ChunkRandom(new SimpleRandom(0));
        chunkRandom.setPopulationSeed(region.getSeed(), chunkPos.getStartX(), chunkPos.getStartZ());
        SpawnHelper.populateEntities(region, biome, chunkPos, chunkRandom);
    }

    @Override
    public int getWorldHeight() {
        return 256;
    }

    //height additions - makes the terrain a bit hillier
    private double sampleHilliness(int x, int y) {
        double noise = this.hillinessNoise.sample((x * 200), 10.0D, (y * 200), 1.0D, 0.0D, true) * 65535.0D / 8000.0D;
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

    public Pool<SpawnSettings.SpawnEntry> getEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos) {
        if (!accessor.hasStructureReferences(pos)) {
            return super.getEntitySpawnList(biome, accessor, group, pos);
        } else {
            if (accessor.getStructureContaining(pos, StructureFeature.SWAMP_HUT).hasChildren()) {
                if (group == SpawnGroup.MONSTER) {
                    return SwampHutFeature.MONSTER_SPAWNS;
                }

                if (group == SpawnGroup.CREATURE) {
                    return SwampHutFeature.CREATURE_SPAWNS;
                }
            }

            if (group == SpawnGroup.MONSTER) {
                if (accessor.getStructureAt(pos, StructureFeature.PILLAGER_OUTPOST).hasChildren()) {
                    return PillagerOutpostFeature.MONSTER_SPAWNS;
                }

                if (accessor.getStructureAt(pos, StructureFeature.MONUMENT).hasChildren()) {
                    return OceanMonumentFeature.MONSTER_SPAWNS;
                }

                if (accessor.getStructureContaining(pos, StructureFeature.FORTRESS).hasChildren()) {
                    return NetherFortressFeature.MONSTER_SPAWNS;
                }
            }

            return (group == SpawnGroup.UNDERGROUND_WATER_CREATURE || group == SpawnGroup.AXOLOTLS) && accessor.getStructureAt(pos, StructureFeature.MONUMENT).hasChildren() ? SpawnSettings.EMPTY_ENTRY_POOL : super.getEntitySpawnList(biome, accessor, group, pos);
        }
    }

    @Override
    public int getSeaLevel() {
        return 63;
    }

    @Override
    public int getMinimumY() {
        return 0;
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new EcotonesChunkGenerator(this.populationSource.withSeed(seed), seed);
    }

    public MultiNoiseUtil.MultiNoiseSampler getMultiNoiseSampler() {
        return (i, j, k) -> {
            return MultiNoiseUtil.createNoiseValuePoint(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        };
    }

    @Override
    public void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor) {
        ChunkPos chunkPos = ((ChunkRegion)world).getCenterPos();
        int startX = chunkPos.getStartX();
        int startZ = chunkPos.getStartZ();
        BlockPos pos = new BlockPos(startX, 0, startZ);
        Biome biome = this.biomeSource.getBiome((chunkPos.x << 2) + 2, 2, (chunkPos.z << 2) + 2, this.getMultiNoiseSampler());
        ImprovedChunkRandom random = new ImprovedChunkRandom(new SimpleRandom(0));
        long populationSeed = random.setPopulationSeed(world.getSeed(), startX, startZ, BiomeGenData.LOOKUP.getOrDefault(key(biome), BiomeGenData.DEFAULT).scale() + scaleNoise.sample(startX + 8, startZ + 8));

        EcotonesFeatures.ORE_VEIN.generate(new FeatureContext<>(Optional.empty(), world, this, random, pos, DefaultFeatureConfig.INSTANCE));
        try {
            generateFeatureStep(biome, structureAccessor, this, (ChunkRegion) world, populationSeed, random, pos);
        } catch (Exception ex) {
            CrashReport crashReport = CrashReport.create(ex, "Biome decoration");
            crashReport.addElement("Generation")
                    .add("CenterX", chunkPos.x)
                    .add("CenterZ", chunkPos.x)
                    .add("Seed", populationSeed)
                    .add("Biome", biome);
            throw new CrashException(crashReport);
        }

        if (this.biomeSource instanceof CaveBiomeSource) {
            CaveBiome caveBiome = ((CaveBiomeSource)this.biomeSource).getCaveBiomeForNoiseGen((chunkPos.x << 2) + 2, (chunkPos.z << 2) + 2);
            for (ConfiguredFeature<?, ?> feature : caveBiome.getFeatures()) {
                feature.generate(world, this, random, pos);
            }
        }
    }

    public void generateFeatureStep(Biome biome, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, ChunkRegion region, long populationSeed, ChunkRandom random, BlockPos origin) {
        List<List<Supplier<PlacedFeature>>> list = biome.getGenerationSettings().getFeatures();
        Registry<PlacedFeature> registry = region.getRegistryManager().get(Registry.PLACED_FEATURE_KEY);
        Registry<StructureFeature<?>> registry2 = region.getRegistryManager().get(Registry.STRUCTURE_FEATURE_KEY);
        int i = GenerationStep.Feature.values().length;

        for(int j = 0; j < i; ++j) {
            int k = 0;
            if (structureAccessor.shouldGenerateStructures()) {
//                for(StructureFeature<?> structureFeature : biome.getOrDefault(j, Collections.emptyList())) {
//                    random.setDecoratorSeed(populationSeed, k, j);
//                    int l = ChunkSectionPos.getSectionCoord(origin.getX());
//                    int m = ChunkSectionPos.getSectionCoord(origin.getZ());
//                    int n = ChunkSectionPos.getBlockCoord(l);
//                    int o = ChunkSectionPos.getBlockCoord(m);
//                    Supplier<String> supplier = () -> registry2.getKey(structureFeature).map(Object::toString).orElseGet(structureFeature::toString);
//
//                    try {
//                        int p = region.getBottomY() + 1;
//                        int q = region.getTopY() - 1;
//                        region.method_36972(supplier);
//                        structureAccessor.getStructuresWithChildren(ChunkSectionPos.from(origin), structureFeature)
//                                .forEach(
//                                        structureStart -> structureStart.generateStructure(
//                                                region, structureAccessor, chunkGenerator, random, new BlockBox(n, p, o, n + 15, q, o + 15), new ChunkPos(l, m)
//                                        )
//                                );
//                    } catch (Exception var24) {
//                        CrashReport crashReport = CrashReport.create(var24, "Feature placement");
//                        crashReport.addElement("Feature").add("Description", supplier::get);
//                        throw new CrashException(crashReport);
//                    }
//
//                    ++k;
//                }
            }

            if (list.size() > j) {
                for(Supplier<PlacedFeature> supplier2 : list.get(j)) {
                    PlacedFeature configuredFeature = supplier2.get();
                    Supplier<String> supplier3 = () -> registry.getKey(configuredFeature).map(Object::toString).orElseGet(configuredFeature::toString);
                    random.setDecoratorSeed(populationSeed, k, j);

                    try {
                        region.setCurrentlyGeneratingStructureName(supplier3);
                        configuredFeature.generate(region, chunkGenerator, random, origin);
                    } catch (Exception var25) {
                        CrashReport crashReport2 = CrashReport.create(var25, "Feature placement");
                        crashReport2.addElement("Feature").add("Description", supplier3::get);
                        throw new CrashException(crashReport2);
                    }

                    ++k;
                }
            }
        }

        region.setCurrentlyGeneratingStructureName(null);
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver generationStep) {
        BiomeAccess biomeAccess2 = biomeAccess.withSource((x, y, z) -> {
            return this.populationSource.getBiome(x, y, z, this.getMultiNoiseSampler());
        });
        ChunkRandom chunkRandom = new ChunkRandom(new AtomicSimpleRandom(RandomSeed.getSeed()));
        ChunkPos chunkPos = chunk.getPos();
        CarverContext carverContext = new EcotonesCarverContext(this, this.shim, chunkRegion.getRegistryManager(), chunkRegion);
        CarvingMask carvingMask = ((ProtoChunk)chunk).getOrCreateCarvingMask(generationStep);
        AquiferSampler aquiferSampler = AquiferSampler.seaLevel((x, y, z) -> new AquiferSampler.FluidLevel(0, this.defaultFluid));

        for(int j = -8; j <= 8; ++j) {
            for(int k = -8; k <= 8; ++k) {
                ChunkPos chunkPos2 = new ChunkPos(chunkPos.x + j, chunkPos.z + k);
                Chunk chunk2 = chunkRegion.getChunk(chunkPos2.x, chunkPos2.z);
                GenerationSettings generationSettings = chunk2.setBiomeIfAbsent(() -> {
                    return this.populationSource.getBiome(BiomeCoords.fromBlock(chunkPos2.getStartX()), 0, BiomeCoords.fromBlock(chunkPos2.getStartZ()), this.getMultiNoiseSampler());
                }).getGenerationSettings();
                List<Supplier<ConfiguredCarver<?>>> list = generationSettings.getCarversForStep(generationStep);
                ListIterator listIterator = list.listIterator();

                while(listIterator.hasNext()) {
                    int l = listIterator.nextIndex();
                    ConfiguredCarver<?> configuredCarver = (ConfiguredCarver)((Supplier)listIterator.next()).get();
                    chunkRandom.setCarverSeed(seed + (long)l, chunkPos2.x, chunkPos2.z);
                    if (configuredCarver.shouldCarve(chunkRandom)) {
                        Objects.requireNonNull(biomeAccess2);
                        configuredCarver.carve(carverContext, chunk, biomeAccess2::getBiome, chunkRandom, aquiferSampler, chunkPos2, carvingMask);
                    }
                }
            }
        }
    }

//    @Override
//    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
//        BiomeAccess biomeAccess = access.withSource(this.populationSource);
//        ImprovedChunkRandom chunkRandom = new ImprovedChunkRandom();
//        ChunkPos chunkPos = chunk.getPos();
//        int j = chunkPos.x;
//        int k = chunkPos.z;
//        GenerationSettings generationSettings = this.populationSource.getBiome(BiomeCoords.fromBlock(chunkPos.getStartX()), 0, BiomeCoords.fromBlock(chunkPos.getStartZ())).getGenerationSettings();
//        BitSet bitSet = ((ProtoChunk)chunk).getOrCreateCarvingMask(carver);
//
//        CarverContext carverContext = new CarverContext(this, chunk);
//        AquiferSampler aquiferSampler = AquiferSampler.seaLevel(this.getSeaLevel(), this.defaultFluid);
//
//        // TODO: clean up, 1.17 made it so
//        for(int l = j - 8; l <= j + 8; ++l) {
//            for(int m = k - 8; m <= k + 8; ++m) {
//                ChunkPos chunkPos2 = new ChunkPos(l, m);
//
//                List<Supplier<ConfiguredCarver<?>>> list = generationSettings.getCarversForStep(carver);
//                ListIterator listIterator = list.listIterator();
//
//                while(listIterator.hasNext()) {
//                    int n = listIterator.nextIndex();
//                    ConfiguredCarver<?> configuredCarver = (ConfiguredCarver)((Supplier)listIterator.next()).get();
//                    chunkRandom.setCarverSeed(seed + (long)n, l, m);
//                    if (configuredCarver.shouldCarve(chunkRandom)) {
//                        configuredCarver.carve(carverContext, chunk, biomeAccess::getBiome, chunkRandom, aquiferSampler, chunkPos2, bitSet);
//                    }
//                }
//            }
//        }
//
//    }

    // data getters
    @Deprecated
    public OctaveNoiseSampler<OpenSimplexNoise> getSoilDrainageNoise() {
        return this.soilDrainageNoise;
    }

    @Deprecated
    public OctaveNoiseSampler<OpenSimplexNoise> getSoilRockinessNoise() {
        return this.soilRockinessNoise;
    }

    @Deprecated
    public double getSoilQualityAt(double x, double z) {
        return MathHelper.clamp((this.soilDrainageNoise.sample(x, z) / 2) + 0.5, 0, 1);
    }

    @Deprecated
    public double getSoilPhAt(double x, double z) {
        return this.soilPhNoise.sample(x, z);
    }

    @Override
    public DataFunction get(Identifier id) {
        return this.data.getOrDefault(id, DataFunction.NOOP);
    }

    // Tree trait data

    public long getTraits(int chunkX, int chunkZ, int salt) {
        LayerRandom random = new LayerRandom(this.seed);
        random.setPosSeed(chunkX, chunkZ);
        int aX = random.nextInt(5) - 2;
        int aZ = random.nextInt(5) - 2;

        random.setPosSeed((chunkX + aX) >> 7, (chunkZ + aZ) >> 7, salt);

        return random.nextLong();
    }
}
