package supercoder79.ecotones.world.layers.util;

import supercoder79.ecotones.world.layers.system.layer.BiomeLayers;
import supercoder79.ecotones.world.layers.system.layer.type.CrossSamplingLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;

// River gen- needs just == 0 check
public enum UncontextedAddIslandLayer implements CrossSamplingLayer {
   INSTANCE;

   @Override
   public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
      return center == 0
            && n == 0
            && e == 0
            && w == 0
            && s == 0
            && context.nextInt(2) == 0
         ? 1
         : center;
   }
}
