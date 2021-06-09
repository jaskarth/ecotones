package supercoder79.ecotones.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class BeachSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private final int y;

    public BeachSurfaceBuilder(Codec<TernarySurfaceConfig> codec, int y) {
        super(codec);
        this.y = y;
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, TernarySurfaceConfig surfaceBlocks) {
        if (height > this.y) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.GRASS_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, surfaceBlocks);
        }
    }
}
