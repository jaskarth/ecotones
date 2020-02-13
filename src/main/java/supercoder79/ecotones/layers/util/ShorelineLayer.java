package supercoder79.ecotones.layers.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.biome.technical.BeachBiome;

public enum ShorelineLayer implements CrossSamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        if (n == 0 || e == 0 || s == 0 || w == 0) {
            int id = 0;
            id += (n + e + w + s);
            if (id > 0) {
                return Registry.BIOME.getRawId(BeachBiome.INSTANCE);
            }
        }
        return center;
    }
}
