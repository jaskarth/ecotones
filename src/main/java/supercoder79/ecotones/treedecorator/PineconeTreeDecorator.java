package supercoder79.ecotones.treedecorator;

import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.decorator.TreeDecorator;
import supercoder79.ecotones.blocks.EcotonesBlocks;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class PineconeTreeDecorator extends TreeDecorator {
    private int rarity;

    public PineconeTreeDecorator(int rarity) {
        super(null);
        this.rarity = rarity;
    }

    @Override
    public void generate(IWorld world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> set, BlockBox box) {
        for (BlockPos pos : leavesPositions) {
            if (world.getBlockState(pos.down()).isAir()) {
                if (random.nextInt(rarity) == 0) {
                    world.setBlockState(pos.down(), EcotonesBlocks.pineconeBlock.getDefaultState(), 3);
                }
            }
        }
    }

    @Override
    public <T> T serialize(DynamicOps<T> ops) {
        return null;
    }
}
