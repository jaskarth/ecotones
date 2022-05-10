package supercoder79.ecotones.world.layers.system.layer.type;

import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;
import supercoder79.ecotones.world.layers.system.layer.util.NorthWestCoordinateTransformer;

public interface SouthEastSamplingLayer extends ParentedLayer, NorthWestCoordinateTransformer {
   int sample(LayerRandomnessSource context, int se);

   @Override
   default int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
      int i = parent.sample(this.transformX(x + 1), this.transformZ(z + 1));
      return this.sample(context, i);
   }
}
