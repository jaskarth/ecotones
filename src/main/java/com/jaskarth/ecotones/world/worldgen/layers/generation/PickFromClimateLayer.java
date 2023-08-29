package com.jaskarth.ecotones.world.worldgen.layers.generation;

import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.IdentitySamplingLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.api.Climate;

public enum PickFromClimateLayer implements IdentitySamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int value) {
        return Climate.VALUES[value].choose(context);
    }
}
