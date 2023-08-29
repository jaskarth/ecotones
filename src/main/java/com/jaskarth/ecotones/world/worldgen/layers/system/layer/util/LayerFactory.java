package com.jaskarth.ecotones.world.worldgen.layers.system.layer.util;

public interface LayerFactory<A extends LayerSampler> {
   A make();
}
