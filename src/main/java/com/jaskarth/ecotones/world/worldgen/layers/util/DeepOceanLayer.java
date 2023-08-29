package com.jaskarth.ecotones.world.worldgen.layers.util;

import net.minecraft.world.biome.BiomeKeys;
import com.jaskarth.ecotones.api.BiomeIdManager;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.CrossSamplingLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.world.worldgen.biome.BiomeHelper;

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
                if (center == BiomeIdManager.getId(BiomeKeys.OCEAN)) return BiomeIdManager.getId(BiomeKeys.DEEP_OCEAN);
                if (center == BiomeIdManager.getId(BiomeKeys.WARM_OCEAN)) return BiomeIdManager.getId(BiomeKeys.WARM_OCEAN);
                if (center == BiomeIdManager.getId(BiomeKeys.LUKEWARM_OCEAN)) return BiomeIdManager.getId(BiomeKeys.DEEP_LUKEWARM_OCEAN);
                if (center == BiomeIdManager.getId(BiomeKeys.COLD_OCEAN)) BiomeIdManager.getId(BiomeKeys.DEEP_COLD_OCEAN);
                if (center == BiomeIdManager.getId(BiomeKeys.FROZEN_OCEAN)) return BiomeIdManager.getId(BiomeKeys.DEEP_FROZEN_OCEAN);
            }
        }

        return center;
    }
}
