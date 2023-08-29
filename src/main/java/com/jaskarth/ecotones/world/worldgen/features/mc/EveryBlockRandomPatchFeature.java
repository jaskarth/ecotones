package com.jaskarth.ecotones.world.worldgen.features.mc;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.util.FeatureContext;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeature;

public class EveryBlockRandomPatchFeature extends EcotonesFeature<RandomPatchFeatureConfig> {
   public EveryBlockRandomPatchFeature(Codec<RandomPatchFeatureConfig> codec) {
      super(codec);
   }

   @Override
   public boolean generate(FeatureContext<RandomPatchFeatureConfig> context) {
      RandomPatchFeatureConfig randomPatchFeatureConfig = context.getConfig();
      Random random = context.getRandom();
      BlockPos blockPos = context.getOrigin();
      StructureWorldAccess structureWorldAccess = context.getWorld();
      BlockPos blockPos2;
      if (randomPatchFeatureConfig.project) {
         blockPos2 = structureWorldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, blockPos);
      } else {
         blockPos2 = blockPos;
      }

      int i = 0;
      BlockPos.Mutable mutable = new BlockPos.Mutable();

      for(int j = 0; j < randomPatchFeatureConfig.tries; ++j) {
         mutable.set(
            blockPos2,
            random.nextInt(randomPatchFeatureConfig.spreadX + 1) - random.nextInt(randomPatchFeatureConfig.spreadX + 1),
            random.nextInt(randomPatchFeatureConfig.spreadY + 1) - random.nextInt(randomPatchFeatureConfig.spreadY + 1),
            random.nextInt(randomPatchFeatureConfig.spreadZ + 1) - random.nextInt(randomPatchFeatureConfig.spreadZ + 1)
         );
         BlockState blockState = randomPatchFeatureConfig.stateProvider.get(random, mutable.toImmutable());
         BlockPos blockPos4 = mutable.down();
         BlockState blockState2 = structureWorldAccess.getBlockState(blockPos4);
         if ((
               structureWorldAccess.isAir(mutable)
                  || randomPatchFeatureConfig.canReplace && structureWorldAccess.getBlockState(mutable).isReplaceable()
            )
            && blockState.canPlaceAt(structureWorldAccess, mutable)
            && (randomPatchFeatureConfig.whitelist.isEmpty() || randomPatchFeatureConfig.whitelist.contains(blockState2.getBlock()))
            && !randomPatchFeatureConfig.blacklist.contains(blockState2)
            && (
               !randomPatchFeatureConfig.needsWater
                  || structureWorldAccess.getFluidState(blockPos4.west()).isIn(FluidTags.WATER)
                  || structureWorldAccess.getFluidState(blockPos4.east()).isIn(FluidTags.WATER)
                  || structureWorldAccess.getFluidState(blockPos4.north()).isIn(FluidTags.WATER)
                  || structureWorldAccess.getFluidState(blockPos4.south()).isIn(FluidTags.WATER)
            )) {
            if (blockState.getBlock() == Blocks.CACTUS || blockState.getBlock() == Blocks.SUGAR_CANE) {
               generateColumn(structureWorldAccess, mutable, blockState, random);
            } else {
               if (blockState.getBlock() instanceof TallPlantBlock) {
                  TallPlantBlock.placeAt(structureWorldAccess, blockState, mutable, 2);
               } else {
                  structureWorldAccess.setBlockState(mutable, blockState, 2);
               }
            }
            ++i;
         }
      }

      return i > 0;
   }

   private void generateColumn(WorldAccess world, BlockPos pos, BlockState state, Random random) {
      BlockPos.Mutable mutable = pos.mutableCopy();
      int i = random.nextInt(3) + 1;
      if (state.getBlock() == Blocks.SUGAR_CANE) {
         i = random.nextInt(3) + 2;
      }

      for(int j = 0; j < i; ++j) {
         world.setBlockState(mutable, state, 2);
         mutable.move(Direction.UP);
      }

   }
}