package supercoder79.ecotones.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import supercoder79.ecotones.blocks.EcotonesBlocks;

import java.util.Random;

public class RedRockSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private static final TernarySurfaceConfig RED_ROCK_CONFIG = new TernarySurfaceConfig(EcotonesBlocks.RED_ROCK.getDefaultState(), EcotonesBlocks.RED_ROCK.getDefaultState(), EcotonesBlocks.RED_ROCK.getDefaultState());

    public RedRockSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int start, long seed, TernarySurfaceConfig surfaceBlocks) {
        double scaledNoise = noise / 6;
        scaledNoise += random.nextDouble() * 0.1;
        scaledNoise += (noise > 0.2 && noise < 1.2) ? 0.2 : 0;
        scaledNoise += MathHelper.clamp((height - 63) / 44.0, 0.0, 1.0) * 0.5;

        if (random.nextInt(64) == 0) {
            scaledNoise += 0.3;
        }

        if (scaledNoise > 0.6) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, SurfaceBuilder.GRASS_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, start, seed, RED_ROCK_CONFIG);
        }
    }
}
