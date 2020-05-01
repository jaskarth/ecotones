package supercoder79.ecotones.world.layers.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum DeepOceanLayer implements CrossSamplingLayer {
    INSTANCE;

    private static final int WARM_OCEAN_ID = Registry.BIOME.getRawId(Biomes.WARM_OCEAN);
    private static final int LUKEWARM_OCEAN_ID = Registry.BIOME.getRawId(Biomes.LUKEWARM_OCEAN);
    private static final int OCEAN_ID = Registry.BIOME.getRawId(Biomes.OCEAN);
    private static final int COLD_OCEAN_ID = Registry.BIOME.getRawId(Biomes.COLD_OCEAN);
    private static final int FROZEN_OCEAN_ID = Registry.BIOME.getRawId(Biomes.FROZEN_OCEAN);
    private static final int DEEP_WARM_OCEAN_ID = Registry.BIOME.getRawId(Biomes.DEEP_WARM_OCEAN);
    private static final int DEEP_LUKEWARM_OCEAN_ID = Registry.BIOME.getRawId(Biomes.DEEP_LUKEWARM_OCEAN);
    private static final int DEEP_OCEAN_ID = Registry.BIOME.getRawId(Biomes.DEEP_OCEAN);
    private static final int DEEP_COLD_OCEAN_ID = Registry.BIOME.getRawId(Biomes.DEEP_COLD_OCEAN);
    private static final int DEEP_FROZEN_OCEAN_ID = Registry.BIOME.getRawId(Biomes.DEEP_FROZEN_OCEAN);

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        if (isShallowOcean(center)) {
            int i = 0;
            if (isShallowOcean(n)) {
                ++i;
            }

            if (isShallowOcean(e)) {
                ++i;
            }

            if (isShallowOcean(w)) {
                ++i;
            }

            if (isShallowOcean(s)) {
                ++i;
            }

            if (i > 3) {
                if (center == OCEAN_ID) return DEEP_OCEAN_ID;
                if (center == WARM_OCEAN_ID) return DEEP_WARM_OCEAN_ID;
                if (center == LUKEWARM_OCEAN_ID) return DEEP_LUKEWARM_OCEAN_ID;
                if (center == COLD_OCEAN_ID) return DEEP_COLD_OCEAN_ID;
                if (center == FROZEN_OCEAN_ID) return DEEP_FROZEN_OCEAN_ID;
            }
        }

        return center;
    }

    protected static boolean isShallowOcean(int id) {
        return id == WARM_OCEAN_ID || id == LUKEWARM_OCEAN_ID || id == OCEAN_ID || id == COLD_OCEAN_ID || id == FROZEN_OCEAN_ID;
    }
}
