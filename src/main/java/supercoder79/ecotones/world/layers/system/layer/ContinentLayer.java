package supercoder79.ecotones.world.layers.system.layer;

import supercoder79.ecotones.world.layers.system.layer.type.InitLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;

public enum ContinentLayer implements InitLayer {
   INSTANCE;

   @Override
   public int sample(LayerRandomnessSource context, int x, int y) {
      if (x == 0 && y == 0) {
         return 1;
      } else {
         return context.nextInt(10) == 0 ? 1 : 0;
      }
   }
}
