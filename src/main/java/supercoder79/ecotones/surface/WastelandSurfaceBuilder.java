package supercoder79.ecotones.surface;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import supercoder79.ecotones.blocks.EcotonesBlocks;

import java.util.Random;
import java.util.function.Function;

public class WastelandSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private static final TernarySurfaceConfig WASTELAND_GRASS = new TernarySurfaceConfig(SurfaceBuilder.GRASS_BLOCK, EcotonesBlocks.driedDirtBlock.getDefaultState(), SurfaceBuilder.GRAVEL);

    public WastelandSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> function) {
        super(function);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig config) {
        double noiseAddition = noise * (random.nextDouble() / 2);

        double randomAddition = ((random.nextDouble() / 2) * (random.nextDouble() / 2)) + (random.nextDouble() * 2);
        double randomAfterAddition = (random.nextDouble() / 3) + (noiseAddition / 4) + (random.nextDouble() / 3);

        double coefficient = random.nextDouble() + randomAddition;
        double gradientNoise = noise + noiseAddition + (((random.nextDouble() + randomAfterAddition) * coefficient));

        if (gradientNoise > 1.0D) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, WASTELAND_GRASS);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, config);
        }
    }
}
