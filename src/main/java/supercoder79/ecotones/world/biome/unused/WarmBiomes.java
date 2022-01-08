//package supercoder79.ecotones.world.biome.base;
//
//import net.minecraft.block.Blocks;
//import net.minecraft.util.Identifier;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.BiomeParticleConfig;
//import net.minecraft.world.gen.GenerationStep;
//import net.minecraft.world.gen.decorator.*;
//import net.minecraft.world.gen.feature.*;
//import supercoder79.ecotones.world.surface.system.SurfaceBuilder;
//import supercoder79.ecotones.api.Climate;
//import supercoder79.ecotones.api.SimpleTreeDecorationData;
//import supercoder79.ecotones.api.TreeType;
//import supercoder79.ecotones.client.particle.EcotonesParticles;
//import supercoder79.ecotones.world.biome.BiomeHelper;
//import supercoder79.ecotones.world.biome.EcotonesBiome;
//import supercoder79.ecotones.world.decorator.EcotonesDecorators;
//import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
//import supercoder79.ecotones.world.features.EcotonesFeatures;
//import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
//import supercoder79.ecotones.world.features.config.RockFeatureConfig;
//import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
//
//import static com.terraformersmc.terraform.biome.builder.DefaultFeature.*;
//
//public class WarmBiomes {
//    public static Biome COOL_DESERT_BIOME;
//    public static Biome COOL_SCRUBLAND_BIOME;
//    public static Biome COOL_STEPPE_BIOME;
//    public static Biome PRAIRIE_BIOME;
//    public static Biome LICHEN_WOODLAND_BIOME;
//    public static Biome SPRUCE_FOREST_BIOME;
//    public static Biome TEMPERATE_FOREST_BIOME;
//    public static Biome TEMPERATE_RAINFOREST_BIOME;
//
//    private static EcotonesBiome.Template template = new EcotonesBiome.Template(EcotonesBiome.builder()
//            .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.SAND_CONFIG)
//            .precipitation(Biome.Precipitation.NONE).category(Biome.Category.FOREST)
//            .depth(0.5F)
//            .scale(0.075F)
//            .temperature(1.2F)
//            .downfall(0F)
//            .waterColor(4159204)
//            .waterFogColor(329011)
//            .addDefaultFeatures(LAND_CARVERS, STRUCTURES, DUNGEONS, MINEABLES, ORES, DISKS,
//                    DEFAULT_FLOWERS, DEFAULT_MUSHROOMS, FOREST_GRASS, DEFAULT_VEGETATION, SPRINGS, FROZEN_TOP_LAYER)
//            .addStructureFeature(DefaultBiomeFeatures.STRONGHOLD)
//            .addStructureFeature(DefaultBiomeFeatures.NORMAL_MINESHAFT)
//
//            .addStructureFeature(DefaultBiomeFeatures.STANDARD_RUINED_PORTAL)
//
//            .addCustomFeature(GenerationStep.Feature.RAW_GENERATION,
//                    EcotonesFeatures.DRAINAGE.configure(FeatureConfig.DEFAULT).decorate(EcotonesDecorators.DRAINAGE_DECORATOR.configure()))
//
//            .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                    EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS).decorate(EcotonesDecorators.ROCKINESS.configure()))
//
//            .addDefaultSpawnEntries());
//
//    public static void init() {
//        COOL_DESERT_BIOME = BiomeHelper.registerBase(new Identifier("ecotones", "cool_desert"), template.builder()
//                .hilliness(1.6)
//                .particleConfig(new BiomeParticleConfig(EcotonesParticles.SAND, 0.00225F))
//                .addStructureFeature(DefaultBiomeFeatures.DESERT_PYRAMID)
//                .addStructureFeature(DefaultBiomeFeatures.PILLAGER_OUTPOST)
//                .addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(new Identifier("village/desert/town_centers"), 10)))
//
//                .addTreeFeature(EcotonesFeatures.RANDOM_PATCH.configure(DefaultBiomeFeatures.DEAD_BUSH_CONFIG), 4)
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.DESERT_GRASS_CONFIG).decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(3))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(DefaultBiomeFeatures.CACTUS_CONFIG).decorate(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(10)))));
//        COOL_SCRUBLAND_BIOME = BiomeHelper.registerBase(new Identifier("ecotones", "cool_scrubland"), template.builder()
//                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
//                .temperature(1.2F)
//                .downfall(0.2F)
//                .hilliness(1.6)
//                .addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(new Identifier("village/savanna/town_centers"), 4)))
//                .addStructureFeature(DefaultBiomeFeatures.PILLAGER_OUTPOST)
//                .addDefaultFeature(PLAINS_TALL_GRASS)
//
//                .addCustomFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
//                        EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
//                                .decorate(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(8))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.CACTI.configure(FeatureConfig.DEFAULT)
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(1))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(3))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.COOL_SCRUBLAND_CONFIG)
//                                .decorate(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 5, 10))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.DESERTIFY_SOIL.configure(FeatureConfig.DEFAULT)
//                                .decorate(EcotonesDecorators.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(1, 0.5f, 1))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.05))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
//                                .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.1))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.SMALL_ACACIA.configure(TreeType.SMALL_ACACIA)
//                                .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.SMALL_ACACIA.decorationData)))
//
//                .addTreeFeature(EcotonesFeatures.RANDOM_PATCH.configure(DefaultBiomeFeatures.DEAD_BUSH_CONFIG), 4));
//        COOL_STEPPE_BIOME = BiomeHelper.registerBase( new Identifier("ecotones", "cool_steppe"), template.builder()
//                .temperature(1.2F)
//                .downfall(0.35F)
//                .hilliness(2.8)
//                .volatility(0.88)
//                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
//                .addDefaultFeature(PLAINS_TALL_GRASS)
//                .addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(new Identifier("village/savanna/town_centers"), 5)))
//                .addStructureFeature(DefaultBiomeFeatures.PILLAGER_OUTPOST)
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(3))))
//
//                .addCustomFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
//                        EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
//                                .decorate(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(14))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.MOSTLY_SHORT_GRASS_CONFIG).decorate(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 5, 10))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.BRANCHING_ACACIA.configure(TreeType.RARE_ACACIA)
//                                .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_ACACIA.decorationData)))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.15))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
//                                .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.1))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.SMALL_ACACIA.configure(TreeType.SMALL_ACACIA)
//                                .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.SMALL_ACACIA.decorationData))));
//        PRAIRIE_BIOME = BiomeHelper.registerBase(new Identifier("ecotones", "prairie"), template.builder()
//                .temperature(1F)
//                .downfall(0.4F)
//                .scale(0.025f)
//                .hilliness(2.2)
//                .foliageColor(0xabcf59)
//                .grassColor(0xabcf59)
//                .addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(new Identifier("village/plains/town_centers"), 10)))
//                .addStructureFeature(DefaultBiomeFeatures.PILLAGER_OUTPOST)
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
//                                .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.05))))
//
//                .addDefaultFeatures(PLAINS_TALL_GRASS, PLAINS_FEATURES)
//
//                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(1.75))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.CLOVER).decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(1))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.15))))
//
//                .addCustomFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
//                        EcotonesFeatures.BEEHIVES.configure(FeatureConfig.DEFAULT)
//                                .decorate(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(64))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.PRAIRIE_CONFIG).decorate(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 20, 30)))));
//
//        LICHEN_WOODLAND_BIOME = BiomeHelper.registerBase( new Identifier("ecotones", "lichen_woodland"), template.builder()
//                .temperature(0.8F)
//                .downfall(0.5F)
//                .scale(0.15f)
//                .hilliness(1.4)
//                .addDefaultFeatures(PLAINS_TALL_GRASS)
//                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
//                .addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(new Identifier("village/taiga/town_centers"), 4)))
//                .addStructureFeature(DefaultBiomeFeatures.PILLAGER_OUTPOST)
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(5))))
//
//                .addCustomFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
//                        EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
//                                .decorate(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(10))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG)
//                                .decorate(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 4, 6))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION, EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.TAIGA_FLOWERS)
//                        .decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(4))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION, EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.WIDE_FERNS)
//                        .decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(2))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.CLOVER).decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(4))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.IMPROVED_BIRCH.configure(TreeType.RARE_DEAD_SPRUCE)
//                                .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_DEAD_SPRUCE.decorationData)))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
//                                .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.05))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.35))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(DefaultBiomeFeatures.SWEET_BERRY_BUSH_CONFIG)
//                        .decorate(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(3))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        Feature.TREE.configure(FeatureConfigHolder.SPRUCE_TREE_CONFIG)
//                                .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(1.25))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SMALL_LILAC)
//                                .decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(1))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.MOSS)
//                                .decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(2))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.SMALL_SPRUCE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(2.5)))));
//
//        SPRUCE_FOREST_BIOME = BiomeHelper.registerBase(new Identifier("ecotones", "spruce_forest"), template.builder()
//                .temperature(0.8F)
//                .downfall(0.6F)
//                .scale(0.3f)
//                .hilliness(1.6)
//                .precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST)
//                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
//                .addStructureFeature(StructureFeature.VILLAGE.configure(new StructurePoolFeatureConfig(new Identifier("village/taiga/town_centers"), 10)))
//                .addStructureFeature(DefaultBiomeFeatures.PILLAGER_OUTPOST)
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(3))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION, EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.TAIGA_FLOWERS)
//                        .decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(3))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION, EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.WIDE_FERNS)
//                        .decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(1))))
//
//                .addCustomFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
//                        EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
//                                .decorate(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(8))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.CLOVER)
//                                .decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(1))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.IMPROVED_BIRCH.configure(TreeType.RARE_DEAD_SPRUCE)
//                                .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_DEAD_SPRUCE.decorationData)))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.65))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(DefaultBiomeFeatures.SWEET_BERRY_BUSH_CONFIG)
//                                .decorate(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(2))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(DefaultBiomeFeatures.TAIGA_GRASS_CONFIG)
//                                .decorate(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 6, 8))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.MOSS)
//                                .decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(2))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.SMALL_SPRUCE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(1.2))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        Feature.TREE.configure(FeatureConfigHolder.SPRUCE_TREE_CONFIG)
//                                .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(7.5)))));
//
//        TEMPERATE_FOREST_BIOME = BiomeHelper.registerBase(new Identifier("ecotones", "temperate_forest"), template.builder()
//                .temperature(0.8F)
//                .downfall(0.8F)
//                .scale(0.4f)
//                .hilliness(2.2)
//                .volatility(0.96)
//                .precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST)
//                .addDefaultFeatures(PLAINS_TALL_GRASS, PLAINS_FEATURES)
//                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
//
//                .addCustomFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
//                        EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
//                                .decorate(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(6))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(3))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.BRANCHING_OAK.configure(TreeType.LARGE_OAK)
//                                .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.LARGE_OAK.decorationData)))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.IMPROVED_BIRCH.configure(TreeType.STANDARD_BIRCH)
//                                .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.STANDARD_BIRCH.decorationData)))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.CLOVER).decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(1))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(2))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SMALL_LILAC)
//                                .decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(1))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.IMPROVED_BIRCH.configure(TreeType.RARE_DEAD_BIRCH)
//                                .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_DEAD_BIRCH.decorationData)))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.MOSS)
//                                .decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(1))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SHORT_GRASS_CONFIG)
//                                .decorate(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 16, 20))))
//        );
//
//        TEMPERATE_RAINFOREST_BIOME = BiomeHelper.registerBase( new Identifier("ecotones", "temperate_rainforest"), template.builder()
//                .temperature(0.8F)
//                .downfall(1F)
//                .scale(0.4f)
//                .hilliness(3.2)
//                .volatility(0.92)
//                .addStructureFeature(DefaultBiomeFeatures.JUNGLE_PYRAMID)
//                .precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST)
//                .addDefaultFeatures(PLAINS_TALL_GRASS, PLAINS_FEATURES)
//                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(3))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.JUNGLE_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(5))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
//                                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(1))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(DefaultBiomeFeatures.GRASS_CONFIG)
//                                .decorate(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 20, 24))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.MOSS)
//                                .decorate(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(4))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.BRANCHING_OAK.configure(TreeType.RARE_VARYING_OAK)
//                                .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_VARYING_OAK.decorationData)))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.BRANCHING_OAK.configure(TreeType.LUSH_JUNGLE)
//                                .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.LUSH_JUNGLE.decorationData)))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        EcotonesFeatures.BANANA_TREE.configure(DefaultBiomeFeatures.JUNGLE_TREE_CONFIG)
//                                .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(2.25, true))))
//
//                .addCustomFeature(GenerationStep.Feature.VEGETAL_DECORATION,
//                        Feature.TREE.configure(DefaultBiomeFeatures.MEGA_JUNGLE_TREE_CONFIG)
//                                .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(1.25, true)))));
//
//        Climate.WARM_DESERT.add(COOL_DESERT_BIOME, 1);
//        Climate.WARM_VERY_DRY.add(COOL_SCRUBLAND_BIOME, 1);
//        Climate.WARM_DRY.add(COOL_STEPPE_BIOME, 1);
//        Climate.WARM_MODERATE.add(PRAIRIE_BIOME, 1);
//        Climate.WARM_MILD.add(LICHEN_WOODLAND_BIOME, 1);
//        Climate.WARM_HUMID.add(SPRUCE_FOREST_BIOME, 1);
//        Climate.WARM_VERY_HUMID.add(TEMPERATE_FOREST_BIOME, 1);
//        Climate.WARM_RAINFOREST.add(TEMPERATE_RAINFOREST_BIOME, 1);
//    }
//}
