package supercoder79.ecotones.world.biome.base.warm;

import net.minecraft.block.Blocks;
import net.minecraft.structure.SavannaVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import supercoder79.ecotones.world.biome.BiomeAssociations;
import supercoder79.ecotones.world.decorator.*;
import supercoder79.ecotones.world.features.mc.RandomPatchFeatureConfig;
import supercoder79.ecotones.world.river.deco.CommonRiverDecorations;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.world.biome.BiomeHelper;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.RockFeatureConfig;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public class CoolScrublandBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome THICKET;
    public static Biome HILLY;
    public static Biome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "cool_scrubland"), new CoolScrublandBiome(0.5f, 0.075f, 1.6, 1, false).build());
        THICKET = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "cool_scrubland_thicket"), new CoolScrublandBiome(0.5f, 0.075f, 1.6, 1, true).build());
        HILLY = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "cool_scrubland_hilly"), new CoolScrublandBiome(1f, 0.5f, 4.2, 0.94, false).build());
        MOUNTAINOUS = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "cool_scrubland_mountainous"), new CoolScrublandBiome(1.75f, 0.8f, 8, 0.87, false).build());
        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 3);
        BiomeRegistries.registerBiomeVariants(INSTANCE, INSTANCE, THICKET);
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);

        Climate.WARM_VERY_DRY.add(INSTANCE, 1);

        BiomeRegistries.registerRiverDecorator(INSTANCE, CommonRiverDecorations::buildDesertLushness);
    }

    protected CoolScrublandBiome(float depth, float scale, double hilliness, double volatility, boolean thicket) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);
        this.precipitation(Biome.Precipitation.NONE);
        this.depth(depth);
        this.scale(scale);
        this.temperature(1.2F);
        this.downfall(0.225F);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.associate(BiomeAssociations.SAVANNA_LIKE);
//        this.category(Biome.Category.SAVANNA);

//        this.addStructureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_DESERT);
//        this.addStructureFeature(ConfiguredStructureFeatures.MINESHAFT);
//         this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD.value());
//        this.addStructureFeature(ConfiguredStructureFeatures.PILLAGER_OUTPOST);
//        this.addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(() -> SavannaVillageData.STRUCTURE_POOLS, 6)));

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

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
                        .decorate(EcotonesDecorators.LARGE_ROCK.configure(new ChanceDecoratorConfig(8))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.CACTUS.getDefaultState())).tries(10).cannotProject().build())
                        .decorate(new SpreadDoubleDecorator()).decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(40));

        if (thicket) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.BIG_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                            .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(1.8))));
        }

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.15))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                                .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CACTI.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BARREN_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.75))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(3.5))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .decorate(EcotonesDecorators.ROCKINESS.configure()));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.ONLY_TALL_GRASS_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally());

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.COOL_SCRUBLAND_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(NoiseThresholdCountPlacementModifier.of(-0.8D, 5, 10)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BIG_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.ABOVE_QUALITY.configure()));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DESERTIFY_SOIL.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.5f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.FLAME_LILY)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(6));

        // TODO: sand patches

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);
    }
}
