package supercoder79.ecotones.world.biome.climatic;

import net.minecraft.block.Blocks;
import net.minecraft.structure.PlainsVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.world.biome.BiomeHelper;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.*;
import supercoder79.ecotones.world.structure.EcotonesConfiguredStructures;

public class BluebellWoodBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome CLEARING;
    public static Biome FLATS;
    public static Biome HILLY;
    public static Biome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "bluebell_wood"), new BluebellWoodBiome(0.3F, 0.05F, false, false).build());
        FLATS = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "bluebell_wood_flats"), new BluebellWoodBiome(0.3F, 0F, false, true).build());
        CLEARING = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "bluebell_wood_clearing"), new BluebellWoodBiome(0.3F, 0.0125F, true, false).build());
        HILLY = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "bluebell_wood_hilly"), new BluebellWoodBiome(0.6F, 0.225F, false, false).build());
        MOUNTAINOUS = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "bluebell_wood_mountainous"), new BluebellWoodBiome(0.8F, 0.625F, false, false).build());

        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 4);
        BiomeRegistries.registerBiomeVariants(INSTANCE, INSTANCE, FLATS, CLEARING);
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);

        Climate.WARM_MILD.add(INSTANCE, 0.2);
        Climate.WARM_HUMID.add(INSTANCE, 0.3);
    }

    public BluebellWoodBiome(float depth, float scale, boolean clearing, boolean flat) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(0.9f);
        this.downfall(0.7f);
        this.precipitation(Biome.Precipitation.RAIN);
        this.category(Biome.Category.FOREST);

        this.hilliness(flat ? 1 : 3);
        this.volatility(flat ? 1.1 : 0.9);

        this.addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS, 7)));
        this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD);
        this.addStructureFeature(EcotonesConfiguredStructures.CAMPFIRE_OAK);

        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        DefaultBiomeFeatures.addPlainsTallGrass(this.getGenerationSettings());
        DefaultBiomeFeatures.addPlainsFeatures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addAmethystGeodes(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultDisks(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .decorate(EcotonesDecorators.ROCKINESS.configure(DecoratorConfig.DEFAULT)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.BLUEBELL_CONFIG)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .decorate(Decorator.COUNT_NOISE.configure(new CountNoiseDecoratorConfig(-0.8D, 12, 18))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(clearing ? 0.8 : 0.6))));

        if (clearing) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.STRAIGHT_OAK.configure(new OakTreeFeatureConfig(7, 14).branchCount(4).branchLength(4).randomBranchLength(4))
                            .decorate(EcotonesDecorators.TREE_DECORATOR.configure(new TreeGenerationConfig.DecorationData(0.2, 0, 0, false))));
        } else {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.STRAIGHT_OAK.configure(new OakTreeFeatureConfig(7, 4).branchCount(2).branchLength(2).randomBranchLength(3))
                            .decorate(EcotonesDecorators.TREE_DECORATOR.configure(new TreeGenerationConfig.DecorationData(1.3, 0, 0, false))));
        }

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(64, 96), true, UniformIntProvider.create(10, 14)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(4)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(12, 16), true, UniformIntProvider.create(10, 14)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(2)
                        .repeat(4));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(clearing ? 1 : 0.85))));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.BEEHIVES.configure(FeatureConfig.DEFAULT)
                        .spreadHorizontally()
                        .applyChance(80));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(UniformIntProvider.create(64, 96), true, UniformIntProvider.create(10, 14)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(4)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(UniformIntProvider.create(6, 14), true, UniformIntProvider.create(3, 5)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(UniformIntProvider.create(16, 64), false, UniformIntProvider.create(8, 14)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(128));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DUCKWEED.configure(new DuckweedFeatureConfig(UniformIntProvider.create(64, 96), UniformIntProvider.create(10, 14)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(6)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DUCKWEED.configure(new DuckweedFeatureConfig(UniformIntProvider.create(8, 16), UniformIntProvider.create(4, 6)))
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(4));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.MUSHROOMS)
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.LILY_PAD.getDefaultState()),
                        SimpleBlockPlacer.INSTANCE).tries(10).build())
                        .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.CLOVER)
                        .spreadHorizontally()
                        .repeat(clearing ? 4 : 2)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(clearing ? 0.1 : 0.075))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BLUEBERRY_BUSH.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.BLUEBERRY_BUSH.configure(new ShrubDecoratorConfig(0.05))));

        DefaultBiomeFeatures.addForestFlowers(this.getGenerationSettings());

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);
    }
}
