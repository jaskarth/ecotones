package supercoder79.ecotones.world.surface;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.ChunkRandom;
import supercoder79.ecotones.util.ImprovedChunkRandom;

import java.util.Arrays;
import java.util.Random;

// Partially derived from https://github.com/Gegy/Terrarium/blob/1.12-dev/src/main/java/net/gegy1000/earth/server/world/ecology/soil/MesaSoilTexture.java.
// Used with permission!
public final class MesaHelper {
    private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
    private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
    private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();
    private static final BlockState YELLOW_TERRACOTTA = Blocks.YELLOW_TERRACOTTA.getDefaultState();
    private static final BlockState BROWN_TERRACOTTA = Blocks.BROWN_TERRACOTTA.getDefaultState();
    private static final BlockState RED_TERRACOTTA = Blocks.RED_TERRACOTTA.getDefaultState();
    private static final BlockState LIGHT_GRAY_TERRACOTTA = Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState();
    private static final BlockState GRAY_TERRACOTTA = Blocks.GRAY_TERRACOTTA.getDefaultState();

    public static BlockState[] initializeRegularMesa(long seed) {
        BlockState[] layerBlocks = new BlockState[64];
        Arrays.fill(layerBlocks, TERRACOTTA);
        ImprovedChunkRandom random = new ImprovedChunkRandom(seed);

        int j;
        for(j = 0; j < 64; ++j) {
            j += random.nextInt(5) + 1;
            if (j < 64) {
                layerBlocks[j] = ORANGE_TERRACOTTA;
            }
        }

        addBand(layerBlocks, random, 1, YELLOW_TERRACOTTA);
        addBand(layerBlocks, random, 2, BROWN_TERRACOTTA);
        addBand(layerBlocks, random, 1, RED_TERRACOTTA);

        addGradientBand(layerBlocks, random, WHITE_TERRACOTTA, LIGHT_GRAY_TERRACOTTA);

        return layerBlocks;
    }

    public static BlockState[] initializeWhiteMesa(long seed) {
        BlockState[] layerBlocks = new BlockState[64];
        Arrays.fill(layerBlocks, WHITE_TERRACOTTA);
        ImprovedChunkRandom random = new ImprovedChunkRandom(seed);

        int y = random.nextInt(3) + 33;
        int z = 0;

        for(int w = 0; w < y; ++w) {
            z += random.nextInt(6) + 4;

            for(int ac = 0; z + ac < 64 && ac < 1; ++ac) {
                layerBlocks[z + ac] = WHITE_TERRACOTTA;
                if (z + ac > 1 && random.nextBoolean()) {
                    layerBlocks[z + ac - 1] = LIGHT_GRAY_TERRACOTTA;
                }

                if (z + ac < 63 && random.nextBoolean()) {
                    layerBlocks[z + ac + 1] = GRAY_TERRACOTTA;
                }

                ac -= random.nextInt(2);
            }

            z -= random.nextInt(6);

            z %= 64;
        }

        return layerBlocks;
    }

    private static void addBand(BlockState[] layerBlocks, Random random, int minDepth, BlockState state) {
        int count = random.nextInt(4) + 2;

        for (int i = 0; i < count; i++) {
            int depth = random.nextInt(3) + minDepth;
            int start = random.nextInt(64);

            for (int y = 0; start + y < 64 && y < depth; y++) {
                layerBlocks[start + y] = state;
            }
        }
    }

    private static void addGradientBand(BlockState[] layerBlocks, Random random, BlockState main, BlockState grad) {
        int count = random.nextInt(3) + 3;
        int y = 0;

        for(int i = 0; i < count; ++i) {
            y += random.nextInt(16) + 4;

            for(int ac = 0; y + ac < 64 && ac < 1; ++ac) {
                layerBlocks[y + ac] = main;
                if (y + ac > 1 && random.nextBoolean()) {
                    layerBlocks[y + ac - 1] = grad;
                }

                if (y + ac < 63 && random.nextBoolean()) {
                    layerBlocks[y + ac + 1] = grad;
                }
            }
        }
    }
}
