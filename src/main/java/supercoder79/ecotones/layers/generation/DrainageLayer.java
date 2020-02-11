package supercoder79.ecotones.layers.generation;

import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.biome.SwampBiomes;
import supercoder79.ecotones.layers.seed.SeedLayer;
import supercoder79.ecotones.noise.OpenSimplexNoise;

public enum  DrainageLayer implements IdentitySamplingLayer, SeedLayer {
    INSTANCE;

    private OpenSimplexNoise drainageNoise;

    @Override
    public int sample(LayerRandomnessSource context, int value) {
        return 0;
    }

    @Override
    public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        int sample = parent.sample(x, z);
        double drainage = drainageNoise.sample(x, z)*1.25;
        if (drainage < -0.65) {
            Integer id = SwampBiomes.Biome2SwampBiomeMap.get(sample);
            return id != null ? id : sample;
        }
        return sample;
    }

    @Override
    public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent, long seed) {
        drainageNoise = new OpenSimplexNoise(seed);
        return this.create(context, parent);
    }
}
