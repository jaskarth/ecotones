package supercoder79.ecotones.world.layers.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.world.biome.BiomeUtil;

public enum ApplyRiversLayer implements MergingLayer, IdentityCoordinateTransformer {
    INSTANCE;

    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        int i = sampler1.sample(this.transformX(x), this.transformZ(z));
        int j = sampler2.sample(this.transformX(x), this.transformZ(z));
        if (BiomeUtil.isOcean(i)) {
            return i;
        } else if (j != -1) {
            return j;
        } else {
            return i;
        }
    }
}
