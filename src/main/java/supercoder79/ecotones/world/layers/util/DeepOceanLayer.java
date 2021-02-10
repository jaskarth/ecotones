package supercoder79.ecotones.world.layers.util;

import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.biome.BiomeHelper;

public enum DeepOceanLayer implements CrossSamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        if (BiomeHelper.isShallowOcean(center)) {
            int i = 0;
            if (BiomeHelper.isShallowOcean(n)) {
                ++i;
            }

            if (BiomeHelper.isShallowOcean(e)) {
                ++i;
            }

            if (BiomeHelper.isShallowOcean(w)) {
                ++i;
            }

            if (BiomeHelper.isShallowOcean(s)) {
                ++i;
            }

            if (i > 3) {
                if (center == BiomeHelper.OCEAN_ID) return BiomeHelper.DEEP_OCEAN_ID;
                if (center == BiomeHelper.WARM_OCEAN_ID) return BiomeHelper.DEEP_WARM_OCEAN_ID;
                if (center == BiomeHelper.LUKEWARM_OCEAN_ID) return BiomeHelper.DEEP_LUKEWARM_OCEAN_ID;
                if (center == BiomeHelper.COLD_OCEAN_ID) return BiomeHelper.DEEP_COLD_OCEAN_ID;
                if (center == BiomeHelper.FROZEN_OCEAN_ID) return BiomeHelper.DEEP_FROZEN_OCEAN_ID;
            }
        }

        return center;
    }
}
