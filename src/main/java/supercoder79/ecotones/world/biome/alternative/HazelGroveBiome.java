package supercoder79.ecotones.world.biome.alternative;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NoiseHeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.world.biome.BiomeUtil;
import supercoder79.ecotones.world.biome.EcotonesBiome;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public class HazelGroveBiome extends EcotonesBiome {
    public static HazelGroveBiome INSTANCE;
    public static HazelGroveBiome CLEARING;
    public static HazelGroveBiome HILLY;
    public static HazelGroveBiome HILLY_CLEARING;

    public static TreeFeatureConfig HAZEL_CONFIG =
            new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
                    new SimpleBlockStateProvider(EcotonesBlocks.hazelLeavesBlock.getDefaultState()),
                    new BlobFoliagePlacer(2, 0, 0, 0, 4),
                    new StraightTrunkPlacer(3, 1, 0),
                    new TwoLayersFeatureSize(2, 0, 2))
        .ignoreVines().build();

    public static void init() {
        INSTANCE = Registry.register(Registry.BIOME, new Identifier("ecotones", "hazel_grove"), new HazelGroveBiome(false, false));
        CLEARING = Registry.register(Registry.BIOME, new Identifier("ecotones", "hazel_grove_clearing"), new HazelGroveBiome(true, false));
        HILLY = Registry.register(Registry.BIOME, new Identifier("ecotones", "hazel_grove_hilly"), new HazelGroveBiome(false, true));
        HILLY_CLEARING = Registry.register(Registry.BIOME, new Identifier("ecotones", "hazel_grove_hilly_clearing"), new HazelGroveBiome(true, true));
        BiomeRegistries.registerAllSpecial(id -> true, Registry.BIOME.getRawId(INSTANCE), Registry.BIOME.getRawId(CLEARING), Registry.BIOME.getRawId(HILLY), Registry.BIOME.getRawId(HILLY_CLEARING));
        BiomeRegistries.registerBigSpecialBiome(INSTANCE, 300);
        BiomeRegistries.registerBiomeVariantChance(INSTANCE, 4);
        BiomeRegistries.registerBiomeVariants(INSTANCE, CLEARING, HILLY, HILLY_CLEARING);
        Climate.HOT_VERY_HUMID.add(INSTANCE, 0.08);
        Climate.HOT_HUMID.add(INSTANCE, 0.04);
    }


    protected HazelGroveBiome(boolean clearing, boolean hilly) {
        super(new Settings()
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                .precipitation(Precipitation.RAIN)
                .category(Category.PLAINS)
                .depth(hilly ? 0.9f : 0.25f)
                .scale(hilly ? 0.6f : 0.05f)
                .temperature(1F)
                .downfall(1F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .build()).parent(null)
                .noises(ImmutableList.of(new MixedNoisePoint(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))),
                hilly ? 4.f : 1.5,
                hilly ? 0.8 : 1);

        if (clearing) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    Feature.TREE.configure(HAZEL_CONFIG)
                            .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.5f, 1))));

            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                            .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(6))));
        } else {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    Feature.TREE.configure(HAZEL_CONFIG)
                            .createDecoratedFeature(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(3.5))));

            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                            .createDecoratedFeature(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(3))));
        }

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.GRASS_CONFIG)
                        .createDecoratedFeature(Decorator.NOISE_HEIGHTMAP_DOUBLE.configure(new NoiseHeightmapDecoratorConfig(-0.8D, 4, 8))));

        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.method_28440(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);

        BiomeUtil.addDefaultSpawns(this);
        BiomeUtil.addDefaultFeatures(this);
    }

    @Override
    public int getSkyColor() {
        return 0xc6e4f5;
    }

    @Override
    public int getGrassColorAt(double x, double z) {
        return 0xaebd11;
    }

    @Override
    public int getFoliageColor() {
        return 0xa67c12;
    }
}
