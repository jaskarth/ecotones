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
import supercoder79.ecotones.world.biome.EcotonesBiome;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.surface.EcotonesSurfaces;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.terraformersmc.terraform.biome.builder.DefaultFeature.*;

public class SwampBiomes {
    public static Map<Integer, Integer> Biome2SwampBiomeMap = new LinkedHashMap<>();

    public static Biome BOG_BIOME;
    public static Biome MIRE_BIOME;
    public static Biome MARSH_BIOME;
    public static Biome WETLAND_BIOME;

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
        BOG_BIOME = Registry.register(Registry.BIOME, new Identifier("ecotones", "bog"), template.builder()
                .grassColor(0x286620)
                .foliageColor(0x286620)
                .configureSurfaceBuilder(EcotonesSurfaces.PEAT_SWAMP_BUILDER, SurfaceBuilder.GRASS_CONFIG)
                .addDefaultFeature(PLAINS_TALL_GRASS)
                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(2, 0.5f, 1))))
                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(FeatureConfigHolder.REEDS_CONFIG).createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 2, 3))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SUGARCANE.configure(FeatureConfig.DEFAULT)
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.125f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.SEAGRASS.configure(new SeagrassFeatureConfig(20, 0.3D))
                        .createDecoratedFeature(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT)))
                .build());

        MIRE_BIOME = Registry.register(Registry.BIOME, new Identifier("ecotones", "mire"), template.builder()
                .grassColor(0x1f8212)
                .foliageColor(0x1f8212)
                .addDefaultFeature(PLAINS_TALL_GRASS)
                .configureSurfaceBuilder(EcotonesSurfaces.PEAT_SWAMP_BUILDER, SurfaceBuilder.GRASS_CONFIG)

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(2, 0.5f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(FeatureConfigHolder.REEDS_CONFIG)
                                .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 4, 6))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.TAIGA_GRASS_CONFIG)
                                .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 1, 2))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.SEAGRASS.configure(new SeagrassFeatureConfig(40, 0.4D))
                        .createDecoratedFeature(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT)))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SUGARCANE.configure(FeatureConfig.DEFAULT)
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.25f, 1))))

                .addTreeFeature(Feature.TREE.configure(DefaultBiomeFeatures.OAK_TREE_CONFIG), 1)
                .build());
        MARSH_BIOME = Registry.register(Registry.BIOME, new Identifier("ecotones", "marsh"), template.builder()
                .configureSurfaceBuilder(EcotonesSurfaces.PEAT_SWAMP_BUILDER, SurfaceBuilder.GRASS_CONFIG)
                .grassColor(0x549129)
                .foliageColor(0x549129)
                .addDefaultFeature(PLAINS_TALL_GRASS)

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 0.5f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(FeatureConfigHolder.REEDS_CONFIG)
                                .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 7, 9))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.TAIGA_GRASS_CONFIG)
                                .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 2, 4))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.SEAGRASS.configure(new SeagrassFeatureConfig(60, 0.45D))
                        .createDecoratedFeature(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT)))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SUGARCANE.configure(FeatureConfig.DEFAULT)
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.5f, 1))))

                .addTreeFeature(Feature.TREE.configure(DefaultBiomeFeatures.OAK_TREE_CONFIG), 2)
                .build());
        WETLAND_BIOME = Registry.register(Registry.BIOME, new Identifier("ecotones", "wetland"), template.builder()
                .configureSurfaceBuilder(EcotonesSurfaces.PEAT_SWAMP_BUILDER, SurfaceBuilder.GRASS_CONFIG)
                .grassColor(0x64b52b)
                .foliageColor(0x64b52b)
                .addDefaultFeature(PLAINS_TALL_GRASS)

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 0.5f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(FeatureConfigHolder.REEDS_CONFIG)
                                .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 10, 16))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.TAIGA_GRASS_CONFIG)
                                .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 4, 8))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.SEAGRASS.configure(new SeagrassFeatureConfig(90, 0.45D))
                        .createDecoratedFeature(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT)))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SUGARCANE.configure(FeatureConfig.DEFAULT)
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 0.5f, 1))))

                .addTreeFeature(Feature.TREE.configure(DefaultBiomeFeatures.OAK_TREE_CONFIG), 4)
                .build());

        Biome2SwampBiomeMap.put(Registry.BIOME.getRawId(HotBiomes.TROPICAL_RAINFOREST_BIOME), Registry.BIOME.getRawId(WETLAND_BIOME));
        Biome2SwampBiomeMap.put(Registry.BIOME.getRawId(HotBiomes.LUSH_FOREST_BIOME), Registry.BIOME.getRawId(WETLAND_BIOME));
        Biome2SwampBiomeMap.put(Registry.BIOME.getRawId(HotBiomes.DRY_FOREST_BIOME), Registry.BIOME.getRawId(MARSH_BIOME));
        Biome2SwampBiomeMap.put(Registry.BIOME.getRawId(HotBiomes.LUSH_SAVANNAH_BIOME), Registry.BIOME.getRawId(MARSH_BIOME));
        Biome2SwampBiomeMap.put(Registry.BIOME.getRawId(HotBiomes.TROPICAL_GRASSLAND_BIOME), Registry.BIOME.getRawId(MIRE_BIOME));
        Biome2SwampBiomeMap.put(Registry.BIOME.getRawId(HotBiomes.STEPPE_BIOME), Registry.BIOME.getRawId(MIRE_BIOME));
        Biome2SwampBiomeMap.put(Registry.BIOME.getRawId(HotBiomes.SCRUBLAND_BIOME), Registry.BIOME.getRawId(BOG_BIOME));
        Biome2SwampBiomeMap.put(Registry.BIOME.getRawId(HotBiomes.DESERT_BIOME), Registry.BIOME.getRawId(BOG_BIOME));

        TerraformBiomeSets.addSlimeSpawnBiomes(BOG_BIOME, MIRE_BIOME, MARSH_BIOME, WETLAND_BIOME);
        BiomeRegistries.registerNoBeachBiome(BOG_BIOME);
        BiomeRegistries.registerNoBeachBiome(MIRE_BIOME);
        BiomeRegistries.registerNoBeachBiome(MARSH_BIOME);
        BiomeRegistries.registerNoBeachBiome(WETLAND_BIOME);
    }
}
