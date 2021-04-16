package supercoder79.ecotones.world.biome.climatic;

import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountNoiseDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.feature.*;
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
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.structure.EcotonesConfiguredStructures;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

// TODO: needs rename?
public class BirchLakesBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome HILLY;
    public static Biome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "birch_lakes"), new BirchLakesBiome(0.25F, 0.075F, 6.0, 0.92).build());
        HILLY = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "birch_lakes_hilly"), new BirchLakesBiome(0.5F, 0.225F, 7.0, 0.88).build());
        MOUNTAINOUS = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "birch_lakes_mountainous"), new BirchLakesBiome(1.2F, 0.625F, 8.02, 0.84).build());
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);
        Climate.WARM_VERY_HUMID.add(INSTANCE, 0.2);
        Climate.WARM_HUMID.add(INSTANCE, 0.3);
        Climate.WARM_MILD.add(INSTANCE, 0.2);
    }

    public BirchLakesBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(EcotonesSurfaces.BIRCH_LAKES, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(0.5f);
        this.downfall(0.7f);
        this.precipitation(Biome.Precipitation.RAIN);

        this.hilliness(hilliness);
        this.volatility(volatility);

        this.waterColor(0x407fd6);

        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        DefaultBiomeFeatures.addPlainsTallGrass(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultDisks(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());

        this.getGenerationSettings().feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.GLOW_LICHEN);

        this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.ORE.configure(
                new OreFeatureConfig(new BlockMatchRuleTest(Blocks.STONE), Blocks.DIRT.getDefaultState(), 33))
                .rangeOf(256)
                .spreadHorizontally()
                .repeat(10));

        this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.ORE.configure(
                new OreFeatureConfig(new BlockMatchRuleTest(Blocks.STONE), Blocks.GRAVEL.getDefaultState(), 33))
                .rangeOf(256)
                .spreadHorizontally()
                .repeat(8));

        this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.ORE.configure(
                new OreFeatureConfig(new BlockMatchRuleTest(Blocks.STONE), Blocks.ANDESITE.getDefaultState(), 33))
                .rangeOf(80)
                .spreadHorizontally()
                .repeat(10));

        this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.ORE.configure(
                new OreFeatureConfig(new BlockMatchRuleTest(Blocks.STONE), Blocks.GRANITE.getDefaultState(), 33))
                .rangeOf(80)
                .spreadHorizontally()
                .repeat(10));

        this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.ORE.configure(
                new OreFeatureConfig(new BlockMatchRuleTest(Blocks.STONE), Blocks.DIORITE.getDefaultState(), 33))
                .rangeOf(80)
                .spreadHorizontally()
                .repeat(10));

        this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD);
        this.addStructureFeature(EcotonesConfiguredStructures.CAMPFIRE_BIRCH);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .decorate(EcotonesDecorators.ROCKINESS.configure(DecoratorConfig.DEFAULT)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.15))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.55))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.6))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(6.6))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.05))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.3))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .decorate(Decorator.COUNT_NOISE.configure(new CountNoiseDecoratorConfig(-0.8D, 12, 16))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SMALL_LILAC)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .repeat(1));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.LAVENDER)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .applyChance(3));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.WILDFLOWERS)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.MOSS)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .repeat(3));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.PLACE_WATER.configure(FeatureConfigHolder.DIORITE_WATER_PATCH)
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .applyChance(8));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.PLACE_WATER.configure(FeatureConfigHolder.DIORITE_WATER_POOL_PATCH)
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .applyChance(10));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.ROCK_SPIRE.configure(FeatureConfigHolder.DIORITE_SPIRE)
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .applyChance(4));

        this.addFeature(GenerationStep.Feature.LAKES,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .applyChance(8));
    }

}
