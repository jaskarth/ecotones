package supercoder79.ecotones.world.layers.generation;

import net.minecraft.util.Identifier;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.Ecotones;

public enum MountainSmallEdgeLayer implements CrossSamplingLayer {
    INSTANCE;

    public static final Identifier PEAKS = new Identifier("ecotones", "mountain_peaks");
    public static final Identifier MONTANE_FIELDS = new Identifier("ecotones", "montane_fields");
    public static final Identifier SPARSE_APLINE_FOREST = new Identifier("ecotones", "sparse_alpine_forest");
    public static final Identifier FOOTHILLS = new Identifier("ecotones", "lush_foothills");

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        int peaks = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(PEAKS));
        int foothills = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(FOOTHILLS));

        if (center == peaks) {
            if (n == foothills || e == foothills || s == foothills || w == foothills) {
                return Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(MONTANE_FIELDS));
            }
        } else if (center == foothills) {
            if (n == peaks || e == peaks || s == peaks || w == peaks) {
                return Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(SPARSE_APLINE_FOREST));
            }
        }

        return center;
    }
}
