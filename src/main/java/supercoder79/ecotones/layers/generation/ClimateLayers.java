package supercoder79.ecotones.layers.generation;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.biome.HumidityLayer1Biomes;
import supercoder79.ecotones.biome.HumidityLayer2Biomes;
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
        double humidity = humidityNoise.sample((x + offsetX) / 2.5f, (z + offsetZ) / 2.5f);
        double temperature = temperatureNoise.sample((x + offsetX2) / 6f, (z + offsetZ2) / 6f);
        if (temperature > 0) {
            for (Map.Entry<Double, Integer> biome : HumidityLayer1Biomes.Humidity2BiomeMap.entrySet()) {
                if (humidity > biome.getKey()) return biome.getValue();
            }
        } else {
            for (Map.Entry<Double, Integer> biome : HumidityLayer2Biomes.Humidity2BiomeMap.entrySet()) {
                if (humidity > biome.getKey()) return biome.getValue();
            }
        }

        return Registry.BIOME.getRawId(HumidityLayer1Biomes.DESERT_BIOME);
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
