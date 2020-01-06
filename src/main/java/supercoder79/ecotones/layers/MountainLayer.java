package supercoder79.ecotones.layers;

import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.noise.OpenSimplexNoise;

import java.util.LinkedHashMap;
import java.util.Map;

public class MountainLayer implements IdentitySamplingLayer {
    public static OpenSimplexNoise mountainNoise;
    public static Map<Integer, Integer[]> Biome2MountainBiomeMap = new LinkedHashMap<>();

    public MountainLayer(long seed) {
        mountainNoise = new OpenSimplexNoise(seed);
    }

    @Override
    public int sample(LayerRandomnessSource context, int value) {
        return 0;
    }

    @Override
    public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        int sample = parent.sample(x, z);
        double mountain = mountainNoise.sample(x / 5f, z / 5f)*1.25;
        if (mountain > 0.75) {
            Integer id = Biome2MountainBiomeMap.get(sample)[1];
            return id != null ? id : sample;
        }
        if (mountain > 0.5) {
            Integer id = Biome2MountainBiomeMap.get(sample)[0];
            return id != null ? id : sample;
        }

        return parent.sample(x, z);
    }
}
