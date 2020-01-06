package supercoder79.ecotones.layers;

import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum LandLayer implements InitLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int x, int y) {
        return context.nextInt(2) == 0 ? 1 : 0;
    }
}
