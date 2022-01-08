package supercoder79.ecotones.world.surface.system;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class DefaultSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
   public DefaultSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
      super(codec);
   }

   public void generate(Random random, Chunk chunk, Biome biome, int i, int j, int k, double d, BlockState blockState, BlockState blockState2, int l, int m, long n, TernarySurfaceConfig ternarySurfaceConfig) {
      this.generate(random, chunk, biome, i, j, k, d, blockState, blockState2, ternarySurfaceConfig.getTopMaterial(), ternarySurfaceConfig.getUnderMaterial(), ternarySurfaceConfig.getUnderwaterMaterial(), l, m);
   }

   protected void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState fluidBlock, BlockState topBlock, BlockState underBlock, BlockState underwaterBlock, int seaLevel, int i) {
      BlockPos.Mutable mutable = new BlockPos.Mutable();
      int j = (int)(noise / 3.0 + 3.0 + random.nextDouble() * 0.25);
      if (j == 0) {
         boolean bl = false;

         for(int k = height; k >= i; --k) {
            mutable.set(x, k, z);
            BlockState blockState = chunk.getBlockState(mutable);
            if (blockState.isAir()) {
               bl = false;
            } else if (blockState.isOf(defaultBlock.getBlock())) {
               if (!bl) {
                  BlockState blockState2;
                  if (k >= seaLevel) {
                     blockState2 = Blocks.AIR.getDefaultState();
                  } else if (k == seaLevel - 1) {
                     blockState2 = biome.isCold(mutable) ? Blocks.ICE.getDefaultState() : fluidBlock;
                  } else if (k >= seaLevel - (7 + j)) {
                     blockState2 = defaultBlock;
                  } else {
                     blockState2 = underwaterBlock;
                  }

                  chunk.setBlockState(mutable, blockState2, false);
               }

               bl = true;
            }
         }
      } else {
         BlockState blockState6 = underBlock;
         int l = -1;

         for(int m = height; m >= i; --m) {
            mutable.set(x, m, z);
            BlockState blockState7 = chunk.getBlockState(mutable);
            if (blockState7.isAir()) {
               l = -1;
            } else if (blockState7.isOf(defaultBlock.getBlock())) {
               if (l == -1) {
                  l = j;
                  BlockState blockState8;
                  if (m >= seaLevel + 2) {
                     blockState8 = topBlock;
                  } else if (m >= seaLevel - 1) {
                     blockState6 = underBlock;
                     blockState8 = topBlock;
                  } else if (m >= seaLevel - 4) {
                     blockState6 = underBlock;
                     blockState8 = underBlock;
                  } else if (m >= seaLevel - (7 + j)) {
                     blockState8 = blockState6;
                  } else {
                     blockState6 = defaultBlock;
                     blockState8 = underwaterBlock;
                  }

                  chunk.setBlockState(mutable, blockState8, false);
               } else if (l > 0) {
                  --l;
                  chunk.setBlockState(mutable, blockState6, false);
                  if (l == 0 && blockState6.isOf(Blocks.SAND) && j > 1) {
                     l = random.nextInt(4) + Math.max(0, m - seaLevel);
                     blockState6 = blockState6.isOf(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
                  }
               }
            }
         }
      }

   }
}
