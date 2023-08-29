package com.jaskarth.ecotones.world.worldgen.features.tree;

import com.jaskarth.ecotones.world.worldgen.features.FeatureHelper;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.util.FeatureContext;
import com.jaskarth.ecotones.api.TreeGenerationConfig;
import com.jaskarth.ecotones.util.DataPos;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeature;

import java.util.ArrayList;
import java.util.List;

public class SmallAcaciaTreeFeature extends EcotonesFeature<TreeGenerationConfig> {

    public SmallAcaciaTreeFeature(Codec<TreeGenerationConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<TreeGenerationConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        TreeGenerationConfig config = context.getConfig();

        //ensure spawn
        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return true;

        // initialize data
        int maxHeight = 4;
        if (pos instanceof DataPos) {
            DataPos data = (DataPos)pos;
            maxHeight = data.maxHeight;
            if (data.isLikelyInvalid) return false;
        }

        if (maxHeight > 6) {
            maxHeight = 6;
        }

        List<BlockPos> leafPlacementNodes = new ArrayList<>();

        branch(world, pos, random, (float) ((Math.PI / 2) + ((random.nextDouble() - 0.5) * (Math.PI * config.yawChange))), (float) ((random.nextDouble() - 0.5) * (Math.PI * config.pitchChange)), maxHeight, 0, leafPlacementNodes, config);

        growLeaves(world, random, leafPlacementNodes, config);

        return false;
    }

    private void growLeaves(WorldAccess world, Random random, List<BlockPos> leafPlacementNodes, TreeGenerationConfig config) {
        for (BlockPos node : leafPlacementNodes) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    // place 3x3
                    BlockPos local = node.add(x, 0, z);
                    if (world.getBlockState(local).isAir()) {
                        world.setBlockState(local, config.leafState, 0);
                    }

                    if (Math.abs(x) == 1 && Math.abs(z) == 1) {
                        continue;
                    }

                    // + shape on top
                    local = node.add(x, 1, z);
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
            height += random.nextInt(3);
        }

        for (int i = 0; i < height; i++) {
            BlockPos local = startPos.add(
                    BlockPos.ofFloored(
                    MathHelper.sin(pitch) * MathHelper.cos(yaw) * i,
                    MathHelper.cos(pitch) * i,
                    MathHelper.sin(pitch) * MathHelper.sin(yaw) * i
                    )
                );

            //if the tree hits a solid block, stop the branch
            if (FeatureHelper.canLogReplace(world, local)) {
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
                    if (!FeatureHelper.canLogReplace(world, mutable)) {
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
}
