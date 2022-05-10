package supercoder79.ecotones.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;
import supercoder79.ecotones.world.surface.system.TernarySurfaceConfig;

import java.util.Random;

public class GraniteSpringsSurfaceBuilder extends SlopedSurfaceBuilder<TernarySurfaceConfig> {
    private static final TernarySurfaceConfig GRANITE_CONFIG = new TernarySurfaceConfig(Blocks.GRANITE.getDefaultState(), Blocks.GRANITE.getDefaultState(), Blocks.GRANITE.getDefaultState());

    public GraniteSpringsSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int startHeight, long seed, double slope, TernarySurfaceConfig surfaceConfig) {
        // Place granite on slopes
        if (slope > 3) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, startHeight, seed, GRANITE_CONFIG);
            return;
        }

        int scaledHeight = (int) (height + ((noise + (random.nextDouble() - random.nextDouble())) / 3.0));

        int layerSelector = scaledHeight % 8;

        double extra = (noise / 10.0) + random.nextDouble() * 0.15;

        if ((layerSelector <= 1 || layerSelector >= 6) && !(extra > 0.5)) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, startHeight, seed, SurfaceBuilder.GRASS_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, startHeight, seed, GRANITE_CONFIG);
        }
    }
}
