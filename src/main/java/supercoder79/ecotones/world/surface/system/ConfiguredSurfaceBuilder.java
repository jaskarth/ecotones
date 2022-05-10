package supercoder79.ecotones.world.surface.system;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class ConfiguredSurfaceBuilder<SC extends SurfaceConfig> {
   public final SurfaceBuilder<SC> surfaceBuilder;
   public final SC config;

   public ConfiguredSurfaceBuilder(SurfaceBuilder<SC> surfaceBuilder, SC config) {
      this.surfaceBuilder = surfaceBuilder;
      this.config = config;
   }

   public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int i, long l) {
      this.surfaceBuilder.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, i, l, this.config);
   }

   public void initSeed(long seed) {
      this.surfaceBuilder.initSeed(seed);
   }

   public SC getConfig() {
      return this.config;
   }
}
