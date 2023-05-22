package supercoder79.ecotones.world.biome.climatic;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import supercoder79.ecotones.world.biome.BiomeAssociations;
import supercoder79.ecotones.world.biome.EarlyBiomeRegistry;
import supercoder79.ecotones.world.features.EcotonesConfiguredFeature;
import supercoder79.ecotones.world.features.mc.RandomPatchFeatureConfig;
import supercoder79.ecotones.world.decorator.Spread32Decorator;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.util.state.DeferredBlockStateProvider;
import supercoder79.ecotones.util.compat.FloralisiaCompat;
import supercoder79.ecotones.world.biome.BiomeHelper;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.*;

public class ClayBasinBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome LAKEBED;
    public static Biome WET;
    public static Biome HILLY;
    public static Biome MOUNTAINOUS;

    public static void init() {
        INSTANCE = EarlyBiomeRegistry.register(new Identifier("ecotones", "clay_basin"), new ClayBasinBiome(0.2f, 0.125f, 2.8, 0.94).build());
        LAKEBED = EarlyBiomeRegistry.register(new Identifier("ecotones", "clay_basin_lakebed"), new ClayBasinBiome(0.2f, 0.125f, 1.2, 0.98, 8, 1).build());
        WET = EarlyBiomeRegistry.register(new Identifier("ecotones", "clay_basin_wet"), new ClayBasinBiome(0.1f, 0.125f, 2.8, 0.94, 3, 10).build());
        HILLY = EarlyBiomeRegistry.register(new Identifier("ecotones", "clay_basin_hilly"), new ClayBasinBiome(0.4f, 0.5f, 4.2, 0.89).build());
        MOUNTAINOUS = EarlyBiomeRegistry.register(new Identifier("ecotones", "clay_basin_mountainous"), new ClayBasinBiome(0.75f, 0.8f, 7, 0.82).build());

        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 4);
        BiomeRegistries.registerBiomeVariants(INSTANCE, LAKEBED, WET);
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);
        Climate.HOT_DRY.add(INSTANCE, 0.1);
        Climate.HOT_MODERATE.add(INSTANCE, 0.1);

        BiomeRegistries.registerSlimeSpawnBiomes(INSTANCE, LAKEBED, WET, HILLY, MOUNTAINOUS);
    }

    protected ClayBasinBiome(float depth, float scale, double hilliness, double volatility) {
        this(depth, scale, hilliness, volatility, 3, 1);
    }

    protected ClayBasinBiome(float depth, float scale, double hilliness, double volatility, int clayCount, int waterCount) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(1.2f);
        this.downfall(0.065f);
        this.precipitation(Biome.Precipitation.NONE);
        this.associate(BiomeAssociations.LONELY_SAVANNA_LIKE);
//        this.category(Biome.Category.SAVANNA);

        this.hilliness(hilliness);
        this.volatility(volatility);

        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        DefaultBiomeFeatures.addPlainsTallGrass(this.getGenerationSettings());
        //DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addAmethystGeodes(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultDisks(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());
        DefaultBiomeFeatures.addSavannaGrass(this.getGenerationSettings());
        DefaultBiomeFeatures.addSavannaTallGrass(this.getGenerationSettings());
        BiomeHelper.addDefaultFeatures(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .decorate(EcotonesDecorators.ROCKINESS.configure()));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(1.25))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesConfiguredFeature.wrap(Feature.TREE, FeatureConfigHolder.DRY_STEPPE_TREE)
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.ROSEMARY.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.ROSEMARY.configure(new ShrubDecoratorConfig(0.1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BARREN_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(1.25))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.ONLY_TALL_GRASS_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SCRUBLAND_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(NoiseThresholdCountPlacementModifier.of(-0.8D, 4, 6)));

        this.addFeature(GenerationStep.Feature.LAKES,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(8));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.GROUND_PATCH.configure(new PatchFeatureConfig(Blocks.CLAY.getDefaultState(), Blocks.GRASS_BLOCK, UniformIntProvider.create(2, 6)))
                        .spreadHorizontally()
                        .repeat(clayCount));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.PLACE_WATER.configure(FeatureConfigHolder.GRASS_WATER_PATCH)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(waterCount));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DUCKWEED.configure(new DuckweedFeatureConfig(UniformIntProvider.create(64, 96), UniformIntProvider.create(10, 14)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DUCKWEED.configure(new DuckweedFeatureConfig(UniformIntProvider.create(8, 16), UniformIntProvider.create(4, 6)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(64, 96), true, UniformIntProvider.create(10, 14)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(12, 16), true, UniformIntProvider.create(10, 14)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(2)
                        .repeat(3));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.FLAME_LILY)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4));

        if (FloralisiaCompat.isEnabled()) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new DeferredBlockStateProvider(FloralisiaCompat.anastasia())).tries(12).build())
                            .decorate(new Spread32Decorator())
                            .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                            .spreadHorizontally()
                            .applyChance(3));
        }

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
    }
}
