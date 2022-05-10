package supercoder79.ecotones.world.layers.util;

import net.minecraft.world.biome.BiomeKeys;
import supercoder79.ecotones.api.BiomeIdManager;
import supercoder79.ecotones.world.layers.system.layer.type.InitLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;

public enum LandLayer implements InitLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int x, int z) {
        if (x < 1 && z < 1 && x > -1 && z > -1) {
            return 1;
        }
        return context.nextInt(2) == 0 ? 1 : BiomeIdManager.getId(BiomeKeys.OCEAN);
    }
}
