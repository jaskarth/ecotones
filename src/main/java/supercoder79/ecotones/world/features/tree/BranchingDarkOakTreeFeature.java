package supercoder79.ecotones.world.features.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.CheckedRandom;
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

public class BranchingDarkOakTreeFeature extends EcotonesFeature<TreeGenerationConfig> {
    public BranchingDarkOakTreeFeature(Codec<TreeGenerationConfig> codec) {
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

        List<BlockPos> leaves = new ArrayList<>();

        trunk(world, pos, random, (float) (Math.PI / 2) * (1 + randomOffset(random)), randomOffset(random), maxHeight, leafPlacementNodes, leaves, config);

        growLeaves(world, random, leafPlacementNodes, leaves, config);

        return true;
    }

    private void growLeaves(WorldAccess world, Random random, List<BlockPos> leafPlacementNodes, List<BlockPos> leaves, TreeGenerationConfig config) {
        for (BlockPos node : leafPlacementNodes) {
            generateSmallLeafLayer(world, node.up(2), leaves, config);
            generateMainLeafLayer(world, random, node.up(), leaves, config);
            generateMainLeafLayer(world, random, node, leaves, config);
        }
    }

    private float randomOffset(Random random) {
        return (random.nextFloat() - 0.5f) * 0.33f;
    }

    private void trunk(WorldAccess world, BlockPos startPos, Random random, float yaw, float pitch, int maxHeight, List<BlockPos> leafPlacementNodes, List<BlockPos> leaves, TreeGenerationConfig config) {
        boolean hasModified = false;
        for (int i = 0; i < maxHeight; i++) {
            BlockPos local = startPos.add(
                    MathHelper.sin(pitch) * MathHelper.cos(yaw) * i,
                    MathHelper.cos(pitch) * i,
                    MathHelper.sin(pitch) * MathHelper.sin(yaw) * i);

            //if the tree hits a solid block, stop
            if (TreeHelper.canLogReplace(world, local)) {
                world.setBlockState(local, config.woodState, 0);
            } else {
                break;
            }

            if (i > 0 && i % config.branchingFactor == 0 && !hasModified) {
                pitch += ((random.nextFloat() - 0.5) * config.pitchChange);
                yaw += ((random.nextFloat() - 0.5) * config.yawChange);
                hasModified = true;
            }

            if (random.nextBoolean()) {
                branch(world, local, random, leaves, config);
            }

            if (i == (maxHeight - 1)) {

                //if the position is wood, place a leaf node
                if (world.getBlockState(local) == config.woodState) {
                    leafPlacementNodes.add(local);
                }
            }
        }
    }

    private void branch(WorldAccess world, BlockPos trunkPos, Random random, List<BlockPos> leaves, TreeGenerationConfig config) {
        BlockPos pos = trunkPos.offset(Direction.Type.HORIZONTAL.random(new CheckedRandom(random.nextLong())));

        if (TreeHelper.canLogReplace(world, pos)) {
            world.setBlockState(pos, config.woodState, 0);

            pos = pos.up();
            if (world.getBlockState(pos).isAir()) {
                world.setBlockState(pos, config.leafState, 0);
            }

            for (Direction direction : Direction.values()) {
                BlockPos local = pos.offset(direction);
                if (world.getBlockState(local).isAir()) {
                    world.setBlockState(local, config.leafState, 0);
                    leaves.add(local);
                }
            }
        }
    }

    private void generateSmallLeafLayer(WorldAccess world, BlockPos pos, List<BlockPos> leaves, TreeGenerationConfig config) {
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

    private void generateMainLeafLayer(WorldAccess world, Random random, BlockPos pos, List<BlockPos> leaves, TreeGenerationConfig config) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos local = pos.add(x, 0, z);
                // randomly place for the ends of the square
                if ((Math.abs(x) == 2 && Math.abs(z) == 2)) {
                    if (random.nextBoolean()) {
                        continue;
                    }
                }

                if (world.getBlockState(local).isAir()) {
                    world.setBlockState(local, config.leafState, 0);
                    leaves.add(local);
                }
            }
        }
    }
}
