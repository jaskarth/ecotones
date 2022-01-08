package supercoder79.ecotones.world.layers.util.unused;

import supercoder79.ecotones.world.layers.system.layer.type.ParentedLayer;
import supercoder79.ecotones.world.layers.system.layer.util.IdentityCoordinateTransformer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;

/**
 * Samples all biomes in a 3x3 grid. No, I'm not calling it KingTransformer, what gave you that idea?
 * This is probably very bad for performance, so use it sparingly.
 *
 * @author SuperCoder79
 */
public interface SquareCrossSamplingLayer extends ParentedLayer, IdentityCoordinateTransformer {
    int sample(LayerRandomnessSource layerRandomnessSource, int w, int nw, int n, int ne, int e, int se, int s, int sw, int center);

    @Override
    default int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        return this.sample(context,
                parent.sample(transformX(x - 1), transformZ(z + 0)),
                parent.sample(transformX(x - 1), transformZ(z + 1)),
                parent.sample(transformX(x + 0), transformZ(z + 1)),
                parent.sample(transformX(x + 1), transformZ(z + 1)),
                parent.sample(transformX(x + 1), transformZ(z + 0)),
                parent.sample(transformX(x + 1), transformZ(z - 1)),
                parent.sample(transformX(x + 0), transformZ(z - 1)),
                parent.sample(transformX(x - 1), transformZ(z - 1)),
                parent.sample(transformX(x + 0), transformZ(z + 0)));
    }
}