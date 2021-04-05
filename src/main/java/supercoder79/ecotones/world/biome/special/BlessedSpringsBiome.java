package supercoder79.ecotones.world.biome.special;

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
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

public class BlessedSpringsBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "blessed_springs"), new BlessedSpringsBiome().build());
        BiomeRegistries.registerSpecialBiome(INSTANCE, id -> true);
    }

    protected BlessedSpringsBiome() {
        this.category(Biome.Category.PLAINS);
        this.surfaceBuilder(EcotonesSurfaces.BLESSED_SPRINGS_BUILDER, SurfaceBuilder.GRASS_CONFIG);

        this.depth(0.25F);
        this.scale(0.0125F);
        this.temperature(1.6F);
        this.downfall(0.4F);

        this.precipitation(Biome.Precipitation.RAIN);

        this.waterColor(4445678);
        this.waterFogColor(329011);

        this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD);

        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .repeat(2)
                        .spreadHorizontally()
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .decorate(Decorator.COUNT_NOISE.configure(new CountNoiseDecoratorConfig(-0.8D, 6, 8))));

//        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                Feature.TREE.configure(DefaultBiomeFeatures.PINE_TREE_CONFIG)
//                        .decorate(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.66f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.TREE.configure(FeatureConfigHolder.SPRUCE_TREE_CONFIG)
                        .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.33f, 1)))
                        .spreadHorizontally()
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.166f, 1)))
                        .spreadHorizontally()
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE)));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                Feature.LAKE.configure(new SingleStateFeatureConfig(Blocks.LAVA.getDefaultState())).decorate(Decorator.LAVA_LAKE.configure(new ChanceDecoratorConfig(16))));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS, Feature.LAKE.configure(new SingleStateFeatureConfig(Blocks.WATER.getDefaultState())).decorate(Decorator.WATER_LAKE.configure(new ChanceDecoratorConfig(1))));

        //no spawns in the blessed springs
    }
}
