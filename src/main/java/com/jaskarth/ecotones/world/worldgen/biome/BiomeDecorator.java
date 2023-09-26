package com.jaskarth.ecotones.world.worldgen.biome;

import com.jaskarth.ecotones.world.worldgen.decorator.ChanceDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.RockFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.mc.RandomPatchFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.HeightmapPlacementModifier;
import net.minecraft.world.gen.placementmodifier.NoiseThresholdCountPlacementModifier;

public final class BiomeDecorator {
    public static void addOakShrubs(EcotonesBiomeBuilder biome, double smallAmt, double wideAmt) {
        addShrubs(biome, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), smallAmt, wideAmt);
    }

    public static void addBirchShrubs(EcotonesBiomeBuilder biome, double smallAmt, double wideAmt) {
        addShrubs(biome, Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState(), smallAmt, wideAmt);
    }

    public static void addAcaciaShrubs(EcotonesBiomeBuilder biome, double smallAmt, double wideAmt) {
        addShrubs(biome, Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState(), smallAmt, wideAmt);
    }

    public static void addSpruceShrubs(EcotonesBiomeBuilder biome, double smallAmt, double wideAmt) {
        addShrubs(biome, Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState(), smallAmt, wideAmt);
    }

    public static void addJungleShrubs(EcotonesBiomeBuilder biome, double smallAmt, double wideAmt) {
        addShrubs(biome, Blocks.JUNGLE_LOG.getDefaultState(), Blocks.JUNGLE_LEAVES.getDefaultState(), smallAmt, wideAmt);
    }

    public static void addRock(EcotonesBiomeBuilder biome, int chance) {
        addRock(biome, Blocks.STONE.getDefaultState(), 1, chance);
    }

    public static void addRock(EcotonesBiomeBuilder biome, BlockState state, int size, int chance) {
        biome.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.ROCK.configure(new RockFeatureConfig(state, size))
                        .decorate(EcotonesDecorators.LARGE_ROCK.configure(new ChanceDecoratorConfig(chance))));
    }

    public static void addWaterRock(EcotonesBiomeBuilder biome, BlockState state, int size, int chance) {
        biome.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.ROCK_IN_WATER.configure(new RockFeatureConfig(state, size))
                        .decorate(EcotonesDecorators.LARGE_ROCK.configure(new ChanceDecoratorConfig(chance))));
    }

    public static void addClover(EcotonesBiomeBuilder biome, int amt) {
        addClover(biome, ConstantIntProvider.create(amt));
    }

    public static void addClover(EcotonesBiomeBuilder biome, IntProvider amt) {
        addPatch(biome, FeatureConfigHolder.CLOVER, amt);
        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.CLOVER)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(amt));
    }

    public static void addLilacs(EcotonesBiomeBuilder biome, int amt) {
        addPatch(biome, FeatureConfigHolder.SMALL_LILAC, amt);
    }

    public static void addLavender(EcotonesBiomeBuilder biome, int amt) {
        addPatch(biome, FeatureConfigHolder.LAVENDER, amt);
    }

    public static void addPatch(EcotonesBiomeBuilder biome, RandomPatchFeatureConfig config, int amt) {
        addPatch(biome, config, ConstantIntProvider.create(amt));
    }

    public static void addPatch(EcotonesBiomeBuilder biome, RandomPatchFeatureConfig config, IntProvider amt) {
        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(config)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(amt));
    }

    public static void addPatchChance(EcotonesBiomeBuilder biome, RandomPatchFeatureConfig config, int chance) {
        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(config)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(chance));
    }

    public static void addPatchRepeatedChance(EcotonesBiomeBuilder biome, RandomPatchFeatureConfig config, int amt, int chance) {
        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(config)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(amt)
                        .applyChance(chance)
        );
    }

    public static void addGrass(EcotonesBiomeBuilder biome, RandomPatchFeatureConfig config, int baseAmt) {
        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(config)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(NoiseThresholdCountPlacementModifier.of(-0.8, (int)(baseAmt * 0.75), baseAmt)));
    }

    public static void addShrubs(EcotonesBiomeBuilder biome, BlockState log, BlockState leaves, double smallAmt, double wideAmt) {
        if (smallAmt > 0) {
            biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(log, leaves))
                            .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(smallAmt))));
        }

        if (wideAmt > 0) {
            biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(log, leaves))
                            .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(wideAmt))));
        }
    }

    public static void addSurfaceRocks(EcotonesBiomeBuilder biome) {
        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .decorate(EcotonesDecorators.ROCKINESS.configure()));
    }
}
