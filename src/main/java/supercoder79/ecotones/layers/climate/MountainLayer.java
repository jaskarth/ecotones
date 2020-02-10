package supercoder79.ecotones.layers.climate;

import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.layers.seed.SeedLayer;
import supercoder79.ecotones.noise.OpenSimplexNoise;

import java.util.LinkedHashMap;
import java.util.Map;

public enum MountainLayer implements IdentitySamplingLayer, SeedLayer {
    INSTANCE;

    private OpenSimplexNoise mountainNoise;
    public static Map<Integer, Integer[]> Biome2MountainBiomeMap = new LinkedHashMap<>();

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

    @Override
    public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent, long seed) {
        mountainNoise = new OpenSimplexNoise(seed);
        return this.create(context, parent);
    }
}
