package supercoder79.ecotones.world.layers.system.layer;

import supercoder79.ecotones.world.layers.system.layer.type.DiagonalCrossSamplingLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;

public enum AddMushroomIslandLayer implements DiagonalCrossSamplingLayer {
   INSTANCE;

   @Override
   public int sample(LayerRandomnessSource context, int sw, int se, int ne, int nw, int center) {
      return BiomeLayers.isShallowOcean(center)
            && BiomeLayers.isShallowOcean(nw)
            && BiomeLayers.isShallowOcean(sw)
            && BiomeLayers.isShallowOcean(ne)
            && BiomeLayers.isShallowOcean(se)
            && context.nextInt(100) == 0
         ? 14
         : center;
   }
}
