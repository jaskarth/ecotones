package supercoder79.ecotones.world.layers.system.layer;

import supercoder79.ecotones.world.layers.system.layer.type.CrossSamplingLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;

public enum AddIslandLayer implements CrossSamplingLayer {
   INSTANCE;

   @Override
   public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
      return BiomeLayers.isShallowOcean(center)
            && BiomeLayers.isShallowOcean(n)
            && BiomeLayers.isShallowOcean(e)
            && BiomeLayers.isShallowOcean(w)
            && BiomeLayers.isShallowOcean(s)
            && context.nextInt(2) == 0
         ? 1
         : center;
   }
}
