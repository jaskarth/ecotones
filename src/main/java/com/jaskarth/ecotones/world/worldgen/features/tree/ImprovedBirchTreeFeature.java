package com.jaskarth.ecotones.world.worldgen.features.tree;

import com.jaskarth.ecotones.world.worldgen.features.FeatureHelper;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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

public class ImprovedBirchTreeFeature extends EcotonesFeature<TreeGenerationConfig> {

    public ImprovedBirchTreeFeature(Codec<TreeGenerationConfig> codec) {
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
        int maxHeight = 12;
        if (pos instanceof DataPos) {
            DataPos data = (DataPos)pos;
            maxHeight = data.maxHeight;
            if (data.isLikelyInvalid) return false;
        }

        List<BlockPos> leafPlacementNodes = new ArrayList<>();

        trunk(world, pos, random, (float) (Math.PI / 2), 0, maxHeight, leafPlacementNodes, config);

        growLeaves(world, leafPlacementNodes, config);

        return false;
    }

    //grows leaves in a 3d + formation.
    private void growLeaves(WorldAccess world, List<BlockPos> leafPlacementNodes, TreeGenerationConfig config) {
        for (BlockPos node : leafPlacementNodes) {
            for (Direction direction : Direction.values()) {
                BlockPos local = node.offset(direction);
                if (world.getBlockState(local).isAir()) {
                    world.setBlockState(local, config.leafState, 0);
                }
            }
        }
    }

    private void trunk(WorldAccess world, BlockPos startPos, Random random, float yaw, float pitch, int maxHeight, List<BlockPos> leafPlacementNodes, TreeGenerationConfig config) {
        boolean hasModified = false;
        for (int i = 0; i < maxHeight; i++) {
            BlockPos local = startPos.add(
                    BlockPos.ofFloored(
                    MathHelper.sin(pitch) * MathHelper.cos(yaw) * i,
                    MathHelper.cos(pitch) * i,
                    MathHelper.sin(pitch) * MathHelper.sin(yaw) * i
                    )
            );

            //if the tree hits a solid block, stop
            if (FeatureHelper.canLogReplace(world, local)) {
                world.setBlockState(local, config.woodState, 0);
            } else {
                break;
            }

            if (i > 0 && i % config.branchingFactor == 0 && !hasModified) {
                pitch += ((random.nextFloat() - 0.5) * config.pitchChange);
                yaw += ((random.nextFloat() - 0.5) * config.yawChange);
                hasModified = true;
            }

            if ((maxHeight - i) > 3 && (maxHeight - i) < (maxHeight - 3)) {
                branch(world, local, random, leafPlacementNodes, config);
            }

            if (i == (maxHeight - 1)) {
                for (int j = 0; j < random.nextInt(4) + 1; j++) {
                    BlockPos leafLocal = local.down(j);

                    //if the position is wood, place a leaf node
                    if (world.getBlockState(leafLocal) == config.woodState) {
                        leafPlacementNodes.add(leafLocal);
                    }

                    //search in all other directions to ensure we found everything
                    for (Direction direction : Direction.values()) {
                        BlockPos local2 = leafLocal.offset(direction);
                        if (world.getBlockState(local2) == config.woodState) {
                            leafPlacementNodes.add(local2);
                        }
                    }

                }
            }
        }
    }

    private void branch(WorldAccess world, BlockPos trunkPos, Random random, List<BlockPos> leafPlacementNodes, TreeGenerationConfig config) {
        if (random.nextInt(3) == 0) {
            leafPlacementNodes.add(trunkPos);
            return;
        }

        // make an offsetted branch
        BlockPos.Mutable mutable = trunkPos.mutableCopy();
        Direction x = random.nextBoolean() ? Direction.EAST : Direction.WEST;
        Direction z = random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
        mutable.move(x);
        mutable.move(z);
        if (FeatureHelper.canLogReplace(world, mutable)) {
            world.setBlockState(mutable, config.woodState, 0);
        } else {
            return;
        }

        //make larger branches sometimes
        if (random.nextBoolean()) {
            // add leaves only half of the time
            if (random.nextBoolean()) {
                leafPlacementNodes.add(mutable.toImmutable());
            }

            //add second part of branch
            mutable.move(x);
            mutable.move(z);
            // change the direction sometimes
            if (random.nextBoolean()) {
                mutable.move(random.nextBoolean() ? Direction.UP : Direction.DOWN);
            }

            if (FeatureHelper.canLogReplace(world, mutable)) {
                world.setBlockState(mutable, config.woodState, 0);
            } else {
                return;
            }
        }
        leafPlacementNodes.add(mutable.toImmutable());
    }
}
