package supercoder79.ecotones.layers.generation;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.biome.base.HumidityLayer1Biomes;
import supercoder79.ecotones.biome.base.HumidityLayer2Biomes;
import supercoder79.ecotones.layers.seed.SeedInitLayer;
import supercoder79.ecotones.noise.OpenSimplexNoise;

import java.util.Map;
import java.util.Random;

public enum ClimateLayers implements InitLayer, SeedInitLayer {
    INSTANCE;

    private static OpenSimplexNoise humidityNoise;
    private static OpenSimplexNoise temperatureNoise;

    private double offsetX = 0;
    private double offsetZ = 0;
    private double offsetX2 = 0;
    private double offsetZ2 = 0;

    @Override
    public int sample(LayerRandomnessSource context, int x, int z) {
        double humidity = MathHelper.clamp(humidityNoise.sample((x + offsetX) / 2.5f, (z + offsetZ) / 2.5f)*1.25, -1, 1);
        double temperature = temperatureNoise.sample((x + offsetX2) / 6f, (z + offsetZ2) / 6f);
        if (temperature > 0) {
            // === Hot Biomes ===
            if (humidity > 0.8) {
                return Climate.HOT_RAINFOREST.choose(context);
            }
            if (humidity > 0.6) {
                return Climate.HOT_VERY_HUMID.choose(context);
            }
            if (humidity > 0.4) {
                return Climate.HOT_HUMID.choose(context);
            }
            if (humidity > 0.2) {
                return Climate.HOT_MILD.choose(context);
            }
            if (humidity > -0.2) {
                return Climate.HOT_MODERATE.choose(context);
            }
            if (humidity > -0.4) {
                return Climate.HOT_DRY.choose(context);
            }
            if (humidity > -0.6) {
                return Climate.HOT_VERY_DRY.choose(context);
            }

            return Climate.HOT_DESERT.choose(context);
        } else {
            // === Warm Biomes ===
            if (humidity > 0.8) {
                return Climate.WARM_RAINFOREST.choose(context);
            }
            if (humidity > 0.6) {
                return Climate.WARM_VERY_HUMID.choose(context);
            }
            if (humidity > 0.4) {
                return Climate.WARM_HUMID.choose(context);
            }
            if (humidity > 0.2) {
                return Climate.WARM_MILD.choose(context);
            }
            if (humidity > -0.2) {
                return Climate.WARM_MODERATE.choose(context);
            }
            if (humidity > -0.4) {
                return Climate.WARM_DRY.choose(context);
            }
            if (humidity > -0.6) {
                return Climate.WARM_VERY_DRY.choose(context);
            }

            return Climate.WARM_DESERT.choose(context);
        }
    }

    @Override
    public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, long seed) {
        Random random = new Random(seed);
        offsetX = (random.nextDouble()-0.5)*10000;
        offsetZ = (random.nextDouble()-0.5)*10000;
        offsetX2 = (random.nextDouble()-0.5)*10000;
        offsetZ2 = (random.nextDouble()-0.5)*10000;
        humidityNoise = new OpenSimplexNoise(seed);
        temperatureNoise = new OpenSimplexNoise(seed - 79);
        return this.create(context);
    }
}
