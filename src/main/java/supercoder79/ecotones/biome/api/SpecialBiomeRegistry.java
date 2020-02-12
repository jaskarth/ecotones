package supercoder79.ecotones.biome.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

public class SpecialBiomeRegistry {
    public static Map<Integer, IntFunction<Boolean>> biomes = new HashMap<>();

    public static void register(int id, IntFunction<Boolean> rule) {
        biomes.put(id, rule);
    }
}
