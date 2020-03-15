package supercoder79.ecotones.layers.generation;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.biome.special.HazelGroveBiome;
import supercoder79.ecotones.biome.special.OasisBiome;
import supercoder79.ecotones.biome.special.ThePitsBiome;
import supercoder79.ecotones.biome.special.UluruBiome;

public enum BaseSpecialBiomesLayer implements IdentitySamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int sample) {
        if (context.nextInt(79) == 0) return Registry.BIOME.getRawId(OasisBiome.INSTANCE);
        if (context.nextInt(100) == 0) return Registry.BIOME.getRawId(ThePitsBiome.INSTANCE);
        if (context.nextInt(100) == 0) return Registry.BIOME.getRawId(UluruBiome.INSTANCE);

        //TODO: move this to its own layer
        if (context.nextInt(4) == 0) {
            if (sample == Registry.BIOME.getRawId(HazelGroveBiome.INSTANCE)) {
                switch (context.nextInt(3)) {
                    case 0:
                        return Registry.BIOME.getRawId(HazelGroveBiome.CLEARING);
                    case 1:
                        return Registry.BIOME.getRawId(HazelGroveBiome.HILLY);
                    case 2:
                        return Registry.BIOME.getRawId(HazelGroveBiome.CLEARING_HILLY);
                }
            }
        }
        return sample;
    }
}
