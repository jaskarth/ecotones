package supercoder79.ecotones.layers.generation;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.biome.special.*;

public enum BaseSpecialBiomesLayer implements IdentitySamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int sample) {
        if (context.nextInt(100) == 0) return Registry.BIOME.getRawId(ThePitsBiome.INSTANCE);
        if (context.nextInt(100) == 0) return Registry.BIOME.getRawId(UluruBiome.INSTANCE);
        if (context.nextInt(79) == 0) return Registry.BIOME.getRawId(OasisBiome.INSTANCE);

        // less rarer, so at the end
        if (context.nextInt(10) == 0) return Registry.BIOME.getRawId(FlowerPrairieBiome.INSTANCE);
        if (context.nextInt(10) == 0) return Registry.BIOME.getRawId(FlowerPrairieBiome.HILLY);
        if (context.nextInt(10) == 0) return Registry.BIOME.getRawId(FlowerPrairieBiome.MOUNTAINOUS);

        return sample;
    }
}
