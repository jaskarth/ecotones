package supercoder79.ecotones.surface;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import supercoder79.ecotones.noise.OctaveNoiseSampler;
import supercoder79.ecotones.noise.OpenSimplexNoise;

import java.util.Random;
import java.util.function.Function;

public class DesertScrubSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

    public DesertScrubSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> function) {
        super(function);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {

        if (noise > 1.0D) {
            //gradient effect on the grass side
            if (noise > 0.95) {
                int chance = (int) ((noise - 0.9) * 30);
                if (chance > 0) {
                    if (random.nextInt(chance + 1) == 0) {
                        SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.SAND_CONFIG);
                        return;
                    }
                }
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.GRASS_CONFIG);
        } else {
            //gradient effect on the sand side
            if (Math.abs(noise - 1.0D) < 1) {
                if (random.nextInt((int) (Math.abs(noise - 1.0D)*2) + 1) == 0) {
                    SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.GRASS_CONFIG);
                    return;
                }
            }
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.SAND_CONFIG);
        }
    }
}
