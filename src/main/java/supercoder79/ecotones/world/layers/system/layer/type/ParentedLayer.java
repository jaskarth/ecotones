package supercoder79.ecotones.world.layers.system.layer.type;

import supercoder79.ecotones.world.layers.system.layer.util.CoordinateTransformer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerFactory;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;

public interface ParentedLayer extends CoordinateTransformer {
   default <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent) {
      return () -> {
         R layerSampler = parent.make();
         return context.createSampler((x, z) -> {
            context.initSeed((long)x, (long)z);
            return this.sample(context, layerSampler, x, z);
         }, layerSampler);
      };
   }

   int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z);
}
