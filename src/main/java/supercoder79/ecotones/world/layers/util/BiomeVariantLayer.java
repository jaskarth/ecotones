package supercoder79.ecotones.world.layers.util;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;

public enum BiomeVariantLayer implements IdentitySamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int sample) {
        RegistryKey<Biome> key = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(sample)).get();
        Integer chance = BiomeRegistries.BIOME_VARANT_CHANCE.get(key);
        if (chance != null) {
            RegistryKey<Biome>[] variants = BiomeRegistries.BIOME_VARIANTS.get(key);
            return Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(variants[context.nextInt(variants.length)]));
        }

        return sample;
    }
}
