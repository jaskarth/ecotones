package supercoder79.ecotones.layers.generation;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.biome.HotSpringsBiome;
import supercoder79.ecotones.biome.VolcanicBiome;
import supercoder79.ecotones.layers.seed.SeedLayer;
import supercoder79.ecotones.noise.OpenSimplexNoise;

public enum VolcanismLayer implements IdentitySamplingLayer, SeedLayer {
    INSTANCE;

    private OpenSimplexNoise volcanismNoise;

    @Override
    public int sample(LayerRandomnessSource context, int value) {
        return 0;
    }

    @Override
    public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        int sample = parent.sample(x, z);
        double volcanism = volcanismNoise.sample(x, z)*1.25;
        if (volcanism < -0.8) {
            return Registry.BIOME.getRawId(VolcanicBiome.INSTANCE);
        }
        if (volcanism > 0.8) {
            return Registry.BIOME.getRawId(HotSpringsBiome.INSTANCE);
        }
        return sample;
    }


    @Override
    public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent, long seed) {
        volcanismNoise = new OpenSimplexNoise(seed);
        return this.create(context, parent);
    }
}
