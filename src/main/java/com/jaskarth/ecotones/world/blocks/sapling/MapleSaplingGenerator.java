package com.jaskarth.ecotones.world.blocks.sapling;

import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import com.jaskarth.ecotones.api.TreeType;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;

public final class MapleSaplingGenerator extends EcotonesSaplingGenerator {
    @Override
    protected @Nullable ConfiguredFeature<?, ?> getFeature(Random random, boolean bees) {
        return EcotonesFeatures.MAPLE_TREE.configure(TreeType.STANDARD_MAPLE).vanilla();
    }
}
