package supercoder79.ecotones.world.features;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

import java.util.Random;

public final class FeatureHelper {
    public static final Direction[] HORIZONTAL = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    private FeatureHelper() {}

    public static boolean isSolidSurrounding(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isOpaque() &&
                world.getBlockState(pos.north()).isOpaque() &&
                world.getBlockState(pos.east()).isOpaque() &&
                world.getBlockState(pos.south()).isOpaque() &&
                world.getBlockState(pos.west()).isOpaque() &&
                world.getBlockState(pos.down()).isOpaque();
    }

    public static Direction randomHorizontal(Random random) {
        return HORIZONTAL[random.nextInt(HORIZONTAL.length)];
    }

    public static void placeLeaves(WorldAccess world, BlockPos pos, BlockState leaves) {
        if (world.getBlockState(pos).isAir()) {
            world.setBlockState(pos, leaves, 3);
        }
    }

    public static void placeLeaves(FeatureContext<SimpleTreeFeatureConfig> context, BlockPos pos) {
        placeLeaves(context.getWorld(), pos, context.getConfig().leafState);
    }
}
