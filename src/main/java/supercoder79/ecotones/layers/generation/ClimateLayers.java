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

public enum ClimateLayers implements InitLayer, SeedInitLayer {
    INSTANCE;

    private static OpenSimplexNoise humidityNoise;
    private static OpenSimplexNoise temperatureNoise;

    @Override
    public int sample(LayerRandomnessSource context, int x, int z) {
        double humidity = humidityNoise.sample(x / 5f, z / 5f)*1.25;
        double temperature = temperatureNoise.sample(x / 15f, z / 15f);
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
        humidityNoise = new OpenSimplexNoise(seed);
        temperatureNoise = new OpenSimplexNoise(seed - 79);
        return this.create(context);
    }
}
