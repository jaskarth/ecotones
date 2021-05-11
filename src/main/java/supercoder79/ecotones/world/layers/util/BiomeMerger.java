package supercoder79.ecotones.world.layers.util;

import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;

public enum BiomeMerger implements MergingLayer, IdentityCoordinateTransformer {
    INSTANCE;

    public static final Identifier ID = new Identifier("ecotones", "chasm");
    public static final Identifier EDGE = new Identifier("ecotones", "chasm_edge");

    @Override
    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        int chasm = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(ID));
        int chasmEdge = Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(EDGE));

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
            if (biomeSample == chasm || biomeSample == chasmEdge) {
                return biomeSample;
            }

            return landSample;
        }
    }
}
