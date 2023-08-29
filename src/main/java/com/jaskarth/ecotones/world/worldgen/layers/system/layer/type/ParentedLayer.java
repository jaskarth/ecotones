package com.jaskarth.ecotones.world.worldgen.layers.system.layer.type;

import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.CoordinateTransformer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerFactory;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampleContext;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampler;

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
