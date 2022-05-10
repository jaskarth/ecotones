package supercoder79.ecotones.world.layers.util;

import supercoder79.ecotones.world.layers.system.layer.type.InitLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;

public enum PlainsOnlyLayer implements InitLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int x, int y) {
        return 1;
    }
}
