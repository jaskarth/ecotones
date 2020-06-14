package supercoder79.ecotones.world.features.tree;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.decorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.decorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.util.DataPos;
import supercoder79.ecotones.util.TreeUtil;

import java.util.*;

//creates a branching trunk then places leaves
public class BranchingOakTreeFeature extends Feature<TreeGenerationConfig> {

    public BranchingOakTreeFeature(Codec<TreeGenerationConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ServerWorldAccess world, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos pos, TreeGenerationConfig config) {
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

        branch(world, pos, random, (float) (random.nextDouble() * 2 * Math.PI), 0, maxHeight, 0, leafPlacementNodes, config);

        growLeaves(world, random, leafPlacementNodes, config);

        return false;
    }

    private void growLeaves(WorldAccess world, Random random, List<BlockPos> leafPlacementNodes, TreeGenerationConfig config) {
        List<BlockPos> leaves = new ArrayList<>();
        for (BlockPos node : leafPlacementNodes) {
            generateSmallLeafLayer(world, node.up(2), leaves, config);
            generateSmallLeafLayer(world, node.down(2), leaves, config);

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
                            leaves.add(local);
                        }
                    }
                }
            }
        }

        if (config.generateVines) {
            new LeaveVineTreeDecorator().generate(world, random, ImmutableList.of(), leaves, new HashSet<>(), BlockBox.empty());
            new TrunkVineTreeDecorator().generate(world, random, ImmutableList.of(), leaves, new HashSet<>(), BlockBox.empty());
        }
    }

    private void branch(WorldAccess world, BlockPos startPos, Random random, float yaw, float pitch, int maxHeight, int depth, List<BlockPos> leafPlacementNodes, TreeGenerationConfig config) {
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

                //branch in approximately opposite directions
                double maxYaw = (Math.PI * config.pitchChange);
                double yaw1 = random.nextDouble() - 0.5;
                double yaw2 = -yaw1;

                double maxPitch = (Math.PI * config.yawChange);
                double pitch1 = random.nextDouble() - 0.5;
                double pitch2 = -pitch1;

                branch(world, local, random, (float) (yaw + (yaw1 * maxYaw)), (float) (pitch + (pitch1 * maxPitch)), maxHeight, depth + 1, leafPlacementNodes, config);
                branch(world, local, random, (float) (yaw + (yaw2 * maxYaw)), (float) (pitch + (pitch2 * maxPitch)), maxHeight, depth + 1, leafPlacementNodes, config);
            }
        }
    }

    private void generateSmallLeafLayer(WorldAccess world, BlockPos pos, List<BlockPos> leaves,TreeGenerationConfig config) {
        if (world.getBlockState(pos).isAir()) {
            leaves.add(pos);
            world.setBlockState(pos, config.leafState, 0);
        }
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos local = pos.offset(direction);
            if (world.getBlockState(local).isAir()) {
                world.setBlockState(local, config.leafState, 0);
                leaves.add(local);
            }
        }
    }
}
