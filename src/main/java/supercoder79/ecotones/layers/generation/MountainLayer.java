package supercoder79.ecotones.layers.generation;

import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.*;
import supercoder79.ecotones.layers.seed.SeedLayer;
import supercoder79.ecotones.noise.OpenSimplexNoise;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public enum MountainLayer implements ParentedLayer, IdentityCoordinateTransformer, SeedLayer {
    INSTANCE;

    private OpenSimplexNoise mountainNoise;
    public static Map<Integer, Integer[]> Biome2MountainBiomeMap = new LinkedHashMap<>();

    private double offsetX = 0;
    private double offsetZ = 0;

    @Override
    public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        int sample = parent.sample(x, z);

        // fail early if no mountain biomes were registered
        if (!Biome2MountainBiomeMap.containsKey(sample)) {
            return sample;
        }

        double mountain = mountainNoise.sample((x + offsetX) / 3f, (z + offsetZ) / 3f)*1.25;
        if (mountain > 0.75) {
            Integer id = Biome2MountainBiomeMap.get(sample)[1];
            return id != null ? id : sample;
        }
        if (mountain > 0.5) {
            Integer id = Biome2MountainBiomeMap.get(sample)[0];
            return id != null ? id : sample;
        }

        return sample;
    }

    @Override
    public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent, long seed) {
        Random random = new Random(seed + 80);
        offsetX = (random.nextDouble()-0.5)*10000;
        offsetZ = (random.nextDouble()-0.5)*10000;
        mountainNoise = new OpenSimplexNoise(seed + 90);
        return this.create(context, parent);
    }
}
