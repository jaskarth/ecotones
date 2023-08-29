package com.jaskarth.ecotones.world.worldgen.tree.root;

import com.jaskarth.ecotones.util.layer.Layer;

public final class RootLayers {
    private RootLayers() {
    }

    public static Layer create(long seed) {
        return new RootSmoothLayer(seed + 2, new RootLayer(seed));
    }
}
