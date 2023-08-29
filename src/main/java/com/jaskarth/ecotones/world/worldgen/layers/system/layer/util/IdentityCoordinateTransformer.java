package com.jaskarth.ecotones.world.worldgen.layers.system.layer.util;

public interface IdentityCoordinateTransformer extends CoordinateTransformer {
   @Override
   default int transformX(int x) {
      return x;
   }

   @Override
   default int transformZ(int y) {
      return y;
   }
}
