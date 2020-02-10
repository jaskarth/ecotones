package supercoder79.ecotones.layers;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.biome.HumidityLayer1Biomes;
import supercoder79.ecotones.biome.HumidityLayer2Biomes;
import supercoder79.ecotones.noise.OpenSimplexNoise;

import java.util.Map;

public class ClimateLayers implements InitLayer {
    private static OpenSimplexNoise humidityNoise;
    private static OpenSimplexNoise temperatureNoise;

    public ClimateLayers(long seed) {
        humidityNoise = new OpenSimplexNoise(seed);
        temperatureNoise = new OpenSimplexNoise(seed - 79);
    }

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
}
