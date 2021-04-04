package supercoder79.ecotones.world.biome.climatic;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

public class GraniteSpringsBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome HILLY;
    public static Biome MOUNTAINOUS;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "granite_springs"), new GraniteSpringsBiome(0.5f, 0.075f, 4.0, 0.94).build());
        HILLY = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "granite_springs_hilly"), new GraniteSpringsBiome(1f, 0.25f, 6.0, 0.90).build());
        MOUNTAINOUS = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "granite_springs_mountainous"), new GraniteSpringsBiome(1.75f, 0.5f, 7.0, 0.82).build());
        BiomeRegistries.registerMountains(INSTANCE, HILLY, MOUNTAINOUS);
        Climate.WARM_MILD.add(INSTANCE, 0.2);
        Climate.WARM_MODERATE.add(INSTANCE, 0.2);
    }

    protected GraniteSpringsBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(EcotonesSurfaces.GRANITE_SPRINGS, SurfaceBuilder.GRASS_CONFIG);
        this.precipitation(Biome.Precipitation.NONE);
        this.depth(depth);
        this.scale(scale);
        this.temperature(0.8F);
        this.downfall(0.5F);

        this.hilliness(hilliness);
        this.volatility(volatility);

        this.waterColor(4566514);
        this.waterFogColor(267827);

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
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(1.4))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(1.6))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.TREE.configure(FeatureConfigHolder.SMALL_PINE_CONFIG)
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SMALL_SPRUCE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.5))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.TREE.configure(FeatureConfigHolder.SPRUCE_TREE_CONFIG)
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(3.2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BARREN_PINE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.7))));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.PLACE_WATER.configure(FeatureConfigHolder.GRANITE_WATER_PATCH)
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .repeat(1));

        this.addFeature(GenerationStep.Feature.LAKES,
                EcotonesFeatures.GRANITE_SPRING.configure(FeatureConfig.DEFAULT)
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .applyChance(3));

        this.addFeature(GenerationStep.Feature.LAKES,
                EcotonesFeatures.WIDE_GRANITE_SPRING.configure(FeatureConfig.DEFAULT)
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .applyChance(10));

        this.addFeature(GenerationStep.Feature.LAKES,
                EcotonesFeatures.GRANITE_LAVA_SPRING.configure(FeatureConfig.DEFAULT)
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .applyChance(6));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.ROCK_SPIRE.configure(FeatureConfigHolder.GRANITE_SPIRE)
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .applyChance(4));
    }
}
