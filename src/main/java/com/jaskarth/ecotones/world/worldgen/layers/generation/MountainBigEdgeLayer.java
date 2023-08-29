package com.jaskarth.ecotones.world.worldgen.layers.generation;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.ClimateType;
import com.jaskarth.ecotones.world.worldgen.layers.util.MergingCrossSamplingLayer;

import java.util.List;

public enum MountainBigEdgeLayer implements MergingCrossSamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center, int climate) {
        List<RegistryKey<Biome>> biomes = BiomeRegistries.TYPED_MOUNTAIN_BIOMES.get(ClimateType.MOUNTAIN_PEAKS);

        RegistryKey<Biome> centerKey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(center)).get();
        RegistryKey<Biome> nkey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(n)).get();
        RegistryKey<Biome> ekey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(e)).get();
        RegistryKey<Biome> skey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(s)).get();
        RegistryKey<Biome> wkey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(w)).get();

        if (biomes.contains(centerKey)) {
            if (!(biomes.contains(nkey) && biomes.contains(ekey) && biomes.contains(skey) && biomes.contains(wkey))) {
                return Climate.VALUES[climate].pickerFor(ClimateType.MOUNTAIN_FOOTHILLS).choose(context);
            }
        }

        return center;
    }
}
