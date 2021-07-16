package supercoder79.ecotones.world.tree.root;

import supercoder79.ecotones.util.layer.Layer;

public final class RootLayers {
    private RootLayers() {
    }

    public static Layer create(long seed) {
        return new RootSmoothLayer(seed + 2, new RootLayer(seed));
    }
}
