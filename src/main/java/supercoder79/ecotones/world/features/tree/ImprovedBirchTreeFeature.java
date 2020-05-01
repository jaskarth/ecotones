package supercoder79.ecotones.world.features.tree;

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

public class ImprovedBirchTreeFeature extends Feature<TreeGenerationConfig> {
    public ImprovedBirchTreeFeature(Function<Dynamic<?>, ? extends TreeGenerationConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(IWorld world, StructureAccessor accessor, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, TreeGenerationConfig config) {
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
    private void growLeaves(IWorld world, List<BlockPos> leafPlacementNodes, TreeGenerationConfig config) {
        for (BlockPos node : leafPlacementNodes) {
            for (Direction direction : Direction.values()) {
                BlockPos local = node.offset(direction);
                if (world.getBlockState(local).isAir()) {
                    world.setBlockState(local, config.leafState, 0);
                }
            }
        }
    }

    private void trunk(IWorld world, BlockPos startPos, Random random, float yaw, float pitch, int maxHeight, List<BlockPos> leafPlacementNodes, TreeGenerationConfig config) {
        boolean hasModified = false;
        for (int i = 0; i < maxHeight; i++) {
            BlockPos local = startPos.add(
                    MathHelper.sin(pitch) * MathHelper.cos(yaw) * i,
                    MathHelper.cos(pitch) * i,
                    MathHelper.sin(pitch) * MathHelper.sin(yaw) * i);

            //if the tree hits a solid block, stop
            if (TreeUtil.canLogReplace(world, local)) {
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

    private void branch(IWorld world, BlockPos trunkPos, Random random, List<BlockPos> leafPlacementNodes, TreeGenerationConfig config) {
        if (random.nextInt(3) == 0) {
            leafPlacementNodes.add(trunkPos);
            return;
        }

        //TODO: check if position is valid

        // make an offsetted branch
        BlockPos.Mutable mutable = trunkPos.mutableCopy();
        Direction x = random.nextBoolean() ? Direction.EAST : Direction.WEST;
        Direction z = random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
        mutable.move(x);
        mutable.move(z);
        world.setBlockState(mutable, config.woodState, 0);

        //make larger branches sometimes
        if ( random.nextBoolean()) {
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
            world.setBlockState(mutable, config.woodState, 0);
            leafPlacementNodes.add(mutable.toImmutable());
        } else {
            leafPlacementNodes.add(mutable.toImmutable());
        }
    }
}
