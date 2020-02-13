package supercoder79.ecotones.layers.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.biome.special.ThePitsBiome;

public enum BiomeEdgeLayer implements CrossSamplingLayer {
    INSTANCE;

    private static final int PITS = Registry.BIOME.getRawId(ThePitsBiome.INSTANCE);
    private static final int PITS_EDGE = Registry.BIOME.getRawId(ThePitsBiome.EDGE);

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        if (n == PITS || e == PITS || s == PITS || w == PITS) {
            if (n == PITS && e == PITS && s == PITS && w == PITS) {
                return center;
            }
            return PITS_EDGE;
        }

        return center;
    }
}
