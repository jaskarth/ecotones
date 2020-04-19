package supercoder79.ecotones.layers.generation;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.biome.special.*;

import java.util.Map;

public enum BigSpecialBiomesLayer implements MergingLayer, IdentityCoordinateTransformer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        for (Map.Entry<Integer, Integer> biomeMap : BiomeRegistries.bigSpecialBiomes.entrySet()) {
            if (context.nextInt(biomeMap.getValue()) == 0) {
                //try to see if the position is valid for spawning
                if (BiomeRegistries.specialBiomes.get(biomeMap.getKey()).apply(sampler2.sample(x, z))) {
                    return biomeMap.getKey();
                }
            }
        }

        return sampler1.sample(x, z);
    }
}
