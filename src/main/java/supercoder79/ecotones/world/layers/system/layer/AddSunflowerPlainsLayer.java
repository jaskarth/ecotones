package supercoder79.ecotones.world.layers.system.layer;

import supercoder79.ecotones.world.layers.system.layer.type.SouthEastSamplingLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;

public enum AddSunflowerPlainsLayer implements SouthEastSamplingLayer {
   INSTANCE;

   @Override
   public int sample(LayerRandomnessSource context, int se) {
      return context.nextInt(57) == 0 && se == 1 ? 129 : se;
   }
}
