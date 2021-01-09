package supercoder79.ecotones.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class DeleteWaterSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

    public DeleteWaterSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        BlockPos.Mutable pos = new BlockPos.Mutable();
        pos.set(x, 0, z);
        for (int i = 0; i < 64; i++) {
            pos.setY(i);
            if (chunk.getBlockState(pos) == Blocks.WATER.getDefaultState()) {
                chunk.setBlockState(pos, Blocks.AIR.getDefaultState(), false);
            }
        }
    }
}
