package supercoder79.ecotones.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;

import java.util.Random;

public class ThornBrushSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private OpenSimplexNoise sandNoise;
    private OpenSimplexNoise coarseDirtNoise;

    private long seed;

    public ThornBrushSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, TernarySurfaceConfig surfaceBlocks) {
        double sand = this.sandNoise.sample(x / 24.0, z / 24.0);
        sand += random.nextDouble() * 0.3;
        sand += noise / 13.0;

        double coarseDirt = this.coarseDirtNoise.sample(x / 32.0, z / 32.0);
        coarseDirt += (random.nextDouble() - random.nextDouble()) * random.nextDouble() * 0.4;
        coarseDirt += noise / 4.0;

        if (sand > 0.6) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.SAND_CONFIG);
        } else if (coarseDirt > 0.4) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.COARSE_DIRT_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.GRASS_CONFIG);
        }
    }

    @Override
    public void initSeed(long seed) {
        if (this.seed != seed) {
            this.sandNoise = new OpenSimplexNoise(seed - 32);
            this.coarseDirtNoise = new OpenSimplexNoise(seed + 32);
            this.seed = seed;
        }
    }
}
