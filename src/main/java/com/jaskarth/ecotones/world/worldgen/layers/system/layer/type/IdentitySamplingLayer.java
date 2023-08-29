package com.jaskarth.ecotones.world.worldgen.layers.system.layer.type;

import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.IdentityCoordinateTransformer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampleContext;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampler;

public interface IdentitySamplingLayer extends ParentedLayer, IdentityCoordinateTransformer {
   int sample(LayerRandomnessSource context, int value);

   @Override
   default int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
      return this.sample(context, parent.sample(this.transformX(x), this.transformZ(z)));
   }
}
