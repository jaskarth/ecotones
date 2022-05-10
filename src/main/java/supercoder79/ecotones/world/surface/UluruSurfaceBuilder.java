package supercoder79.ecotones.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;
import supercoder79.ecotones.world.surface.system.TernarySurfaceConfig;

import java.util.Random;

public class UluruSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

    public UluruSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, TernarySurfaceConfig surfaceBlocks) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        mutable.setX(x);
        mutable.setZ(z);
        for (int i = 0; i < 12; i++) {
            mutable.setY(height - i);
            chunk.setBlockState(mutable.toImmutable(), Blocks.RED_TERRACOTTA.getDefaultState(), false);
        }
    }
}
