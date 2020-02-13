package supercoder79.ecotones.layers.generation;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.biome.special.OasisBiome;
import supercoder79.ecotones.biome.special.ThePitsBiome;

public enum BaseSpecialBiomesLayer implements IdentitySamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int sample) {
        if (context.nextInt(79) == 0) return Registry.BIOME.getRawId(OasisBiome.INSTANCE);
        if (context.nextInt(100) == 0) return Registry.BIOME.getRawId(ThePitsBiome.INSTANCE);
        return sample;
    }
}
