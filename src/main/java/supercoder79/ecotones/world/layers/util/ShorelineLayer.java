package supercoder79.ecotones.world.layers.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.world.biome.BiomeUtil;
import supercoder79.ecotones.world.biome.technical.BeachBiome;

public enum ShorelineLayer implements CrossSamplingLayer {
    INSTANCE;
    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        if (BiomeRegistries.NO_BEACH_BIOMES.contains(center)) return center;

        if (!BiomeUtil.isOcean(center)) {
            if (BiomeUtil.isOcean(n) || BiomeUtil.isOcean(e) || BiomeUtil.isOcean(s) || BiomeUtil.isOcean(w)) {
                return Registry.BIOME.getRawId(BeachBiome.INSTANCE);
            }
        }
        return center;
    }
}
