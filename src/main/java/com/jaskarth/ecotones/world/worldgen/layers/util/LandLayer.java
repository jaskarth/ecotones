package com.jaskarth.ecotones.world.worldgen.layers.util;

import net.minecraft.world.biome.BiomeKeys;
import com.jaskarth.ecotones.api.BiomeIdManager;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.InitLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;

public enum LandLayer implements InitLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int x, int z) {
        if (x < 1 && z < 1 && x > -1 && z > -1) {
            return 1;
        }
        return context.nextInt(2) == 0 ? 1 : BiomeIdManager.getId(BiomeKeys.OCEAN);
    }
}
