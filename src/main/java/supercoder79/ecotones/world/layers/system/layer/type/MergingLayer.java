package supercoder79.ecotones.world.layers.system.layer.type;

import supercoder79.ecotones.world.layers.system.layer.util.CoordinateTransformer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerFactory;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;

public interface MergingLayer extends CoordinateTransformer {
   default <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> layer1, LayerFactory<R> layer2) {
      return () -> {
         R layerSampler = layer1.make();
         R layerSampler2 = layer2.make();
         return context.createSampler((x, z) -> {
            context.initSeed((long)x, (long)z);
            return this.sample(context, layerSampler, layerSampler2, x, z);
         }, layerSampler, layerSampler2);
      };
   }

   int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z);
}
