package supercoder79.ecotones.world.layers.generation;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.*;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.api.ClimateType;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.world.layers.seed.MergingSeedLayer;
import supercoder79.ecotones.world.layers.seed.SeedLayer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public enum MountainLayer implements MergingLayer, IdentityCoordinateTransformer, MergingSeedLayer {
    INSTANCE;

    public OpenSimplexNoise mountainNoise;
    public static final Map<RegistryKey<Biome>, RegistryKey<Biome>[]> BIOME_TO_MOUNTAINS = new LinkedHashMap<>();

    public double mountainOffsetX = 0;
    public double mountainOffsetZ = 0;

    @Override
    public int sample(LayerRandomnessSource context, LayerSampler biomes, LayerSampler climates, int x, int z) {
        int sample = biomes.sample(x, z);
        RegistryKey<Biome> key = Ecotones.REGISTRY.getKey(Ecotones.REGISTRY.get(sample)).get();

        // fail early if no mountain biomes were registered
        if (!BIOME_TO_MOUNTAINS.containsKey(key)) {
            return sample;
        }

        double mountain = mountainNoise.sample((x + mountainOffsetX) / 3f, (z + mountainOffsetZ) / 3f) * 1.25;
        // Make mountains spawn less frequently near the spawn
        mountain *= distFactor(x, z);

        if (mountain > 0.75) {
            return Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(BIOME_TO_MOUNTAINS.get(key)[1]));
        }

        if (mountain > 0.5) {
            return Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(BIOME_TO_MOUNTAINS.get(key)[0]));
        }

        if (mountain < -0.8) {
            return Climate.VALUES[climates.sample(x, z)].pickerFor(ClimateType.MOUNTAIN_PEAKS).choose(context);
        }

        return sample;
    }

    @Override
    public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> layer1, LayerFactory<R> layer2, long seed) {
        Random random = new Random(seed + 80);
        mountainOffsetX = (random.nextDouble() - 0.5) * 10000;
        mountainOffsetZ = (random.nextDouble() - 0.5) * 10000;
        mountainNoise = new OpenSimplexNoise(seed + 90);
        return this.create(context, layer1, layer2);
    }

    public static double distFactor(int x, int z) {
        return Math.max(0, 1 - (4.0 / (x * x + z * z)));
    }
}
