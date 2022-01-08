package supercoder79.ecotones.world.layers.system.layer.type;

import supercoder79.ecotones.world.layers.system.layer.util.LayerFactory;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;

public interface InitLayer {
   default <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context) {
      return () -> context.createSampler((x, z) -> {
            context.initSeed((long)x, (long)z);
            return this.sample(context, x, z);
         });
   }

   int sample(LayerRandomnessSource context, int x, int y);
}
