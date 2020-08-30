package supercoder79.ecotones.api;

import net.minecraft.block.Blocks;

public class TreeType {
    // Oak trees
    public static final TreeGenerationConfig LARGE_OAK = new TreeGenerationConfig(1, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 4, 4, 6, 18, 0.5, 0.4, false, 79);
    public static final TreeGenerationConfig MEDIUM_OAK = new TreeGenerationConfig(0.7, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 4, 4, 12, 12, 0.45, 0.45, false, 79);
    public static final TreeGenerationConfig RARE_VARYING_OAK = new TreeGenerationConfig(0.2, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 3, 6, 4, 10, 0.35, 0.25, false, 79);
    public static final TreeGenerationConfig RARE_LARGE_OAK = new TreeGenerationConfig(0.06, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 3, 5, 8, 4, 0.4, 0.3, false, 79);
    public static final TreeGenerationConfig RARE_LARGE_CLUSTERED_OAK = new TreeGenerationConfig(0.04, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 4, 5, 12, 7, 0.3, 0.3, false, 79);
    public static final TreeGenerationConfig MEDIUM_RARE_OAK = new TreeGenerationConfig(0.5, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 4, 5, 14, 7, 0.3, 0.3, false, 79);
    public static final TreeGenerationConfig MEDIUM_SMALL_RARE_OAK = new TreeGenerationConfig(0.55, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 4, 5, 8, 5, 0.4, 0.4, false, 79);

    // Birch trees
    public static final TreeGenerationConfig STANDARD_BIRCH = new TreeGenerationConfig(0.7, Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState(), 5, -1, 8, 20, 0.25, 0.25, false, 79);
    public static final TreeGenerationConfig LUSH_BIRCH = new TreeGenerationConfig(1, Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState(), 4, -1, 12, 16, 0.15, 0.15, false, 79);
    public static final TreeGenerationConfig RARE_VARYING_BIRCH = new TreeGenerationConfig(0.3, Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState(), 3, -1, 5, 12, 0.2, 0.2, false, 79);
    public static final TreeGenerationConfig FOREST_BIRCH = new TreeGenerationConfig(3, Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState(), 6, -1, 8, 24, 0.35, 0.35, false, 79);

    // Acacia trees
    public static final TreeGenerationConfig ACACIA = new TreeGenerationConfig(0.8, Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState(), 3, 8, 6, 6, 0.45, 0.35, false, 79);
    public static final TreeGenerationConfig SMALL_ACACIA = new TreeGenerationConfig(0.6, Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState(), 2, 8, 3, 2, 0.5, 0.45, false, 79);
    public static final TreeGenerationConfig COMMON_ACACIA = new TreeGenerationConfig(1, Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState(), 3, 12, 6, 6, 0.55, 0.425, false, 79);
    public static final TreeGenerationConfig RARE_ACACIA = new TreeGenerationConfig(0.1, Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState(), 3, 8, 6, 6, 0.45, 0.35, false, 79);

    // Jungle trees
    public static final TreeGenerationConfig LUSH_JUNGLE = new TreeGenerationConfig(0.2, Blocks.JUNGLE_LOG.getDefaultState(), Blocks.JUNGLE_LEAVES.getDefaultState(), 4, 4, 14, 8, 0.45, 0.45, true, 79);

    // Mangrove trees
    public static final TreeGenerationConfig MANGROVE = new TreeGenerationConfig(4, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 3, -1, 5, 2, 0.2, 0.2, false, 79);

    // Dark oak trees
    public static final TreeGenerationConfig RARE_DARK_OAK = new TreeGenerationConfig(0.3, Blocks.DARK_OAK_LOG.getDefaultState(), Blocks.DARK_OAK_LEAVES.getDefaultState(), 3, -1, 6, 4, 0.2, 0.2, false, 79);
    public static final TreeGenerationConfig STANDARD_DARK_OAK = new TreeGenerationConfig(3.5, Blocks.DARK_OAK_LOG.getDefaultState(), Blocks.DARK_OAK_LEAVES.getDefaultState(), 3, -1, 8, 8, 0.2, 0.2, false, 79);

    // Dead trees
    public static final TreeGenerationConfig DEAD_SPRUCE = new TreeGenerationConfig(3, Blocks.SPRUCE_LOG.getDefaultState(), Blocks.AIR.getDefaultState(), 4, -1, 10, 10, 0.5, 0.5, false, 79);
    public static final TreeGenerationConfig RARE_DEAD_SPRUCE = new TreeGenerationConfig(0.06, Blocks.SPRUCE_LOG.getDefaultState(), Blocks.AIR.getDefaultState(), 4, -1, 6, 14, 0.5, 0.5, false, 79);
    public static final TreeGenerationConfig RARE_DEAD_BIRCH = new TreeGenerationConfig(0.08, Blocks.BIRCH_LOG.getDefaultState(), Blocks.AIR.getDefaultState(), 6, -1, 16, 6, 0.45, 0.45, false, 79);
    public static final TreeGenerationConfig RARER_DEAD_BIRCH = new TreeGenerationConfig(0.03, Blocks.BIRCH_LOG.getDefaultState(), Blocks.AIR.getDefaultState(), 6, -1, 12, 6, 0.45, 0.45, false, 79);
}
