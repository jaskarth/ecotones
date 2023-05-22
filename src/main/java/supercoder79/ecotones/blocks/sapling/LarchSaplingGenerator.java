package supercoder79.ecotones.blocks.sapling;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public class LarchSaplingGenerator extends EcotonesSaplingGenerator {
    @Override
    protected @Nullable ConfiguredFeature<?, ?> getFeature(Random random, boolean bees) {
        return EcotonesFeatures.LARCH_TREE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), EcotonesBlocks.LARCH_LEAVES.getDefaultState())).vanilla();
    }
}
