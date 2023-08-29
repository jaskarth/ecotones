package com.jaskarth.ecotones.world.worldgen.surface.system;

import net.minecraft.block.BlockState;

public interface SurfaceConfig {
   BlockState getTopMaterial();

   BlockState getUnderMaterial();

   BlockState getUnderwaterMaterial();
}
