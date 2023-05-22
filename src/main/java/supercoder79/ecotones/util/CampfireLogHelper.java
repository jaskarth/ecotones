package supercoder79.ecotones.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public final class CampfireLogHelper {
    public static final Map<Block, BlockState> LOG_TO_STATE = new HashMap<>();

    public static void initVanilla() {
        LOG_TO_STATE.put(Blocks.OAK_LOG, Blocks.OAK_LOG.getDefaultState());
        LOG_TO_STATE.put(Blocks.BIRCH_LOG, Blocks.BIRCH_LOG.getDefaultState());
        LOG_TO_STATE.put(Blocks.SPRUCE_LOG, Blocks.SPRUCE_LOG.getDefaultState());
        LOG_TO_STATE.put(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LOG.getDefaultState());
    }

    public static BlockState stateFor(Block block) {
        return LOG_TO_STATE.getOrDefault(block, Blocks.OAK_LOG.getDefaultState());
    }
}
