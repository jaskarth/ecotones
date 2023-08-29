package com.jaskarth.ecotones.world.worldgen.layers.generation;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.MergingLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.IdentityCoordinateTransformer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampler;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.api.BiomeRegistries;

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
