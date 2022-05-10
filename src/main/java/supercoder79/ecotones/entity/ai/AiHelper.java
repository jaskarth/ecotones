package supercoder79.ecotones.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public final class AiHelper {
    @Nullable
    public static BlockPos locateBlock(BlockPos blockPos, BlockView world, int rangeX, int rangeY, Predicate<BlockState> predicate) {
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        int minimumDist = Integer.MAX_VALUE;
        BlockPos minPos = null;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int dx = x - rangeX; dx <= x + rangeX; ++dx) {
            for(int dy = y - rangeY; dy <= y + rangeY; ++dy) {
                for(int dz = z - rangeX; dz <= z + rangeX; ++dz) {
                    mutable.set(dx, dy, dz);
                    if (predicate.test(world.getBlockState(mutable))) {
                        int ax = dx - x;
                        int ay = dy - y;
                        int az = dz - z;

                        int dist = ax * ax + ay * ay + az * az;
                        if (dist < minimumDist) {
                            minimumDist = dist;
                            minPos = mutable.toImmutable();
                        }
                    }
                }
            }
        }

        return minPos;
    }
}
