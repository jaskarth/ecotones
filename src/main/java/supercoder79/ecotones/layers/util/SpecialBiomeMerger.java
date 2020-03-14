package supercoder79.ecotones.layers.util;

import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.api.BiomeRegistries;

public enum SpecialBiomeMerger implements MergingLayer, IdentityCoordinateTransformer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        int biomeSample = sampler1.sample(x, z);
        if (x < 256 && z < 256 && x > -256 && z > -256) {
            return biomeSample;
        }

        int specialSample = sampler2.sample(x, z);
        
        if (specialSample != 1) {
            boolean canApply = BiomeRegistries.specialBiomes.get(specialSample).apply(biomeSample);
            return canApply ? specialSample : biomeSample;
        }
        return biomeSample;
    }
}
