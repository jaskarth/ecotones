package supercoder79.ecotones.tree.oak;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.tree.OakTrait;

import java.util.List;
import java.util.Random;

public class SparseOakTrait implements OakTrait {
    @Override
    public void generateLeaves(WorldAccess world, BlockPos node, Random random, List<BlockPos> leaves, TreeGenerationConfig config) {
        generateSmallLeafLayer(world, node.up(1), leaves, config);
        generateSmallLeafLayer(world, node.down(1), leaves, config);

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                //test and set
                BlockPos local = node.add(x, 0, z);
                if (world.getBlockState(local).isAir()) {
                    world.setBlockState(local, config.leafState, 0);
                    leaves.add(local);
                }
            }
        }
    }

    @Override
    public String name() {
        return "Sparse";
    }
}
