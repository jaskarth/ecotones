package com.jaskarth.ecotones.world.worldgen.layers.util;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.IdentitySamplingLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.api.BiomeRegistries;

public enum BiomeVariantLayer implements IdentitySamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int sample) {
        RegistryKey<Biome> key = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(sample)).get();
        Integer chance = BiomeRegistries.BIOME_VARIANT_CHANCE.get(key);
        if (chance != null && context.nextInt(chance) == 0) {
            RegistryKey<Biome>[] variants = BiomeRegistries.BIOME_VARIANTS.get(key);
            return Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(variants[context.nextInt(variants.length)]));
        }

        return sample;
    }
}
