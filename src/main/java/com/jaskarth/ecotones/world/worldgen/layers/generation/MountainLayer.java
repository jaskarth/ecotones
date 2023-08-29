package com.jaskarth.ecotones.world.worldgen.layers.generation;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.MergingLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.*;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.ClimateType;
import com.jaskarth.ecotones.util.noise.OpenSimplexNoise;
import com.jaskarth.ecotones.world.worldgen.layers.seed.MergingSeedLayer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public enum MountainLayer implements MergingLayer, IdentityCoordinateTransformer, MergingSeedLayer {
    INSTANCE;

    public OpenSimplexNoise mountainNoise;
    public OpenSimplexNoise mountainRangesNoise;

    public double mountainOffsetX = 0;
    public double mountainOffsetZ = 0;

    @Override
    public int sample(LayerRandomnessSource context, LayerSampler biomes, LayerSampler climates, int x, int z) {
        int sample = biomes.sample(x, z);

        double mountain = mountainNoise.sample((x + mountainOffsetX) / 3f, (z + mountainOffsetZ) / 3f) * 1.25;
        double mountainRanges = 1 - Math.abs(MountainLayer.INSTANCE.mountainRangesNoise.sample((x - MountainLayer.INSTANCE.mountainOffsetX) / 6f, (z - MountainLayer.INSTANCE.mountainOffsetZ) / 6f));

        // Make mountains spawn less frequently near the spawn
        mountain *= distFactor(x, z);

        if (mountain < -0.8 + (mountainRanges * 0.2)) {
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
        mountainRangesNoise = new OpenSimplexNoise(seed + 190);
        return this.create(context, layer1, layer2);
    }

    public static double distFactor(int x, int z) {
        return Math.max(0, 1 - (4.0 / (x * x + z * z)));
    }
}
