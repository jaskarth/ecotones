package supercoder79.ecotones.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import supercoder79.ecotones.blocks.EcotonesBlocks;

import java.util.Random;

public class PeatSwampSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

    public PeatSwampSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, TernarySurfaceConfig surfaceBlocks) {
        if (noise > 0.3 && noise < 1.2) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, new TernarySurfaceConfig(EcotonesBlocks.PEAT_BLOCK.getDefaultState(), EcotonesBlocks.PEAT_BLOCK.getDefaultState(), EcotonesBlocks.PEAT_BLOCK.getDefaultState()));
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.GRASS_CONFIG);
        }
    }
}
