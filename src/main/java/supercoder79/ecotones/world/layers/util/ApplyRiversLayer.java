package supercoder79.ecotones.world.layers.util;

import supercoder79.ecotones.world.layers.system.layer.type.MergingLayer;
import supercoder79.ecotones.world.layers.system.layer.util.IdentityCoordinateTransformer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.world.biome.BiomeHelper;

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
