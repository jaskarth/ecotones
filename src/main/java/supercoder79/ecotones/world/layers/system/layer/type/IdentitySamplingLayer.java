package supercoder79.ecotones.world.layers.system.layer.type;

import supercoder79.ecotones.world.layers.system.layer.util.IdentityCoordinateTransformer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;

public interface IdentitySamplingLayer extends ParentedLayer, IdentityCoordinateTransformer {
   int sample(LayerRandomnessSource context, int value);

   @Override
   default int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
      return this.sample(context, parent.sample(this.transformX(x), this.transformZ(z)));
   }
}
