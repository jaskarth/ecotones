package supercoder79.ecotones.world.layers.seed;

import supercoder79.ecotones.world.layers.system.layer.util.LayerFactory;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;

public interface SeedLayer {
    <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent, long seed);
}
