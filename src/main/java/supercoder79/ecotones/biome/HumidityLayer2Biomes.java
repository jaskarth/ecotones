package supercoder79.ecotones.biome;

import com.terraformersmc.terraform.biome.builder.TerraformBiome;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NoiseHeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.MineshaftFeature;
import net.minecraft.world.gen.feature.MineshaftFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import supercoder79.ecotones.features.EcotonesFeatures;
import supercoder79.ecotones.features.config.FeatureConfigHolder;
import supercoder79.ecotones.features.config.ShrubConfig;
import supercoder79.ecotones.layers.MountainLayer;
import supercoder79.ecotones.surface.EcotonesSurfaces;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.terraformersmc.terraform.biome.builder.DefaultFeature.*;

public class HumidityLayer2Biomes {
    public static Map<Double, Integer> Humidity2BiomeMap = new LinkedHashMap<>();


    public static Biome DESERT_BIOME;
    public static Biome DESERT_SCRUB_BIOME;
    public static Biome DESERT_WOODLAND_BIOME;
    public static Biome VERY_DRY_FOREST_BIOME;
    public static Biome DRY_FOREST_BIOME;
    public static Biome MOIST_FOREST_BIOME;
    public static Biome WET_FOREST_BIOME;
    public static Biome RAINFOREST_BIOME;

    private static TerraformBiome.Template template = new TerraformBiome.Template(TerraformBiome.builder()
            .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.SAND_CONFIG)
            .precipitation(Biome.Precipitation.NONE).category(Biome.Category.FOREST)
            .depth(0.5F)
            .scale(0.075F)
            .temperature(1.2F)
            .downfall(0F)
            .waterColor(4159204)
            .waterFogColor(329011)
            .addDefaultFeatures(LAND_CARVERS, STRUCTURES, DUNGEONS, MINEABLES, ORES, DISKS,
                    DEFAULT_FLOWERS, DEFAULT_MUSHROOMS, FOREST_GRASS, DEFAULT_VEGETATION, SPRINGS, FROZEN_TOP_LAYER)
            .addStructureFeature(Feature.STRONGHOLD)
            .addStructureFeature(Feature.MINESHAFT, new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL))
            .addDefaultSpawnEntries()
            .addSpawnEntry(new Biome.SpawnEntry(EntityType.WOLF, 5, 4, 4)));

    public static void init() {
        DESERT_BIOME = register(new Identifier("ecotones", "cool_desert"), template.builder()
                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.CACTUS_CONFIG).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(10)))));
        DESERT_SCRUB_BIOME = register(new Identifier("ecotones", "cool_scrubland"), template.builder()
                .configureSurfaceBuilder(EcotonesSurfaces.DESERT_SCRUB_BUILDER, SurfaceBuilder.GRASS_CONFIG)
                .temperature(1.2F)
                .downfall(0.2F)
                .addDefaultFeature(PLAINS_TALL_GRASS)

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.CACTUS_CONFIG).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(40))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new ShrubConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(4, 0.5f, 3))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(FeatureConfigHolder.MOSTLY_SHORT_GRASS_CONFIG).createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 5, 10))))
                .addTreeFeature(Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.DEAD_BUSH_CONFIG), 5));
        DESERT_WOODLAND_BIOME = register( new Identifier("ecotones", "cool_steppe"), template.builder()
                .temperature(1.2F)
                .downfall(0.35F)
                .addDefaultFeature(PLAINS_TALL_GRASS)

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new ShrubConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(2, 0.5f, 3))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(FeatureConfigHolder.MOSTLY_SHORT_GRASS_CONFIG).createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 5, 10))))

                .addTreeFeature(Feature.ACACIA_TREE.configure(DefaultBiomeFeatures.ACACIA_TREE_CONFIG), 1)
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG));
        VERY_DRY_FOREST_BIOME = register(new Identifier("ecotones", "prairie"), template.builder()
                .temperature(1F)
                .downfall(0.4F)
                .scale(0.025f)
                .foliageColor(0xabcf59)
                .grassColor(0xabcf59)

                .addDefaultFeatures(PLAINS_TALL_GRASS, PLAINS_FEATURES)
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new ShrubConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 0.5f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(FeatureConfigHolder.MOSTLY_SHORT_GRASS_CONFIG).createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 20, 30)))));

        DRY_FOREST_BIOME = register( new Identifier("ecotones", "boreal_forest"), template.builder()
                .temperature(0.8F)
                .downfall(0.5F)
                .scale(0.15f)
                .addDefaultFeatures(PLAINS_TALL_GRASS)
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new ShrubConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(2, 0.25f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG).createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 4, 6))))

                .addTreeFeature(Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.OAK_TREE_CONFIG), 3)
                .addTreeFeature(Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.PINE_TREE_CONFIG), 1)
                .addTreeFeature(Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.SPRUCE_TREE_CONFIG), 1));

        MOIST_FOREST_BIOME = register(new Identifier("ecotones", "spruce_forest"), template.builder()
                .temperature(0.8F)
                .downfall(0.6F)
                .scale(0.3f)
                .precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST)
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new ShrubConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 0.5f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.TAIGA_GRASS_CONFIG).createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 6, 8))))
                .addTreeFeature(Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.OAK_TREE_CONFIG), 1)
                .addTreeFeature(Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.SPRUCE_TREE_CONFIG), 5));

        WET_FOREST_BIOME = register(new Identifier("ecotones", "temperate_forest"), template.builder()
                .temperature(0.8F)
                .downfall(0.8F)
                .scale(0.4f)
                .precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST)
                .addDefaultFeatures(PLAINS_TALL_GRASS, PLAINS_FEATURES)
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new ShrubConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 0.5f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(FeatureConfigHolder.SHORT_GRASS_CONFIG).createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 16, 20))))
                .addTreeFeature(Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.OAK_TREE_CONFIG), 5)
                .addTreeFeature(Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.BIRCH_TREE_CONFIG), 1));

        RAINFOREST_BIOME = register( new Identifier("ecotones", "temperate_rainforest"), template.builder()
                .temperature(0.8F)
                .downfall(1F)
                .scale(0.4f)
                .precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST)
                .addDefaultFeatures(PLAINS_TALL_GRASS, PLAINS_FEATURES)
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new ShrubConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(2, 0.5f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.SHRUB.configure(new ShrubConfig(Blocks.JUNGLE_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(4, 0.5f, 1))))

                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.GRASS_CONFIG).createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 20, 20))))
                .addTreeFeature(Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.OAK_TREE_CONFIG), 2)
                .addTreeFeature(Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.JUNGLE_TREE_CONFIG), 2)
                .addTreeFeature(Feature.MEGA_JUNGLE_TREE.configure(DefaultBiomeFeatures.MEGA_JUNGLE_TREE_CONFIG), 1));

        Humidity2BiomeMap.put(0.75, Registry.BIOME.getRawId(RAINFOREST_BIOME));
        Humidity2BiomeMap.put(0.5, Registry.BIOME.getRawId(WET_FOREST_BIOME));
        Humidity2BiomeMap.put(0.25, Registry.BIOME.getRawId(MOIST_FOREST_BIOME));
        Humidity2BiomeMap.put(0.1, Registry.BIOME.getRawId(DRY_FOREST_BIOME));
        Humidity2BiomeMap.put(-0.1, Registry.BIOME.getRawId(VERY_DRY_FOREST_BIOME));
        Humidity2BiomeMap.put(-0.25, Registry.BIOME.getRawId(DESERT_WOODLAND_BIOME));
        Humidity2BiomeMap.put(-0.5, Registry.BIOME.getRawId(DESERT_SCRUB_BIOME));
        Humidity2BiomeMap.put(-0.75, Registry.BIOME.getRawId(DESERT_BIOME));
    }

    public static Biome register(Identifier name, TerraformBiome.Builder builder) {
        Integer[] ids = new Integer[2];
        Biome ret = Registry.register(Registry.BIOME, name, builder.build());
        ids[0] = Registry.BIOME.getRawId(Registry.register(Registry.BIOME,
                new Identifier(name.getNamespace(), name.getPath().concat("_hilly")),
                builder.depth(ret.getDepth()+0.75f).scale(ret.getScale()+0.2f).temperature(ret.getTemperature()-0.15f).build()));

        ids[1] = Registry.BIOME.getRawId(Registry.register(Registry.BIOME,
                new Identifier(name.getNamespace(), name.getPath().concat("_mountainous")),
                builder.depth(ret.getDepth()+1.5f).scale(ret.getScale()+0.3f).temperature(ret.getTemperature()-0.3f).build()));

        MountainLayer.Biome2MountainBiomeMap.put(Registry.BIOME.getRawId(ret), ids);

        return ret;
    }
}
