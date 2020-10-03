package supercoder79.ecotones.world.layers.generation;

import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.world.layers.seed.SeedLayer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public enum MountainLayer implements ParentedLayer, IdentityCoordinateTransformer, SeedLayer {
    INSTANCE;

    private OpenSimplexNoise mountainNoise;
    public static final Map<Integer, Integer[]> BIOME_TO_MOUNTAINS = new LinkedHashMap<>();

    private double mountainOffsetX = 0;
    private double mountainOffsetZ = 0;

    @Override
    public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        int sample = parent.sample(x, z);

        // fail early if no mountain biomes were registered
        if (!BIOME_TO_MOUNTAINS.containsKey(sample)) {
            return sample;
        }

        double mountain = mountainNoise.sample((x + mountainOffsetX) / 3f, (z + mountainOffsetZ) / 3f)*1.25;
        if (mountain > 0.75) {
            Integer id = BIOME_TO_MOUNTAINS.get(sample)[1];
            return id != null ? id : sample;
        }
        if (mountain > 0.5) {
            Integer id = BIOME_TO_MOUNTAINS.get(sample)[0];
            return id != null ? id : sample;
        }

        return sample;
    }

    @Override
    public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent, long seed) {
        Random random = new Random(seed + 80);
        mountainOffsetX = (random.nextDouble() - 0.5) * 10000;
        mountainOffsetZ = (random.nextDouble() - 0.5) * 10000;
        mountainNoise = new OpenSimplexNoise(seed + 90);
        return this.create(context, parent);
    }
}
