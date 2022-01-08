package supercoder79.ecotones.world.layers.system.layer;

import supercoder79.ecotones.world.layers.system.layer.type.SouthEastSamplingLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;

public enum AddBambooJungleLayer implements SouthEastSamplingLayer {
   INSTANCE;

   @Override
   public int sample(LayerRandomnessSource context, int se) {
      return context.nextInt(10) == 0 && se == 21 ? 168 : se;
   }
}
