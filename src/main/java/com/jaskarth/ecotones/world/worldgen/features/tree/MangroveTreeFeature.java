package com.jaskarth.ecotones.world.worldgen.features.tree;

import com.jaskarth.ecotones.world.worldgen.features.FeatureHelper;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import com.jaskarth.ecotones.api.TreeGenerationConfig;
import com.jaskarth.ecotones.util.DataPos;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeature;
import com.jaskarth.ecotones.world.worldgen.tree.decorator.LeafVineTreeDecorator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class MangroveTreeFeature extends EcotonesFeature<TreeGenerationConfig> {
    private static final LeafVineTreeDecorator DECORATOR = new LeafVineTreeDecorator(3, 5, 3);
    public MangroveTreeFeature(Codec<TreeGenerationConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<TreeGenerationConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        TreeGenerationConfig config = context.getConfig();

        BlockState downState = world.getBlockState(pos.down());
        if (downState != Blocks.GRASS_BLOCK.getDefaultState() && downState != Blocks.DIRT.getDefaultState()) {
            return true;
        }

        int maxHeight = 4;
        if (pos instanceof DataPos data) {
            maxHeight = data.maxHeight;

            if (data.isLikelyInvalid) {
                return false;
            }
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

        BiConsumer<BlockPos, BlockState> replacer = (p, s) -> world.setBlockState(p, s, 3);

        TreeDecorator.Generator generator = new TreeDecorator.Generator(world, replacer, new CheckedRandom(random.nextLong()), Set.of(), new HashSet<>(leaves), Set.of());
        DECORATOR.generate(generator);
    }

    private void root(WorldAccess world, BlockPos startPos, Random random, float yaw, float pitch, TreeGenerationConfig config) {
        BlockPos local = startPos;
        int i = 0;
        while (world.getBlockState(local).isAir() || world.getBlockState(local).getFluidState().isIn(FluidTags.WATER) || world.getBlockState(local) == config.woodState || world.getBlockState(local).isReplaceable()) {
            local = startPos.add(
                    BlockPos.ofFloored(
                    MathHelper.sin(pitch) * MathHelper.cos(yaw) * i,
                    MathHelper.cos(pitch) * i,
                    MathHelper.sin(pitch) * MathHelper.sin(yaw) * i)
            );
            i++;
            if (i > 25) break;

            if (FeatureHelper.canLogReplace(world, local)) {
                world.setBlockState(local, config.woodState, 0);
            }
        }
    }

    private void trunk(WorldAccess world, BlockPos startPos, Random random, float yaw, float pitch, int maxHeight, List<BlockPos> leafPlacementNodes, List<BlockPos> leaves, TreeGenerationConfig config) {
        boolean hasModified = false;
        for (int i = 0; i < maxHeight; i++) {
            BlockPos local = startPos.add(
                    BlockPos.ofFloored(
                            MathHelper.sin(pitch) * MathHelper.cos(yaw) * i,
                            MathHelper.cos(pitch) * i,
                            MathHelper.sin(pitch) * MathHelper.sin(yaw) * i)
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

        if (FeatureHelper.canLogReplace(world, pos)) {
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
