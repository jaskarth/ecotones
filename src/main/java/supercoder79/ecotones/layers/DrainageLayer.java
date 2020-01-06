package supercoder79.ecotones.layers;

import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.biome.SwampBiomes;
import supercoder79.ecotones.noise.OpenSimplexNoise;

public class DrainageLayer implements IdentitySamplingLayer {

    public static OpenSimplexNoise drainageNoise;

    public DrainageLayer(long seed) {
        drainageNoise = new OpenSimplexNoise(seed);
//        Biome2SwampBiomeMap.put();
    }

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
        return parent.sample(x, z);
    }
}
