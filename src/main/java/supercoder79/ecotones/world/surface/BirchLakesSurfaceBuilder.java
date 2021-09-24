package supercoder79.ecotones.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;

import java.util.Random;

public class BirchLakesSurfaceBuilder extends SlopedSurfaceBuilder<TernarySurfaceConfig> {
    private static final TernarySurfaceConfig DIORITE_CONFIG = new TernarySurfaceConfig(Blocks.DIORITE.getDefaultState(), Blocks.DIORITE.getDefaultState(), Blocks.DIORITE.getDefaultState());

    private OpenSimplexNoise noise;
    private long seed;

    public BirchLakesSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, double slope, TernarySurfaceConfig surfaceBlocks) {
        int yLevel = height - seaLevel;
        yLevel += 2;
        double progress = yLevel / 12.0;
        progress += noise / 5.0;
        progress += random.nextDouble() * 0.15;

        double selectorNoise = (this.noise.sample(x / 12.0, z / 12.0) + 1) / 2;
        selectorNoise += random.nextDouble() * 0.1;

        if (selectorNoise > progress || slope > 3) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, DIORITE_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.GRASS_CONFIG);
        }
    }

    @Override
    public void initSeed(long seed) {
        if (this.seed != seed) {
            this.noise = new OpenSimplexNoise(seed);
            this.seed = seed;
        }
    }
}
