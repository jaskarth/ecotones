package supercoder79.ecotones.world.layers;

import supercoder79.ecotones.world.layers.system.layer.type.ParentedLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerFactory;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;

import java.util.function.LongFunction;

public final class LayerHelper {
    public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider) {
        LayerFactory<T> layerFactory = parent;

        for(int i = 0; i < count; ++i) {
            layerFactory = layer.create(contextProvider.apply(seed + i), layerFactory);
        }

        return layerFactory;
    }
}
