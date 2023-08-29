package com.jaskarth.ecotones.world.blocks.sapling;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public abstract class EcotonesSaplingGenerator extends SaplingGenerator {
    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return null;
    }

    @Nullable
    protected abstract ConfiguredFeature<?, ?> getFeature(Random random, boolean bees);

    public boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
        ConfiguredFeature<?, ?> feature = this.getFeature(random, this.areFlowersNearby(world, pos));
        if (feature == null) {
            return false;
        } else {
            BlockState blockState = world.getFluidState(pos).getBlockState();
            world.setBlockState(pos, blockState, Block.NO_REDRAW);
            if (feature.generate(world, chunkGenerator, random, pos)) {
                if (world.getBlockState(pos) == blockState) {
                    world.updateListeners(pos, state, blockState, Block.NOTIFY_LISTENERS);
                }

                return true;
            } else {
                world.setBlockState(pos, state, Block.NO_REDRAW);
                return false;
            }
        }
    }

    private boolean areFlowersNearby(WorldAccess world, BlockPos pos) {
        for(BlockPos blockPos : BlockPos.Mutable.iterate(pos.down().north(2).west(2), pos.up().south(2).east(2))) {
            if (world.getBlockState(blockPos).isIn(BlockTags.FLOWERS)) {
                return true;
            }
        }

        return false;
    }
}
