package supercoder79.ecotones.layers.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.biome.technical.BeachBiome;

public enum ExpandShorelineLayer implements CrossSamplingLayer {
    INSTANCE;

    private static final int BEACH = Registry.BIOME.getRawId(BeachBiome.INSTANCE);

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        if (center > 0) {
            if (n == BEACH || e == BEACH || s == BEACH || w == BEACH) {
                return BEACH;
            }
        }
        return center;
    }
}
