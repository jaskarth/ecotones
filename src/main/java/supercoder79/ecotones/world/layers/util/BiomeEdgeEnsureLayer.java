package supercoder79.ecotones.world.layers.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.DiagonalCrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.biome.special.ThePitsBiome;

public enum BiomeEdgeEnsureLayer implements DiagonalCrossSamplingLayer {
    INSTANCE;

    private static final int PITS = Registry.BIOME.getRawId(ThePitsBiome.INSTANCE);
    private static final int PITS_EDGE = Registry.BIOME.getRawId(ThePitsBiome.EDGE);

    @Override
    public int sample(LayerRandomnessSource context, int sw, int se, int ne, int nw, int center) {
        if (sw == PITS || se == PITS || ne == PITS || nw == PITS) {
            if (sw == PITS && se == PITS && ne == PITS && nw == PITS) {
                return center;
            }
            return PITS_EDGE;
        }

        return center;
    }
}
