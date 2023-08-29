package com.jaskarth.ecotones.world.worldgen.layers.util;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.MergingLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.IdentityCoordinateTransformer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampler;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.api.BiomeRegistries;

public enum BiomeMergeLayer implements MergingLayer, IdentityCoordinateTransformer {
    INSTANCE;

    public static final Identifier ID = new Identifier("ecotones", "chasm");
    public static final Identifier EDGE = new Identifier("ecotones", "chasm_edge");

    @Override
    public int sample(LayerRandomnessSource context, LayerSampler landSampler, LayerSampler biomeSampler, int x, int z) {
        int chasm = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(ID));
        int chasmEdge = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(EDGE));

        int landSample = landSampler.sample(x, z);
        int biomeSample = biomeSampler.sample(x, z);

        if (landSample == 1) {
            return biomeSample;
        } else {
            RegistryKey<Biome> key = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(biomeSample)).get();
            RegistryKey<Biome> landKey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(landSample)).get();
            if (BiomeRegistries.BEACH_LIST.contains(landKey)) {
                if (BiomeRegistries.NO_BEACH_BIOMES.contains(key)) {
                    return biomeSample;
                }
            }

            if (BiomeRegistries.MOUNTAIN_BIOMES.containsKey(key)) {
                return biomeSample;
            }

            //TODO: stop hardcoding these
            if (biomeSample == chasm || biomeSample == chasmEdge) {
                // Make edge where they meet
                if (landSampler.sample(x + 4, z) == 1 || landSampler.sample(x - 4, z) == 1 || landSampler.sample(x, z + 4) == 1 || landSampler.sample(x, z - 4) == 1) {
                    return chasmEdge;
                }
            }

            return landSample;
        }
    }
}
