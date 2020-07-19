package supercoder79.ecotones.world.biome.base;

import com.terraformersmc.terraform.util.TerraformBiomeSets;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.world.biome.EcotonesBiome;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

import static com.terraformersmc.terraform.biome.builder.DefaultFeature.*;

public class SwampBiomes {
    public static Biome SWAMP_BIOME;
    private static EcotonesBiome.Template template = new EcotonesBiome.Template(EcotonesBiome.builder()
            .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
            .precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST)
            .depth(-0.125F)
            .scale(0.075F)
            .temperature(1.4F)
            .downfall(1.5F)
            .waterColor(0x2e8a07)
            .waterFogColor(0x2e8a07)

            .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    Feature.TREE.configure(FeatureConfigHolder.DEAD_LARGE_OAK)
                            .createDecoratedFeature(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.08))))

            .addCustomFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                    EcotonesFeatures.PLACE_WATER.configure(FeatureConfig.DEFAULT)
                            .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP.configure(new CountDecoratorConfig(5))))

            .addDefaultFeatures(LAND_CARVERS, STRUCTURES, DUNGEONS, MINEABLES, ORES,
                    DEFAULT_FLOWERS, DEFAULT_MUSHROOMS, FOREST_GRASS, DEFAULT_VEGETATION, SPRINGS, FROZEN_TOP_LAYER)
            .addStructureFeature(DefaultBiomeFeatures.STRONGHOLD)
            .addCustomFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                    Feature.LAKE.configure(new SingleStateFeatureConfig(Blocks.WATER.getDefaultState())).createDecoratedFeature(Decorator.WATER_LAKE.configure(new ChanceDecoratorConfig(1))))
            .addStructureFeature(DefaultBiomeFeatures.NORMAL_MINESHAFT)
            .addDefaultSpawnEntries());

    public static void init() {
        SWAMP_BIOME = Registry.register(Registry.BIOME, new Identifier("ecotones", "swamp"), template.builder()
                .grassColor(0x286620)
                .foliageColor(0x286620)
                .configureSurfaceBuilder(EcotonesSurfaces.PEAT_SWAMP_BUILDER, SurfaceBuilder.GRASS_CONFIG)
                .addDefaultFeature(PLAINS_TALL_GRASS)
                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(2, 0.5f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(FeatureConfigHolder.REEDS_CONFIG)
                                .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 4, 7))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SUGARCANE.configure(FeatureConfig.DEFAULT)
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 0.5f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SUGARCANE.configure(FeatureConfig.DEFAULT)
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.125f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.BRANCHING_OAK.configure(TreeType.RARE_LARGE_CLUSTERED_OAK.config)
                                .createDecoratedFeature(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_LARGE_CLUSTERED_OAK.config.decorationData)))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.TREE.configure(FeatureConfigHolder.DEAD_LARGE_OAK)
                                .createDecoratedFeature(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.25))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.LILY_PAD_CONFIG)
                                .createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(6))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.SEAGRASS.configure(new SeagrassFeatureConfig(20, 0.3D))
                        .createDecoratedFeature(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT)))

                .addCustomFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                        Feature.FOREST_ROCK.configure(new ForestRockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
                                .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(15))))

                .addCustomFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                        Feature.FOREST_ROCK.configure(new ForestRockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 0))
                                .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(9))))

                .build());
        TerraformBiomeSets.addSlimeSpawnBiomes(SWAMP_BIOME);
        BiomeRegistries.registerNoBeachBiome(SWAMP_BIOME);
        BiomeRegistries.registerNoRiverBiome(SWAMP_BIOME);
    }
}
