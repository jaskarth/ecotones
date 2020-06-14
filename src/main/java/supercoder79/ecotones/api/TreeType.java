package supercoder79.ecotones.api;

import net.minecraft.block.Blocks;

public enum TreeType {
    OAK(new TreeGenerationConfig(1, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 4, 4, 6, 18, 0.5, 0.4, false)),
    LUSH_OAK(new TreeGenerationConfig(0.7, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 4, 4, 12, 12, 0.45, 0.45, false)),
    DRY_OAK(new TreeGenerationConfig(0.2, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 3, 6, 4, 10, 0.35, 0.25, false)),
    RARE_LARGE_OAK(new TreeGenerationConfig(0.06, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 3, 5, 8, 4, 0.4, 0.3, false)),
    RARE_LARGE_CLUSTERED_OAK(new TreeGenerationConfig(0.04, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 4, 5, 12, 7, 0.3, 0.3, false)),
    BIRCH(new TreeGenerationConfig(0.7, Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState(), 5, -1, 8, 20, 0.25, 0.25, false)),
    LUSH_BIRCH(new TreeGenerationConfig(1, Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState(), 4, -1, 12, 16, 0.15, 0.15, false)),
    DRY_BIRCH(new TreeGenerationConfig(0.3, Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState(), 3, -1, 5, 12, 0.2, 0.2, false)),
    FOREST_BIRCH(new TreeGenerationConfig(3, Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState(), 6, -1, 8, 24, 0.35, 0.35, false)),
    DEAD_SPRUCE(new TreeGenerationConfig(3, Blocks.SPRUCE_LOG.getDefaultState(), Blocks.AIR.getDefaultState(), 4, -1, 10, 10, 0.5, 0.5, false)),
    RARE_DEAD_SPRUCE(new TreeGenerationConfig(0.06, Blocks.SPRUCE_LOG.getDefaultState(), Blocks.AIR.getDefaultState(), 4, -1, 6, 14, 0.5, 0.5, false)),
    ACACIA(new TreeGenerationConfig(0.8, Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState(), 3, 8, 6, 6, 0.45, 0.35, false)),
    FLOODED_ACACIA(new TreeGenerationConfig(1, Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState(), 3, 12, 6, 6, 0.55, 0.425, false)),
    RARE_ACACIA(new TreeGenerationConfig(0.1, Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState(), 3, 8, 6, 6, 0.45, 0.35, false)),
    LUSH_JUNGLE(new TreeGenerationConfig(0.2, Blocks.JUNGLE_LOG.getDefaultState(), Blocks.JUNGLE_LEAVES.getDefaultState(), 4, 4, 14, 8, 0.45, 0.45, true));

    public final TreeGenerationConfig config;

    TreeType(TreeGenerationConfig config) {
        this.config = config;
    }
}
