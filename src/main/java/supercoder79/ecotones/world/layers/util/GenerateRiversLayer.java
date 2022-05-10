package supercoder79.ecotones.world.layers.util;

import net.minecraft.util.Identifier;
import supercoder79.ecotones.world.layers.system.layer.type.CrossSamplingLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.Ecotones;

public enum GenerateRiversLayer implements CrossSamplingLayer {
    INSTANCE;

    private static final Identifier ID = new Identifier("ecotones", "river");

    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        int i = isValidForRiver(center);
        return i == isValidForRiver(w) && i == isValidForRiver(n) && i == isValidForRiver(e) && i == isValidForRiver(s) ? -1 : Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(ID));
    }

    private static int isValidForRiver(int value) {
        return value >= 2 ? 2 + (value & 1) : value;
    }
}
