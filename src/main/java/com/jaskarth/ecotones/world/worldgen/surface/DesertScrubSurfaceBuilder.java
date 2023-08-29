package com.jaskarth.ecotones.world.worldgen.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceConfig;

import java.util.Random;

public class DesertScrubSurfaceBuilder extends SurfaceBuilder<DesertScrubSurfaceBuilder.Config> {
    public static final Config SCRUB_CONFIG = new Config(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), 1.0, 1.0, 1.0);
    public static final Config DESERT_SHRUBLAND_CONFIG = new Config(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), 0.1, 0.3, 0.7);

    public DesertScrubSurfaceBuilder(Codec<Config> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, Config config) {
        double coefficient = (random.nextDouble() * config.coefficientScalar) + config.coefficientAdd;
        double gradientNoise = noise + ((random.nextDouble() * coefficient));
        if (gradientNoise > config.threshold) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.GRASS_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.SAND_CONFIG);
        }
    }

    public static record Config(BlockState topMaterial, BlockState underMaterial, BlockState underwaterMaterial, double coefficientAdd, double coefficientScalar, double threshold) implements SurfaceConfig {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                BlockState.CODEC.fieldOf("top_material").forGetter((config) -> config.topMaterial),
                BlockState.CODEC.fieldOf("under_material").forGetter((config) -> config.underMaterial),
                BlockState.CODEC.fieldOf("underwater_material").forGetter((config) -> config.underwaterMaterial),
                Codec.DOUBLE.fieldOf("coefficient_add").forGetter(c -> c.coefficientAdd),
                Codec.DOUBLE.fieldOf("coefficient_scalar").forGetter(c -> c.coefficientScalar),
                Codec.DOUBLE.fieldOf("threshold").forGetter(c -> c.threshold)
            ).apply(instance, Config::new));

        @Override
        public BlockState getTopMaterial() {
            return this.topMaterial;
        }

        @Override
        public BlockState getUnderMaterial() {
            return this.underMaterial;
        }

        @Override
        public BlockState getUnderwaterMaterial() {
            return this.underwaterMaterial;
        }
    }
}
