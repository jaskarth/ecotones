package supercoder79.ecotones.world.layers.seed;

import supercoder79.ecotones.world.layers.system.layer.util.LayerFactory;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;

public interface SeedInitLayer {
    <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, long seed);
}
