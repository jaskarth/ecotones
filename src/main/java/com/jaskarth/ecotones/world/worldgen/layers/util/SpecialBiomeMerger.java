package com.jaskarth.ecotones.world.worldgen.layers.util;

import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.MergingLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.IdentityCoordinateTransformer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampler;

public enum SpecialBiomeMerger implements MergingLayer, IdentityCoordinateTransformer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        int specialSample = sampler2.sample(x, z);
        
        if (specialSample != 1) {
            return specialSample;
        }

        return sampler1.sample(x, z);
    }
}
