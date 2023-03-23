package supercoder79.ecotones.world.surface.system;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.function.Supplier;

public class ForwardSurfaceConfig extends TernarySurfaceConfig {
   private final Supplier<BlockState> topMaterial;
   private final Supplier<BlockState> underMaterial;
   private final Supplier<BlockState> underwaterMaterial;

   public ForwardSurfaceConfig(Supplier<BlockState> topMaterial, Supplier<BlockState> underMaterial, Supplier<BlockState> underwaterMaterial) {
      super(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState());
      this.topMaterial = topMaterial;
      this.underMaterial = underMaterial;
      this.underwaterMaterial = underwaterMaterial;
   }

   @Override
   public BlockState getTopMaterial() {
      return this.topMaterial.get();
   }

   @Override
   public BlockState getUnderMaterial() {
      return this.underMaterial.get();
   }

   @Override
   public BlockState getUnderwaterMaterial() {
      return this.underwaterMaterial.get();
   }
}
