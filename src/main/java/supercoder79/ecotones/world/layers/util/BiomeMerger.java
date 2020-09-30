package supercoder79.ecotones.world.layers.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.world.biome.special.ThePitsBiome;

public enum BiomeMerger implements MergingLayer, IdentityCoordinateTransformer {
    INSTANCE;

    private static final int PITS = Registry.BIOME.getRawId(ThePitsBiome.INSTANCE);
    private static final int PITS_EDGE = Registry.BIOME.getRawId(ThePitsBiome.EDGE);

    @Override
    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        int landSample = sampler1.sample(x, z);
        int biomeSample = sampler2.sample(x, z);
        if (landSample == 1) {
            return biomeSample;
        }

        if (Registry.BIOME.get(landSample).getCategory() == Biome.Category.BEACH) {
            if (BiomeRegistries.NO_BEACH_BIOMES.contains(biomeSample)) {
                return biomeSample;
            }
        }

        //TODO: stop hardcoding these
        if (biomeSample == PITS || biomeSample == PITS_EDGE) {
            return biomeSample;
        }

        return landSample;
    }
}
