package supercoder79.ecotones.world.layers.system.layer;

import supercoder79.ecotones.world.layers.system.layer.type.MergingLayer;
import supercoder79.ecotones.world.layers.system.layer.util.IdentityCoordinateTransformer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;

public enum ApplyRiverLayer implements MergingLayer, IdentityCoordinateTransformer {
   INSTANCE;

   @Override
   public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
      int i = sampler1.sample(this.transformX(x), this.transformZ(z));
      int j = sampler2.sample(this.transformX(x), this.transformZ(z));
      if (BiomeLayers.isOcean(i)) {
         return i;
      } else if (j == 7) {
         if (i == 12) {
            return 11;
         } else {
            return i != 14 && i != 15 ? j & 0xFF : 15;
         }
      } else {
         return i;
      }
   }
}
