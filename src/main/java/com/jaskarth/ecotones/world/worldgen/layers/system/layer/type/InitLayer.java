package com.jaskarth.ecotones.world.worldgen.layers.system.layer.type;

import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerFactory;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampleContext;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampler;

public interface InitLayer {
   default <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context) {
      return () -> context.createSampler((x, z) -> {
            context.initSeed((long)x, (long)z);
            return this.sample(context, x, z);
         });
   }

   int sample(LayerRandomnessSource context, int x, int y);
}
