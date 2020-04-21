package supercoder79.ecotones.layers.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.biome.technical.BeachBiome;

public enum ShorelineLayer implements CrossSamplingLayer {
    INSTANCE;

    private static final int WARM_OCEAN_ID = Registry.BIOME.getRawId(Biomes.WARM_OCEAN);
    private static final int LUKEWARM_OCEAN_ID = Registry.BIOME.getRawId(Biomes.LUKEWARM_OCEAN);
    private static final int OCEAN_ID = Registry.BIOME.getRawId(Biomes.OCEAN);
    private static final int COLD_OCEAN_ID = Registry.BIOME.getRawId(Biomes.COLD_OCEAN);
    private static final int FROZEN_OCEAN_ID = Registry.BIOME.getRawId(Biomes.FROZEN_OCEAN);

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        if (BiomeRegistries.NO_BEACH_BIOMES.contains(center)) return center;

        if (isShallowOcean(n) || isShallowOcean(e) || isShallowOcean(s) || isShallowOcean(w)) {
            if (!isShallowOcean(center)) {
                return Registry.BIOME.getRawId(BeachBiome.INSTANCE);
            }
        }
        return center;
    }

    protected static boolean isShallowOcean(int id) {
        return id == WARM_OCEAN_ID || id == LUKEWARM_OCEAN_ID || id == OCEAN_ID || id == COLD_OCEAN_ID || id == FROZEN_OCEAN_ID;
    }
}
