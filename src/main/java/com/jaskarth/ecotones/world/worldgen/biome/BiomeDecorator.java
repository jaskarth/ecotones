package com.jaskarth.ecotones.world.worldgen.biome;

import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.GenerationStep;

public final class BiomeDecorator {
    public static void addOakShrubs(EcotonesBiomeBuilder biome, double smallAmt, double wideAmt) {
        addShrubs(biome, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), smallAmt, wideAmt);
    }

    public static void addBirchShrubs(EcotonesBiomeBuilder biome, double smallAmt, double wideAmt) {
        addShrubs(biome, Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState(), smallAmt, wideAmt);
    }

    public static void addSpruceShrubs(EcotonesBiomeBuilder biome, double smallAmt, double wideAmt) {
        addShrubs(biome, Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState(), smallAmt, wideAmt);
    }

    public static void addShrubs(EcotonesBiomeBuilder biome, BlockState log, BlockState leaves, double smallAmt, double wideAmt) {
        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(log, leaves))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(smallAmt))));

        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(log, leaves))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(wideAmt))));
    }
}
