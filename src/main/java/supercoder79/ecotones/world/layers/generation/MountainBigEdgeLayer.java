package supercoder79.ecotones.world.layers.generation;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.ClimateType;
import supercoder79.ecotones.world.layers.util.MergingCrossSamplingLayer;

import java.util.List;

public enum MountainBigEdgeLayer implements MergingCrossSamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center, int climate) {
        List<RegistryKey<Biome>> biomes = BiomeRegistries.TYPED_MOUNTAIN_BIOMES.get(ClimateType.MOUNTAIN_PEAKS);

        RegistryKey<Biome> nkey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(n)).get();
        RegistryKey<Biome> ekey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(e)).get();
        RegistryKey<Biome> skey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(s)).get();
        RegistryKey<Biome> wkey = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(w)).get();

        if (biomes.contains(nkey) || biomes.contains(ekey) || biomes.contains(skey) || biomes.contains(wkey)) {
            if (biomes.contains(nkey) && biomes.contains(ekey) && biomes.contains(skey) && biomes.contains(wkey)) {
                return center;
            }

            return Climate.VALUES[climate].pickerFor(ClimateType.MOUNTAIN_FOOTHILLS).choose(context);
        }

        return center;
    }
}
