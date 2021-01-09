package supercoder79.ecotones.world.layers.util;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.world.biome.technical.RiverBiome;

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
