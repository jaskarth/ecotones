package supercoder79.ecotones.layers.util;

import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum LandLayer implements InitLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int x, int z) {
        if (x < 8 && z < 8 && x > -8 && z > -8) {
            return 1;
        }
        return context.nextInt(2) == 0 ? 1 : 0;
    }
}
