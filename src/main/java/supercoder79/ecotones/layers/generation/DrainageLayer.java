package supercoder79.ecotones.layers.generation;

import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.*;
import supercoder79.ecotones.biome.SwampBiomes;
import supercoder79.ecotones.layers.seed.SeedLayer;
import supercoder79.ecotones.noise.OpenSimplexNoise;

import java.util.Random;

public enum  DrainageLayer implements ParentedLayer, IdentityCoordinateTransformer, SeedLayer {
    INSTANCE;

    private OpenSimplexNoise drainageNoise;

    private double offsetX = 0;
    private double offsetZ = 0;

    @Override
    public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        int sample = parent.sample(x, z);
        double drainage = drainageNoise.sample(x + offsetX, z + offsetZ)*1.25;
        if (drainage < -0.65) {
            Integer id = SwampBiomes.Biome2SwampBiomeMap.get(sample);
            return id != null ? id : sample;
        }
        return sample;
    }

    @Override
    public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent, long seed) {
        Random random = new Random(seed - 80);
        offsetX = (random.nextDouble()-0.5)*10000;
        offsetZ = (random.nextDouble()-0.5)*10000;
        drainageNoise = new OpenSimplexNoise(seed - 80);
        return this.create(context, parent);
    }
}
