package supercoder79.ecotones.util;

import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class TreeUtil {
    public static boolean canLogReplace(IWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.isAir() || !state.getMaterial().isSolid()) {
            return true;
        }
        return state.isIn(BlockTags.LEAVES) || state.isIn(BlockTags.LOGS);
    }
}
