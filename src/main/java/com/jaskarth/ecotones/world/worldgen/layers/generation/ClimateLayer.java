package com.jaskarth.ecotones.world.worldgen.layers.generation;

import net.minecraft.util.math.MathHelper;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.type.InitLayer;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerFactory;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerRandomnessSource;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampleContext;
import com.jaskarth.ecotones.world.worldgen.layers.system.layer.util.LayerSampler;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.util.noise.OpenSimplexNoise;
import com.jaskarth.ecotones.world.worldgen.layers.seed.SeedInitLayer;

import java.util.Random;

public enum ClimateLayer implements InitLayer, SeedInitLayer {
    INSTANCE;

    public static OpenSimplexNoise humidityNoise;
    public static OpenSimplexNoise temperatureNoise;

    public double humidityOffsetX = 0;
    public double humidityOffsetZ = 0;
    public double temperatureOffsetX = 0;
    public double temperatureOffsetZ = 0;

    @Override
    public int sample(LayerRandomnessSource context, int x, int z) {
        double humidity = MathHelper.clamp(ClimateLayer.humidityNoise.sample((x + ClimateLayer.INSTANCE.humidityOffsetX) / 6.0, (z + ClimateLayer.INSTANCE.humidityOffsetZ) / 6.0) * 1.1, -1, 1);
        double temperature = ClimateLayer.temperatureNoise.sample((x + ClimateLayer.INSTANCE.temperatureOffsetX) / 8.0, (z + ClimateLayer.INSTANCE.temperatureOffsetZ) / 8.0);
        if (temperature > 0) {
            // === Hot Biomes ===
            if (humidity > 0.8) {
                return Climate.HOT_RAINFOREST.ordinal();
            }
            if (humidity > 0.6) {
                return Climate.HOT_VERY_HUMID.ordinal();
            }
            if (humidity > 0.4) {
                return Climate.HOT_HUMID.ordinal();
            }
            if (humidity > 0.2) {
                return Climate.HOT_MILD.ordinal();
            }
            if (humidity > -0.2) {
                return Climate.HOT_MODERATE.ordinal();
            }
            if (humidity > -0.4) {
                return Climate.HOT_DRY.ordinal();
            }
            if (humidity > -0.6) {
                return Climate.HOT_VERY_DRY.ordinal();
            }

            return Climate.HOT_DESERT.ordinal();
        } else {
            // === Warm Biomes ===
            if (humidity > 0.8) {
                return Climate.WARM_RAINFOREST.ordinal();
            }
            if (humidity > 0.6) {
                return Climate.WARM_VERY_HUMID.ordinal();
            }
            if (humidity > 0.4) {
                return Climate.WARM_HUMID.ordinal();
            }
            if (humidity > 0.2) {
                return Climate.WARM_MILD.ordinal();
            }
            if (humidity > -0.2) {
                return Climate.WARM_MODERATE.ordinal();
            }
            if (humidity > -0.4) {
                return Climate.WARM_DRY.ordinal();
            }
            if (humidity > -0.6) {
                return Climate.WARM_VERY_DRY.ordinal();
            }

            return Climate.WARM_DESERT.ordinal();
        }
    }

    @Override
    public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, long seed) {
        Random random = new Random(seed);
        humidityOffsetX = (random.nextDouble() - 0.5) * 10000;
        humidityOffsetZ = (random.nextDouble() - 0.5) * 10000;
        temperatureOffsetX = (random.nextDouble() - 0.5) * 10000;
        temperatureOffsetZ = (random.nextDouble() - 0.5) * 10000;
        humidityNoise = new OpenSimplexNoise(seed);
        temperatureNoise = new OpenSimplexNoise(seed - 79);
        return this.create(context);
    }
}
