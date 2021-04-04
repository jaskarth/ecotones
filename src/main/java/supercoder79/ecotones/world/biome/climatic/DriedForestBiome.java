package supercoder79.ecotones.world.biome.climatic;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.world.biome.BiomeHelper;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.RockFeatureConfig;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public class DriedForestBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome HILLY;
    public static Biome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "dried_forest"), new DriedForestBiome(0.5f, 0.075f, 2.8, 0.88).build());
        HILLY = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "dried_forest_hilly"), new DriedForestBiome(1f, 0.5f, 4.2, 0.83).build());
        MOUNTAINOUS = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "dried_forest_mountainous"), new DriedForestBiome(1.75f, 0.8f, 7, 0.75).build());
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);
        Climate.HOT_DRY.add(INSTANCE, 0.3);
        Climate.HOT_MODERATE.add(INSTANCE, 0.1);
    }

    protected DriedForestBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);
        this.precipitation(Biome.Precipitation.NONE);
        this.depth(depth);
        this.scale(scale);
        this.temperature(1.6F);
        this.downfall(0.15F);

        this.hilliness(hilliness);
        this.volatility(volatility);

        this.addStructureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);
        this.addStructureFeature(ConfiguredStructureFeatures.MINESHAFT);
        this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD);

        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        DefaultBiomeFeatures.addPlainsTallGrass(this.getGenerationSettings());
        DefaultBiomeFeatures.addPlainsFeatures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultDisks(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(2.0))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.25))));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
                        .decorate(EcotonesDecorators.LARGE_ROCK.configure(new ChanceDecoratorConfig(6))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .decorate(EcotonesDecorators.ROCKINESS.configure(DecoratorConfig.DEFAULT)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BARREN_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(1.85))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DESERTIFY_SOIL.configure(FeatureConfig.DEFAULT)
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(7, 0.5f, 3))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.ONLY_TALL_GRASS_CONFIG)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally());

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SCRUBLAND_CONFIG)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .decorate(Decorator.COUNT_NOISE.configure(new CountNoiseDecoratorConfig(-0.8D, 10, 14))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.ROSEMARY.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.ROSEMARY.configure(new ShrubDecoratorConfig(0.1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(1.25))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(1.25))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.TREE.configure(FeatureConfigHolder.DRY_STEPPE_TREE)
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.4))));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .applyChance(12));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.ROCK_SPIRE.configure(FeatureConfigHolder.STONE_SPIRE)
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .applyChance(12));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
    }
}
