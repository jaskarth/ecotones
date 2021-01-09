package supercoder79.ecotones.world.layers.util;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.world.biome.special.ThePitsBiome;

public enum BiomeMerger implements MergingLayer, IdentityCoordinateTransformer {
    INSTANCE;

    public static final Identifier ID = new Identifier("ecotones", "the_pits");
    public static final Identifier EDGE = new Identifier("ecotones", "the_pits_edge");

    @Override
    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        int PITS = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(ID));
        int PITS_EDGE = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(EDGE));

        int landSample = sampler1.sample(x, z);
        int biomeSample = sampler2.sample(x, z);

        if (landSample == 1) {
            return biomeSample;
        } else {
            if (Ecotones.REGISTRY.get(landSample).getCategory() == Biome.Category.BEACH) {
                if (BiomeRegistries.NO_BEACH_BIOMES.contains(Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(biomeSample)).get())) {
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
}
