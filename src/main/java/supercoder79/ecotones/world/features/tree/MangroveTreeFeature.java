package supercoder79.ecotones.world.features.tree;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.util.DataPos;
import supercoder79.ecotones.util.TreeUtil;
import supercoder79.ecotones.world.treedecorator.LeafVineTreeDecorator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MangroveTreeFeature extends Feature<TreeGenerationConfig> {
    private static final LeafVineTreeDecorator DECORATOR = new LeafVineTreeDecorator(3, 5, 3);
    public MangroveTreeFeature(Codec<TreeGenerationConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<TreeGenerationConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getPos();
        Random random = context.getRandom();
        TreeGenerationConfig config = context.getConfig();

        BlockState downState = world.getBlockState(pos.down());
        if (downState != Blocks.GRASS_BLOCK.getDefaultState() && downState != Blocks.DIRT.getDefaultState()) return true;
        int maxHeight = 4;
        if (pos instanceof DataPos) {
            DataPos data = (DataPos)pos;
            maxHeight = data.maxHeight;
            if (data.isLikelyInvalid) return false;
        }

        int depth = 3;
        int ySurface = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
        int yFloor = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX(), pos.getZ());

        if (ySurface - yFloor > 0) {
            depth = (ySurface - yFloor) + 2;
        }

        List<BlockPos> leafPlacementNodes = new ArrayList<>();

        // Make 4 roots
        for (int i = 0; i < 4; i++) {
            root(world, pos.up(depth), random, (float) (Math.PI / 2) * (i + randomOffset(random)), (float) (Math.PI / 2) * (1.65f + randomOffset(random)), config);
        }

        List<BlockPos> leaves = new ArrayList<>();

        trunk(world, pos.up(depth + 1), random, (float) (Math.PI / 2) * (1 + randomOffset(random)), randomOffset(random), maxHeight, leafPlacementNodes, leaves, config);

        growLeaves(world, random, leafPlacementNodes, leaves, config);

        return false;
    }

    private float randomOffset(Random random) {
        return (random.nextFloat() - 0.5f) * 0.1f;
    }

    private void growLeaves(StructureWorldAccess world, Random random, List<BlockPos> leafPlacementNodes, List<BlockPos> leaves, TreeGenerationConfig config) {
        for (BlockPos node : leafPlacementNodes) {
            generateSmallLeafLayer(world, node.up(), leaves, config);
            generateMainLeafLayer(world, node, leaves, config);
        }

        DECORATOR.generate(world, random, ImmutableList.of(), leaves, new HashSet<>(), BlockBox.empty());
    }

    private void root(WorldAccess world, BlockPos startPos, Random random, float yaw, float pitch, TreeGenerationConfig config) {
        BlockPos local = startPos;
        int i = 0;
        while (world.getBlockState(local).isAir() || world.getBlockState(local).getFluidState().isIn(FluidTags.WATER) || world.getBlockState(local) == config.woodState || world.getBlockState(local).getMaterial().isReplaceable()) {
            local = startPos.add(
                    MathHelper.sin(pitch) * MathHelper.cos(yaw) * i,
                    MathHelper.cos(pitch) * i,
                    MathHelper.sin(pitch) * MathHelper.sin(yaw) * i);
            i++;
            if (i > 25) break;

            if (TreeUtil.canLogReplace(world, local)) {
                world.setBlockState(local, config.woodState, 0);
            }
        }
    }

    private void trunk(WorldAccess world, BlockPos startPos, Random random, float yaw, float pitch, int maxHeight, List<BlockPos> leafPlacementNodes, List<BlockPos> leaves, TreeGenerationConfig config) {
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

            if (random.nextInt(3) == 0) {
                branch(world, local, random, leaves, config);
            }

            if (i == (maxHeight - 1)) {

                //if the position is wood, place a leaf node
                if (world.getBlockState(local) == config.woodState) {
                    leafPlacementNodes.add(local);
                }

                //search in all other directions to ensure we found everything
                for (Direction direction : Direction.values()) {
                    BlockPos local2 = local.offset(direction);
                    if (world.getBlockState(local2) == config.woodState) {
                        leafPlacementNodes.add(local2);
                    }
                }
            }
        }
    }

    private void branch(WorldAccess world, BlockPos trunkPos, Random random, List<BlockPos> leaves, TreeGenerationConfig config) {
        BlockPos pos = trunkPos.offset(Direction.Type.HORIZONTAL.random(random));

        if (TreeUtil.canLogReplace(world, pos)) {
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

    private void generateMainLeafLayer(WorldAccess world, BlockPos pos, List<BlockPos> leaves, TreeGenerationConfig config) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos local = pos.add(x, 0, z);
                if (world.getBlockState(local).isAir()) {
                    world.setBlockState(local, config.leafState, 0);
                    leaves.add(local);
                }
            }
        }

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos local = pos.offset(direction, 2);
            if (world.getBlockState(local).isAir()) {
                world.setBlockState(local, config.leafState, 0);
                leaves.add(local);
            }
        }
    }
}
