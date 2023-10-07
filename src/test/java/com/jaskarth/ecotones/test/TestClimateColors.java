package com.jaskarth.ecotones.test;

import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.util.noise.OpenSimplexNoise;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class TestClimateColors {
    private static final double RAINFOREST_ABOVE = 0.5;
    private static final double V_HUMID_ABOVE = 0.33;
    private static final double HUMID_ABOVE = 0.175;
    private static final double MILD_ABOVE = 0;
    private static final double MODERATE_ABOVE = -0.175;
    private static final double DRY_ABOVE = -0.33;
    private static final double V_DRY_ABOVE = -0.5;

    private static final DecimalFormat FMT = new DecimalFormat("#.###");
    public static void main(String[] args) {
        int seed = 193913713;
        Random random = new Random(seed);
        double humidityOffsetX = (random.nextDouble() - 0.5) * 10000;
        double humidityOffsetZ = (random.nextDouble() - 0.5) * 10000;
        double temperatureOffsetX = (random.nextDouble() - 0.5) * 10000;
        double temperatureOffsetZ = (random.nextDouble() - 0.5) * 10000;
        OpenSimplexNoise humidityNoise = new OpenSimplexNoise(seed);
        OpenSimplexNoise temperatureNoise = new OpenSimplexNoise(seed - 79);
        Map<Climate, Integer> map = new HashMap<>();
        AtomicInteger total = new AtomicInteger();
        ImageDumper.dumpImage("climates.png", 2048, (x, z) -> {
            double humidity = humidityNoise.sample((x + humidityOffsetX) / 4.0, (z + humidityOffsetZ) / 4.0);
            double temperature = temperatureNoise.sample((x + temperatureOffsetX) / 4.0, (z + temperatureOffsetZ) / 4.0);

            Climate climate = get(humidity, temperature);
            map.compute(climate, (k, v) -> v == null ? 1 : v + 1);
            total.incrementAndGet();

            return ImageDumper.getIntFromColor(getColorForHumidity(humidity), temperature > 0 ? 200 : 100, 0);
        });

        int i = total.get();
        for (Climate key : Climate.values()) {
            if (map.containsKey(key)) {
                Integer value = map.get(key);
                System.out.println(key + " " + value + " (" + FMT.format((value / (double)i) * 100) + "%)");
            }
        }
    }

    public static int getColorForHumidity(double humidity) {
        // === Warm Biomes ===
        if (humidity > RAINFOREST_ABOVE) {
            return 0;
        }
        if (humidity > V_HUMID_ABOVE) {
            return 30;
        }
        if (humidity > HUMID_ABOVE) {
            return 60;
        }
        if (humidity > MILD_ABOVE) {
            return 90;
        }
        if (humidity > MODERATE_ABOVE) {
            return 120;
        }
        if (humidity > DRY_ABOVE) {
            return 150;
        }
        if (humidity > V_DRY_ABOVE) {
            return 180;
        }

        return 210;
    }
    
    public static Climate get(double humidity, double temperature) {
        if (temperature > 0) {
            // === Hot Biomes ===
            if (humidity > RAINFOREST_ABOVE) {
                return Climate.HOT_RAINFOREST;
            }
            if (humidity > V_HUMID_ABOVE) {
                return Climate.HOT_VERY_HUMID;
            }
            if (humidity > HUMID_ABOVE) {
                return Climate.HOT_HUMID;
            }
            if (humidity > MILD_ABOVE) {
                return Climate.HOT_MILD;
            }
            if (humidity > MODERATE_ABOVE) {
                return Climate.HOT_MODERATE;
            }
            if (humidity > DRY_ABOVE) {
                return Climate.HOT_DRY;
            }
            if (humidity > V_DRY_ABOVE) {
                return Climate.HOT_VERY_DRY;
            }

            return Climate.HOT_DESERT;
        } else {
            // === Warm Biomes ===
            if (humidity > RAINFOREST_ABOVE) {
                return Climate.WARM_RAINFOREST;
            }
            if (humidity > V_HUMID_ABOVE) {
                return Climate.WARM_VERY_HUMID;
            }
            if (humidity > HUMID_ABOVE) {
                return Climate.WARM_HUMID;
            }
            if (humidity > MILD_ABOVE) {
                return Climate.WARM_MILD;
            }
            if (humidity > MODERATE_ABOVE) {
                return Climate.WARM_MODERATE;
            }
            if (humidity > DRY_ABOVE) {
                return Climate.WARM_DRY;
            }
            if (humidity > V_DRY_ABOVE) {
                return Climate.WARM_VERY_DRY;
            }

            return Climate.WARM_DESERT;
        }
    }
}
