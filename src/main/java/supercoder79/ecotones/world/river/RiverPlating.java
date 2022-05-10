package supercoder79.ecotones.world.river;

import supercoder79.ecotones.world.layers.LayerHelper;
import supercoder79.ecotones.world.layers.system.layer.ScaleLayer;
import supercoder79.ecotones.world.layers.system.layer.util.*;
import supercoder79.ecotones.world.layers.util.UncontextedAddIslandLayer;
import supercoder79.ecotones.world.layers.util.UncontextedLandLayer;

import java.util.function.LongFunction;

public final class RiverPlating {
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(long seed, LongFunction<C> contextProvider) {
        // Copypaste of the biomelayer continent stack

        //Initialize land
        LayerFactory<T> layerFactory = UncontextedLandLayer.INSTANCE.create(contextProvider.apply(1L));

        layerFactory = ScaleLayer.FUZZY.create(contextProvider.apply(2000L), layerFactory);
        layerFactory = ScaleLayer.NORMAL.create(contextProvider.apply(2001L), layerFactory);

        // RemoveTooMuchOcean- do we need it?
        layerFactory = UncontextedAddIslandLayer.INSTANCE.create(contextProvider.apply(2L), layerFactory);

        layerFactory = LayerHelper.stack(2001L, ScaleLayer.NORMAL, layerFactory, 2, contextProvider);

        //scale up the land to be bigger
        layerFactory = LayerHelper.stack(2001L, ScaleLayer.NORMAL, layerFactory, 2, contextProvider);

        layerFactory = LayerHelper.stack(2081L, ScaleLayer.NORMAL, layerFactory, 3, contextProvider);

        return layerFactory;
    }

    public static CachingLayerSampler build(long seed) {;
        return build(seed, (salt) -> new CachingLayerContext(25, seed, salt)).make();
    }
}
