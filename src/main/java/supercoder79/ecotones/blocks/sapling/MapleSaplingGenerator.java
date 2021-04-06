package supercoder79.ecotones.blocks.sapling;

import net.minecraft.block.BlockState;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.world.features.EcotonesFeatures;

import java.util.Random;

public final class MapleSaplingGenerator extends SaplingGenerator {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bees) {
        return null;
    }

    @Override
    public boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
        return EcotonesFeatures.MAPLE_TREE.configure(TreeType.STANDARD_MAPLE).generate(world, chunkGenerator, random, pos);
    }
}
