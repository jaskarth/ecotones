package supercoder79.ecotones.tree;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import supercoder79.ecotones.api.TreeGenerationConfig;

import java.util.List;
import java.util.Random;

public interface OakTrait extends Trait {
    default boolean generateThickTrunk() {
        return true;
    }

    default int scaleHeight(int originalHeight) {
        return originalHeight;
    }

    default double getPitch(Random random) {
        return 0;
    }

    default double branchChance() {
        return 1;
    }

    default void generateLeaves(WorldAccess world, BlockPos node, Random random, List<BlockPos> leaves, TreeGenerationConfig config) {
        generateSmallLeafLayer(world, node.up(2), leaves, config);
        generateSmallLeafLayer(world, node.down(2), leaves, config);

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                for (int y = -1; y <= 1; y++) {
                    //skip the edges
                    if (Math.abs(x) == 2 && Math.abs(z) == 2) {
                        continue;
                    }

                    //test and set
                    BlockPos local = node.add(x, y, z);
                    if (world.getBlockState(local).isAir()) {
                        world.setBlockState(local, config.leafState, 0);
                        leaves.add(local);
                    }
                }
            }
        }
    }

    default void generateSmallLeafLayer(WorldAccess world, BlockPos pos, List<BlockPos> leaves,TreeGenerationConfig config) {
        if (world.getBlockState(pos).isAir()) {
            leaves.add(pos);
            world.setBlockState(pos, config.leafState, 0);
        }
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos local = pos.offset(direction);
            if (world.getBlockState(local).isAir()) {
                world.setBlockState(local, config.leafState, 0);
                leaves.add(local);
            }
        }
    }
}
