package supercoder79.ecotones.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class GravelBandsSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

    public GravelBandsSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, TernarySurfaceConfig surfaceBlocks) {
        double scaledNoise = noise + (random.nextDouble() - random.nextDouble()) * 0.6;

        if (scaledNoise > 0.3 && scaledNoise < 1.2) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.GRAVEL_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.GRASS_CONFIG);
        }
    }
}
