package supercoder79.ecotones.blocks.sapling;

import net.minecraft.block.BlockState;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.world.features.EcotonesFeatures;

public final class MapleSaplingGenerator extends EcotonesSaplingGenerator {
    @Override
    protected @Nullable ConfiguredFeature<?, ?> getFeature(Random random, boolean bees) {
        return EcotonesFeatures.MAPLE_TREE.configure(TreeType.STANDARD_MAPLE).vanilla();
    }
}
