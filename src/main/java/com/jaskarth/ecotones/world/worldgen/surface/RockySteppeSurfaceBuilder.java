package com.jaskarth.ecotones.world.worldgen.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import com.jaskarth.ecotones.util.noise.OpenSimplexNoise;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.world.worldgen.surface.system.TernarySurfaceConfig;

import java.util.Random;

public class RockySteppeSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private OpenSimplexNoise noise;
    private long seed;

    public RockySteppeSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, TernarySurfaceConfig surfaceBlocks) {
        double simplexNoise = this.noise.sample(x / 24.0, z / 24.0);
        simplexNoise += random.nextDouble() * 0.1;

        if (simplexNoise > 0.5 || (noise > 0.6 && noise < 1.2)) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.STONE_CONFIG);
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
