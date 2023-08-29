package com.jaskarth.ecotones.world.worldgen.features;

import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.util.FeatureContext;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;

import java.util.Random;
import java.util.function.Consumer;

public final class FeatureHelper {
    public static final Direction[] HORIZONTAL = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    public static final Direction[] HORIZONTAL_AND_DOWN = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.DOWN};

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

    public static boolean canLogReplace(WorldAccess world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.isAir() || !state.isOpaque()) {
            return true;
        }

        return state.isIn(BlockTags.LEAVES) || state.isIn(BlockTags.LOGS);
    }

    // Code used from Terraform
    public static void circle(BlockPos.Mutable origin, double radius, Consumer<BlockPos.Mutable> consumer) {
        int x = origin.getX();
        int z = origin.getZ();
        double radiusSq = radius * radius;
        int radiusCeil = (int) Math.ceil(radius);

        for (int dz = -radiusCeil; dz <= radiusCeil; ++dz) {
            int dzSq = dz * dz;

            for (int dx = -radiusCeil; dx <= radiusCeil; ++dx) {
                int dxSq = dx * dx;
                if ((double) (dzSq + dxSq) <= radiusSq) {
                    origin.set(x + dx, origin.getY(), z + dz);
                    consumer.accept(origin);
                }
            }
        }
    }
}
