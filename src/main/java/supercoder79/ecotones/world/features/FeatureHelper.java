package supercoder79.ecotones.world.features;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

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
}
