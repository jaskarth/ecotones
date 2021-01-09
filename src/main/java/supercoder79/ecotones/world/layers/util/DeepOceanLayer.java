package supercoder79.ecotones.world.layers.util;

import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.biome.BiomeUtil;

public enum DeepOceanLayer implements CrossSamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        if (BiomeUtil.isShallowOcean(center)) {
            int i = 0;
            if (BiomeUtil.isShallowOcean(n)) {
                ++i;
            }

            if (BiomeUtil.isShallowOcean(e)) {
                ++i;
            }

            if (BiomeUtil.isShallowOcean(w)) {
                ++i;
            }

            if (BiomeUtil.isShallowOcean(s)) {
                ++i;
            }

            if (i > 3) {
                if (center == BiomeUtil.OCEAN_ID) return BiomeUtil.DEEP_OCEAN_ID;
                if (center == BiomeUtil.WARM_OCEAN_ID) return BiomeUtil.DEEP_WARM_OCEAN_ID;
                if (center == BiomeUtil.LUKEWARM_OCEAN_ID) return BiomeUtil.DEEP_LUKEWARM_OCEAN_ID;
                if (center == BiomeUtil.COLD_OCEAN_ID) return BiomeUtil.DEEP_COLD_OCEAN_ID;
                if (center == BiomeUtil.FROZEN_OCEAN_ID) return BiomeUtil.DEEP_FROZEN_OCEAN_ID;
            }
        }

        return center;
    }
}
