package supercoder79.ecotones.features.tree;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.util.DataPos;
import supercoder79.ecotones.util.TreeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

//creates a branching trunk then places leaves
public class BranchingOakTreeFeature extends Feature<TreeGenerationConfig> {

    public BranchingOakTreeFeature(Function<Dynamic<?>, ? extends TreeGenerationConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(IWorld world, StructureAccessor accessor, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, TreeGenerationConfig config) {
        //ensure spawn
        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return true;

        // initialize data
        int maxHeight = 9;
        if (pos instanceof DataPos) {
            DataPos data = (DataPos)pos;
            maxHeight = data.maxHeight;
            if (data.isLikelyInvalid) return false;
        }

        List<BlockPos> leafPlacementNodes = new ArrayList<>();

        branch(world, pos, random, (float) (Math.PI / 2), 0, maxHeight, 0, leafPlacementNodes, config);

        growLeaves(world, leafPlacementNodes, config);

        return false;
    }

    private void growLeaves(IWorld world, List<BlockPos> leafPlacementNodes, TreeGenerationConfig config) {
        for (BlockPos node : leafPlacementNodes) {
            generateSmallLeafLayer(world, node.up(2), config);
            generateSmallLeafLayer(world, node.down(2), config);

            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    for (int y = -1; y <= 1; y++) {
                        //skip the edges
                        if (Math.abs(x) == 2 && Math.abs(z) == 2) {
                            continue;
                        }
                        //test and set
                        BlockPos local = node.add(x, y, z);
                        if (world.getBlockState(local).isAir()) {
                            world.setBlockState(local, config.leafState, 0);
                        }
                    }
                }
            }
        }
    }

    private void branch(IWorld world, BlockPos startPos, Random random, float yaw, float pitch, int maxHeight, int depth, List<BlockPos> leafPlacementNodes, TreeGenerationConfig config) {
        int height = maxHeight / config.branchingFactor;

        if (depth == (maxHeight / config.branchingFactor) - 1) {
            height += random.nextInt(4);
        }

        for (int i = 0; i < height; i++) {
            BlockPos local = startPos.add(
                    MathHelper.sin(pitch) * MathHelper.cos(yaw) * i,
                    MathHelper.cos(pitch) * i,
                    MathHelper.sin(pitch) * MathHelper.sin(yaw) * i);

            //if the tree hits a solid block, stop the branch
            if (TreeUtil.canLogReplace(world, local)) {
                world.setBlockState(local, config.woodState, 0);
            } else {
                break;
            }

            //place thick trunk if the tree is big enough
            if (((maxHeight / config.branchingFactor) - depth) > config.thickTrunkDepth) {
                world.setBlockState(local.up(), config.woodState, 0);
                for (Direction direction : Direction.Type.HORIZONTAL) {
                    BlockPos local2 = local.offset(direction);
                    world.setBlockState(local2, config.woodState, 0);
                }
            }

            if (i == height - 1) {
                //test for last branch, then set leaves
                if (depth == (maxHeight / config.branchingFactor) - 1) {
                    leafPlacementNodes.add(local);
                    break;
                }

                //test upwards to ensure we have sky light
                BlockPos.Mutable mutable = local.mutableCopy();
                boolean shouldNotBranch = false;
                for (int y = local.getY() + 1; y < 256; y++) {
                    mutable.setY(y);
                    if (!TreeUtil.canLogReplace(world, mutable)) {
                        shouldNotBranch = true;
                        break;
                    }
                }
                //break if non opaque blocks were found
                if (shouldNotBranch) {
                    break;
                }

                branch(world, local, random, (float) (yaw + ((random.nextDouble() - 0.5) * (Math.PI * config.yawChange))), (float) (pitch + ((random.nextDouble() - 0.5) * (Math.PI * config.pitchChange))), maxHeight, depth + 1, leafPlacementNodes, config);
                branch(world, local, random, (float) (yaw - ((random.nextDouble() - 0.5) * (Math.PI * config.yawChange))), (float) (pitch - ((random.nextDouble() - 0.5) * (Math.PI * config.pitchChange))), maxHeight, depth + 1, leafPlacementNodes, config);
            }
        }
    }

    private void generateSmallLeafLayer(IWorld world, BlockPos pos, TreeGenerationConfig config) {
        if (world.getBlockState(pos).isAir()) {
            world.setBlockState(pos, config.leafState, 0);
        }
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos local = pos.offset(direction);
            if (world.getBlockState(local).isAir()) {
                world.setBlockState(local, config.leafState, 0);
            }
        }
    }
}
