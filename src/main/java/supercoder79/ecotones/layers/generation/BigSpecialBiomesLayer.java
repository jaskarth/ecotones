package supercoder79.ecotones.layers.generation;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.biome.special.GreenSpiresBiome;

public enum BigSpecialBiomesLayer implements IdentitySamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int sample) {
        if (context.nextInt(400) == 0) return Registry.BIOME.getRawId(GreenSpiresBiome.INSTANCE);
        return sample;
    }
}
