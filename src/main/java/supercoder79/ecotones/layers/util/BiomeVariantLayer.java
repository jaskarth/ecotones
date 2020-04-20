package supercoder79.ecotones.layers.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.biome.special.*;

public enum BiomeVariantLayer implements IdentitySamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int sample) {
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

            if (sample == Registry.BIOME.getRawId(WoodlandThicketBiome.INSTANCE)) {
                switch (context.nextInt(3)) {
                    case 0:
                        return Registry.BIOME.getRawId(WoodlandThicketBiome.CLEARING);
                    case 1:
                        return Registry.BIOME.getRawId(WoodlandThicketBiome.HILLY);
                    case 2:
                        return Registry.BIOME.getRawId(WoodlandThicketBiome.HILLY_CLEARING);
                }
            }
        }

        if (context.nextInt(2) == 0) {
            if (sample == Registry.BIOME.getRawId(WastelandBiome.INSTANCE)) {
                switch (context.nextInt(5)) {
                    case 0:
                        return Registry.BIOME.getRawId(WastelandBiome.THICKET);
                    case 1:
                        return Registry.BIOME.getRawId(WastelandBiome.HILLS);
                    case 2:
                        return Registry.BIOME.getRawId(WastelandBiome.FLATS);
                    case 3:
                        return Registry.BIOME.getRawId(WastelandBiome.SHRUB);
                    case 4:
                        return Registry.BIOME.getRawId(WastelandBiome.DEPTHS);
                }
            }

            if (sample == Registry.BIOME.getRawId(CloverFieldsBiome.INSTANCE)) {
                switch (context.nextInt(4)) {
                    case 0:
                        return Registry.BIOME.getRawId(CloverFieldsBiome.THICKET);
                    case 1:
                        return Registry.BIOME.getRawId(CloverFieldsBiome.HILLS);
                    case 2:
                        return Registry.BIOME.getRawId(CloverFieldsBiome.FLATS);
                    case 3:
                        return Registry.BIOME.getRawId(CloverFieldsBiome.SHRUB);
                }
            }

            if (sample == Registry.BIOME.getRawId(PoplarForestBiome.INSTANCE)) {
                switch (context.nextInt(6)) {
                    case 0:
                        return Registry.BIOME.getRawId(PoplarForestBiome.THICKET);
                    case 1:
                        return Registry.BIOME.getRawId(PoplarForestBiome.HILLS);
                    case 2:
                        return Registry.BIOME.getRawId(PoplarForestBiome.FLATS);
                    case 3:
                        return Registry.BIOME.getRawId(PoplarForestBiome.SHRUB);
                    case 4:
                        return Registry.BIOME.getRawId(PoplarForestBiome.OAK);
                    case 5:
                        return Registry.BIOME.getRawId(PoplarForestBiome.BIRCH);
                }
            }
        }

        return sample;
    }
}
