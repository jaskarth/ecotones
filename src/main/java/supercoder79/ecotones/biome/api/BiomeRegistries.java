package supercoder79.ecotones.biome.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

public class BiomeRegistries {
    public static Map<Integer, IntFunction<Boolean>> specialBiomes = new HashMap<>();
    public static List<Integer> noBeachBiomes = new ArrayList<>();

    public static void registerSpecialBiome(int id, IntFunction<Boolean> rule) {
        specialBiomes.put(id, rule);
    }

    public static void registerNoBeachBiome(int id) {
        noBeachBiomes.add(id);
    }
}
