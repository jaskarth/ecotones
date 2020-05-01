package supercoder79.ecotones.world.layers.util;

import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.api.BiomeRegistries;

import java.util.Map;

public enum BiomeVariantLayer implements IdentitySamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int sample) {

        for (Map.Entry<Integer, Integer> biomeMap : BiomeRegistries.BIOME_VARANT_CHANCE.entrySet()) {
            if (biomeMap.getKey() == sample) {
                // see if we're allowed to set a biome variant here
                if (context.nextInt(biomeMap.getValue()) == 0) {
                    // get a random variant from the registry
                    return BiomeRegistries.BIOME_VARIANTS.get(biomeMap.getKey())[context.nextInt(BiomeRegistries.BIOME_VARIANTS.get(biomeMap.getKey()).length)];
                }
            }
        }

        return sample;
    }
}
