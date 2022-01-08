package supercoder79.ecotones.world.layers.generation;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.ClimateType;
import supercoder79.ecotones.world.layers.util.MergingCrossSamplingLayer;

import java.util.List;

public enum MountainSmallEdgeLayer implements MergingCrossSamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center, int climate) {
        List<RegistryKey<Biome>> peaks = BiomeRegistries.TYPED_MOUNTAIN_BIOMES.get(ClimateType.MOUNTAIN_PEAKS);
        List<RegistryKey<Biome>> foothills = BiomeRegistries.TYPED_MOUNTAIN_BIOMES.get(ClimateType.MOUNTAIN_FOOTHILLS);

        RegistryKey<Biome> centerKey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(center)).get();
        RegistryKey<Biome> nkey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(n)).get();
        RegistryKey<Biome> ekey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(e)).get();
        RegistryKey<Biome> skey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(s)).get();
        RegistryKey<Biome> wkey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(w)).get();

        if (peaks.contains(centerKey)) {
            if (foothills.contains(nkey) || foothills.contains(ekey) || foothills.contains(skey) || foothills.contains(wkey)) {
                return Climate.VALUES[climate].pickerFor(ClimateType.MOUNTAIN_PLAINS).choose(context);
            }
        } else if (foothills.contains(centerKey)) {
            if (peaks.contains(nkey) || peaks.contains(ekey) || peaks.contains(skey) || peaks.contains(wkey)) {
                return Climate.VALUES[climate].pickerFor(ClimateType.MOUNTAIN_FOOTHILLS_UPPER).choose(context);
            }
        }

        return center;
    }
}
