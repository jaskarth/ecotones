package com.jaskarth.ecotones.world.blocks.sapling;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;

public class LarchSaplingGenerator extends EcotonesSaplingGenerator {
    @Override
    protected @Nullable ConfiguredFeature<?, ?> getFeature(Random random, boolean bees) {
        return EcotonesFeatures.LARCH_TREE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), EcotonesBlocks.LARCH_LEAVES.getDefaultState())).vanilla();
    }
}
