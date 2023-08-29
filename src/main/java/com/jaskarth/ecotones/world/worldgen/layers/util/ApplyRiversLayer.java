package com.jaskarth.ecotones.world.worldgen.layers.util;

import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.MergingLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.IdentityCoordinateTransformer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampler;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.world.worldgen.biome.BiomeHelper;

public enum ApplyRiversLayer implements MergingLayer, IdentityCoordinateTransformer {
    INSTANCE;

    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        int biomeSample = sampler1.sample(this.transformX(x), this.transformZ(z));
        int riverSample = sampler2.sample(this.transformX(x), this.transformZ(z));
        if (BiomeHelper.isOcean(biomeSample) || BiomeRegistries.NO_RIVER_BIOMES.contains(Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(biomeSample)).get())) {
            return biomeSample;
        } else if (riverSample != -1) {
            return riverSample;
        } else {
            return biomeSample;
        }
    }
}
