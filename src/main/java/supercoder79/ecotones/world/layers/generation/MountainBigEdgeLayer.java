package supercoder79.ecotones.world.layers.generation;

import net.minecraft.util.Identifier;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.Ecotones;

public enum MountainBigEdgeLayer implements CrossSamplingLayer {
    INSTANCE;

    public static final Identifier ID = new Identifier("ecotones", "mountain_peaks");
    public static final Identifier EDGE = new Identifier("ecotones", "lush_foothills");

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        int peaks = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(ID));
        int foothills = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(EDGE));

        if (n == peaks || e == peaks || s == peaks || w == peaks) {
            if (n == peaks && e == peaks && s == peaks && w == peaks) {
                return center;
            }
            return foothills;
        }

        return center;
    }
}
