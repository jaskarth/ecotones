package supercoder79.ecotones.world.biome.alternative;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.CountNoiseDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.world.biome.BiomeUtil;
import supercoder79.ecotones.world.biome.EcotonesBiomeBuilder;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public class HazelGroveBiome extends EcotonesBiomeBuilder {
    public static Biome INSTANCE;
    public static Biome CLEARING;
    public static Biome HILLY;
    public static Biome HILLY_CLEARING;

    public static TreeFeatureConfig HAZEL_CONFIG =
            new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
                    new SimpleBlockStateProvider(EcotonesBlocks.HAZEL_LEAVES.getDefaultState()),
                    new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 4),
                    new StraightTrunkPlacer(6, 2, 0),
                    new TwoLayersFeatureSize(2, 0, 2))
        .ignoreVines().build();

    public static void init() {
        INSTANCE = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "hazel_grove"), new HazelGroveBiome(false, false).build());
        CLEARING = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "hazel_grove_clearing"), new HazelGroveBiome(true, false).build());
        HILLY = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "hazel_grove_hilly"), new HazelGroveBiome(false, true).build());
        HILLY_CLEARING = Registry.register(BuiltinRegistries.BIOME, new Identifier("ecotones", "hazel_grove_hilly_clearing"), new HazelGroveBiome(true, true).build());
        Climate.HOT_VERY_HUMID.add(INSTANCE, 0.1);
        Climate.HOT_HUMID.add(INSTANCE, 0.05);
    }


    protected HazelGroveBiome(boolean clearing, boolean hilly) {
        this.category(Biome.Category.FOREST);
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(hilly ? 0.9f : 0.25f);
        this.scale(hilly ? 0.6f : 0.05f);
        this.temperature(1F);
        this.downfall(1F);

        this.precipitation(Biome.Precipitation.RAIN);

        this.grassColor(0xaebd11);
        this.foliageColor(0xa67c12);
        this.skyColor(0xc6e4f5);

        this.hilliness(hilly ? 4.0 : 1.5);
        this.volatility(hilly ? 0.8 : 1.0);

        this.addStructureFeature(ConfiguredStructureFeatures.STRONGHOLD);

        if (clearing) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    Feature.TREE.configure(HAZEL_CONFIG)
                            .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.5f, 1)))
                            .spreadHorizontally()
                            .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE)));

            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                            .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(6))));
        } else {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    Feature.TREE.configure(HAZEL_CONFIG)
                            .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(3.5))));

            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                            .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(3))));
        }

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(clearing ? 1.4 : 0.95))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG)
                        .decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE))
                        .decorate(Decorator.HEIGHTMAP.configure(NopeDecoratorConfig.INSTANCE))
                        .spreadHorizontally()
                        .decorate(Decorator.COUNT_NOISE.configure(new CountNoiseDecoratorConfig(-0.8D, 4, 8))));

        DefaultBiomeFeatures.addDefaultDisks(this.getGenerationSettings());
        DefaultBiomeFeatures.addLandCarvers(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultUndergroundStructures(this.getGenerationSettings());
        DefaultBiomeFeatures.addDungeons(this.getGenerationSettings());
        DefaultBiomeFeatures.addMineables(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());
        DefaultBiomeFeatures.addDefaultMushrooms(this.getGenerationSettings());
        DefaultBiomeFeatures.addSprings(this.getGenerationSettings());
        DefaultBiomeFeatures.addFrozenTopLayer(this.getGenerationSettings());

        BiomeUtil.addDefaultSpawns(this.getSpawnSettings());
        BiomeUtil.addDefaultFeatures(this);
    }
}
