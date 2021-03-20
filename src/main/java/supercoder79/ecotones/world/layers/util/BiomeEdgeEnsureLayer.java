package supercoder79.ecotones.world.layers.util;

import net.minecraft.util.Identifier;
import net.minecraft.world.biome.layer.type.DiagonalCrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.Ecotones;

public enum BiomeEdgeEnsureLayer implements DiagonalCrossSamplingLayer {
    INSTANCE;

    public static final Identifier ID = new Identifier("ecotones", "the_pits");
    public static final Identifier EDGE = new Identifier("ecotones", "the_pits_edge");

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        int PITS = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(ID));
        int PITS_EDGE = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(EDGE));

        if (n == PITS || e == PITS || s == PITS || w == PITS) {
            if (n == PITS && e == PITS && s == PITS && w == PITS) {
                return center;
            }
            return PITS_EDGE;
        }

        return center;
    }
}
