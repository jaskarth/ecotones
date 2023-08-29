package com.jaskarth.ecotones.world.worldgen.layers.util;

import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.InitLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampleContext;

// River gen- doesn't need biome
public enum UncontextedLandLayer implements InitLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int x, int z) {
        if (x < 1 && z < 1 && x > -1 && z > -1) {
            int sample = context.nextInt(200000) + 1;
            // Reset seed
            ((LayerSampleContext<?>)context).initSeed(x, z);
            return sample;
//            return 1;
        }
//        return context.nextInt(2) == 0 ? 1 : 0;
        if (context.nextInt(2) == 0) {
            int sample = context.nextInt(200000) + 1;
            ((LayerSampleContext<?>)context).initSeed(x, z);

            // Simple discard
            context.nextInt(2);
            return sample;
//            return 1;
        }
        
        return 0;
    }
}
