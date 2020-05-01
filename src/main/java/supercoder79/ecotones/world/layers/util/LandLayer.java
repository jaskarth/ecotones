package supercoder79.ecotones.world.layers.util;

import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum LandLayer implements InitLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int x, int z) {
        if (x < 1 && z < 1 && x > -1 && z > -1) {
            return 1;
        }
        return context.nextInt(2) == 0 ? 1 : 0;
    }
}
