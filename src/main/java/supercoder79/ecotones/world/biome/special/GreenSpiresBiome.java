package supercoder79.ecotones.world.biome.special;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.heightprovider.BiasedToBottomHeightProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import supercoder79.ecotones.world.decorator.CountExtraDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesConfiguredFeature;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;
import net.minecraft.world.gen.treedecorator.CocoaBeansTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.world.biome.BiomeHelper;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.decorator.Spread32Decorator;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

public class GreenSpiresBiome extends EcotonesBiomeBuilder {
    public static final TreeFeatureConfig JUNGLE_TREE = new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.JUNGLE_LOG.getDefaultState()),
            new StraightTrunkPlacer(4, 8, 0),
            BlockStateProvider.of(Blocks.JUNGLE_LEAVES.getDefaultState()),
//            BlockStateProvider.of(Blocks.JUNGLE_SAPLING.getDefaultState()),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
            new TwoLayersFeatureSize(1, 0, 1))
            .decorators(ImmutableList.of(
                    new CocoaBeansTreeDecorator(0.2F),
                    TrunkVineTreeDecorator.INSTANCE,
                    LeavesVineTreeDecorator.INSTANCE)
            ).ignoreVines().build();

    public static Biome INSTANCE;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "green_spires"), new GreenSpiresBiome().build());
        BiomeRegistries.registerSpecialBiome(INSTANCE, id -> true);
        BiomeRegistries.registerBigSpecialBiome(INSTANCE, 400);
        BiomeRegistries.registerNoBeachBiome(INSTANCE);
    }


    protected GreenSpiresBiome() {
        this.surfaceBuilder(EcotonesSurfaces.GREEN_SPIRES_BUILDER, SurfaceBuilder.GRASS_CONFIG);

        this.depth(0.2f);
        this.scale(1.75F);
        this.temperature(1F);
        this.downfall(1F);

        this.precipitation(Biome.Precipitation.RAIN);
        this.category(Biome.Category.JUNGLE);

        this.skyColor(0xadc1cc);
        this.grassColor(0x73a859);
        this.foliageColor(0x74ad57);
        this.waterColor(0x74ad57);
        this.waterFogColor(0x73a859);

        this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD.value());

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .decorate(EcotonesDecorators.ROCKINESS.configure()));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.JUNGLE_PALM_TREE.configure(JUNGLE_TREE)
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(2, 0.5f, 1)))
                        .spreadHorizontally()
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesConfiguredFeature.wrap(Feature.TREE, (JUNGLE_TREE))
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(6, 0.5f, 1)))
                        .spreadHorizontally()
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, EcotonesConfiguredFeature.wrap(Feature.SEAGRASS, (new ProbabilityConfig(0.75f)))
                .decorate(HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG))
                .spreadHorizontally()
                .repeat(152));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, EcotonesConfiguredFeature.wrap(Feature.VINES, (FeatureConfig.DEFAULT))
                .range(BiasedToBottomHeightProvider.create(YOffset.fixed(64), YOffset.fixed(192), 32))
                .spreadHorizontally()
                .repeat(2)
                .repeat(256)
        );

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.JUNGLE_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(8))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(NoiseThresholdCountPlacementModifier.of(-0.8D, 10, 20)));

        DefaultBiomeFeatures.addDefaultDisks(this.getGenerationSettings());
        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        //DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addAmethystGeodes(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);
    }
}
