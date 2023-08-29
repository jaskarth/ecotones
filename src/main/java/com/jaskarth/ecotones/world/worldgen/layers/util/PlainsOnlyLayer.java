package com.jaskarth.ecotones.world.worldgen.layers.util;

import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.InitLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;

public enum PlainsOnlyLayer implements InitLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int x, int y) {
        return 1;
    }
}
