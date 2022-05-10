package supercoder79.ecotones.world.layers.system.layer;

import supercoder79.ecotones.world.layers.system.layer.type.IdentitySamplingLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;

public enum SimpleLandNoiseLayer implements IdentitySamplingLayer {
   INSTANCE;

   @Override
   public int sample(LayerRandomnessSource context, int value) {
      return BiomeLayers.isShallowOcean(value) ? value : context.nextInt(299999) + 2;
   }
}
