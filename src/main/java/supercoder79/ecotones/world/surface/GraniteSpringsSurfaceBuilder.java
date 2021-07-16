package supercoder79.ecotones.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class GraniteSpringsSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private static final TernarySurfaceConfig GRANITE_CONFIG = new TernarySurfaceConfig(Blocks.GRANITE.getDefaultState(), Blocks.GRANITE.getDefaultState(), Blocks.GRANITE.getDefaultState());

    public GraniteSpringsSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, TernarySurfaceConfig surfaceBlocks) {
        int scaledHeight = (int) (height + ((noise + (random.nextDouble() - random.nextDouble())) / 3.0));

        int layerSelector = scaledHeight % 8;

        double extra = (noise / 10.0) + random.nextDouble() * 0.15;

        if ((layerSelector <= 1 || layerSelector >= 6) && !(extra > 0.5)) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.GRASS_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, GRANITE_CONFIG);
        }
    }
}
