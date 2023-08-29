package com.jaskarth.ecotones.world.worldgen.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.world.worldgen.surface.system.TernarySurfaceConfig;

import java.util.Random;

public class GreenSpiresSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private static final TernarySurfaceConfig GEYSER_CONFIG = new TernarySurfaceConfig(EcotonesBlocks.GEYSER.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState());

    public GreenSpiresSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, TernarySurfaceConfig config) {
        int rand = random.nextInt(1024);
        if (rand == 1) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, GEYSER_CONFIG);
        } else if (rand == 2) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, config);
            boolean n = chunk.getBlockState(new BlockPos(x-1, height-1, z)).isAir();
            boolean s = chunk.getBlockState(new BlockPos(x+1, height-1, z)).isAir();
            boolean e = chunk.getBlockState(new BlockPos(x, height-1, z+1)).isAir();
            boolean w = chunk.getBlockState(new BlockPos(x, height-1, z-1)).isAir();
            if (!n && !s && !e && !w) {
                chunk.setBlockState(new BlockPos(x, height-1, z), Blocks.WATER.getDefaultState(), false);
            }
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, config);
        }
    }
}
