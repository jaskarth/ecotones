package supercoder79.ecotones.world.layers.generation;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;

import java.util.Map;

public enum SmallSpecialBiomesLayer implements MergingLayer, IdentityCoordinateTransformer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        for (Map.Entry<RegistryKey<Biome>, Integer> biomeMap : BiomeRegistries.SMALL_SPECIAL_BIOMES.entrySet()) {
            if (context.nextInt(biomeMap.getValue()) == 0) {
                //try to see if the position is valid for spawning
                if (BiomeRegistries.SPECIAL_BIOMES.get(biomeMap.getKey()).apply(sampler2.sample(x, z))) {
                    return Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(biomeMap.getKey()));
                }
            }
        }

        return sampler1.sample(x, z);
    }
}
