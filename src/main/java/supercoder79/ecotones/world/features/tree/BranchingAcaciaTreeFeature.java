package supercoder79.ecotones.world.features.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.util.DataPos;
import supercoder79.ecotones.util.TreeHelper;
import supercoder79.ecotones.world.features.EcotonesFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//creates a branching trunk then places leaves
public class BranchingAcaciaTreeFeature extends EcotonesFeature<TreeGenerationConfig> {

    public BranchingAcaciaTreeFeature(Codec<TreeGenerationConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<TreeGenerationConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        TreeGenerationConfig config = context.getConfig();

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

        branch(world, pos, random, (float) ((Math.PI / 2) + ((random.nextDouble() - 0.5) * (Math.PI * config.yawChange))), (float) ((random.nextDouble() - 0.5) * (Math.PI * config.pitchChange)), maxHeight, 0, leafPlacementNodes, config);

        growLeaves(world, random, leafPlacementNodes, config);

        return false;
    }

    private void growLeaves(WorldAccess world, Random random, List<BlockPos> leafPlacementNodes, TreeGenerationConfig config) {
        for (BlockPos node : leafPlacementNodes) {
            generateSmallLeafLayer(world, random, node.up(2), config);

            for (int x = -3; x <= 3; x++) {
                for (int z = -3; z <= 3; z++) {
                    if (Math.abs(x) == 3 && Math.abs(z) == 3) {
                        continue;
                    }

                    BlockPos local = node.add(x, 1, z);
                    if (world.getBlockState(local).isAir()) {
                        world.setBlockState(local, config.leafState, 0);
                    }
                }
            }
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
            if (TreeHelper.canLogReplace(world, local)) {
                world.setBlockState(local, config.woodState, 0);
            } else {
                break;
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
                    if (!TreeHelper.canLogReplace(world, mutable)) {
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
                if (random.nextBoolean()) {
                    branch(world, local, random, (float) (yaw + (yaw2 * maxYaw)), (float) (pitch + (pitch2 * maxPitch)), maxHeight, depth + 1, leafPlacementNodes, config);
                }
            }
        }
    }

    private void generateSmallLeafLayer(WorldAccess world, Random random, BlockPos pos, TreeGenerationConfig config) {
        //switch small leaf types
        if (random.nextBoolean()) {
            //make smaller version of normal leaf layer
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    if (Math.abs(x) == 2 && Math.abs(z) == 2) {
                        continue;
                    }
                    BlockPos local = pos.add(x, 0, z);
                    if (world.getBlockState(local).isAir()) {
                        world.setBlockState(local, config.leafState, 0);
                    }
                }
            }
        } else {
            //make vanilla-like small leaf layer
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos local = pos.add(x, 0, z);
                    if (world.getBlockState(local).isAir()) {
                        world.setBlockState(local, config.leafState, 0);
                    }
                }
            }

            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos local = pos.offset(direction, 2);
                if (world.getBlockState(local).isAir()) {
                    world.setBlockState(local, config.leafState, 0);
                }
            }
        }
    }
}
