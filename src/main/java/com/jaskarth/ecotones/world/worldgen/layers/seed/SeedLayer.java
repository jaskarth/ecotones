package com.jaskarth.ecotones.world.worldgen.layers.seed;

import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerFactory;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampleContext;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampler;

public interface SeedLayer {
    <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent, long seed);
}
