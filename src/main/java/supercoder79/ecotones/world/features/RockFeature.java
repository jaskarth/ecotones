package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.ecotones.world.features.config.RockFeatureConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RockFeature extends Feature<RockFeatureConfig> {
    public RockFeature(Codec<RockFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, RockFeatureConfig config) {
        // TODO: cleanup
        while(true) {
            restart: {
                if (pos.getY() > 3) {
                    if (world.isAir(pos.down())) {
                        break restart;
                    }

                    BlockState state = world.getBlockState(pos.down());
                    if (!isStone(state.getBlock()) && !isStone(state.getBlock())) {
                        break restart;
                    }
                }

                if (pos.getY() <= 3) {
                    return false;
                }

                int startRadius = config.startRadius;

                List<BlockPos> dirtPositions = new ArrayList<>();

                for(int spheroidCount = 0; startRadius >= 0 && spheroidCount < 3; ++spheroidCount) {
                    int x = startRadius + random.nextInt(2);
                    int y = startRadius + random.nextInt(2);
                    int z = startRadius + random.nextInt(2);
                    float radius = (float)(x + y + z) * 0.333F + 0.5F;
                    Iterator<BlockPos> iterator = BlockPos.iterate(pos.add(-x, -y, -z), pos.add(x, y, z)).iterator();

                    // We disable this inspection because ***somehow*** it doesn't work with a foreach?! How?!
                    //noinspection WhileLoopReplaceableByForEach
                    while(iterator.hasNext()) {
                        BlockPos local = iterator.next();
                        if (local.getSquaredDistance(pos) <= (double)(radius * radius)) {
                            // Post process if enabled
                            if (config.postProcess) {
                                postProcess(world, local, config.state, random, dirtPositions);
                            } else {
                                world.setBlockState(pos, config.state, 4);
                            }
                        }
                    }

                    pos = pos.add(-(startRadius + 1) + random.nextInt(2 + startRadius * 2), -random.nextInt(2), -(startRadius + 1) + random.nextInt(2 + startRadius * 2));
                }

                convertToGrass(world, dirtPositions);

                return true;
            }

            pos = pos.down();
        }
    }

    //TODO: refactor into custom post processor classes
    private static void postProcess(ServerWorldAccess world, BlockPos pos, BlockState state, Random random, List<BlockPos> dirtPositions) {
        if (state.getBlock() == Blocks.COBBLESTONE) {
            BlockState placementState = state;

            if (random.nextInt(4) == 0) {
                placementState = Blocks.STONE.getDefaultState();
            }

            if (random.nextInt(4) == 0) {
                placementState = Blocks.DIRT.getDefaultState();
                dirtPositions.add(pos.toImmutable());
            }

            world.setBlockState(pos, placementState, 4);
        } else if (state.getBlock() == Blocks.STONE) {
            BlockState placementState = state;

            if (random.nextInt(4) == 0) {
                placementState = Blocks.COBBLESTONE.getDefaultState();
            }

            if (random.nextInt(3) == 0) {
                placementState = Blocks.DIRT.getDefaultState();
                dirtPositions.add(pos.toImmutable());
            }

            world.setBlockState(pos, placementState, 4);
        } else { // Usually stone, generate old-style
            world.setBlockState(pos, state, 4);
        }
    }

    private static void convertToGrass(ServerWorldAccess world, List<BlockPos> dirtPositions) {
        for (BlockPos pos : dirtPositions) {
            BlockState state = world.getBlockState(pos.up());
            if (!state.isOpaque() && !(state.getBlock() == Blocks.WATER)) {
                world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState(), 4);
            }
        }
    }
}
