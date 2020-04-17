package supercoder79.ecotones.layers.generation;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.biome.special.*;

public enum BigSpecialBiomesLayer implements IdentitySamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int sample) {
        if (context.nextInt(400) == 0) return Registry.BIOME.getRawId(GreenSpiresBiome.INSTANCE);
        if (context.nextInt(300) == 0) return Registry.BIOME.getRawId(HazelGroveBiome.INSTANCE);
        if (context.nextInt(150) == 0) return Registry.BIOME.getRawId(WastelandBiome.INSTANCE);
        if (context.nextInt(60) == 0) return Registry.BIOME.getRawId(PinePeaksBiome.INSTANCE);
        if (context.nextInt(45) == 0) return Registry.BIOME.getRawId(CloverFieldsBiome.INSTANCE);
        if (context.nextInt(30) == 0) return Registry.BIOME.getRawId(WoodlandThicketBiome.INSTANCE);

        return sample;
    }
}
