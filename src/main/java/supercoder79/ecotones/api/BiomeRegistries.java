package supercoder79.ecotones.api;

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

    //yes i know this is stupid shuddup
    public static void registerAllSpecial(IntFunction<Boolean> rule, int... ids) {
        for (int id : ids) {
            specialBiomes.put(id, rule);
        }
    }

    public static void registerNoBeachBiome(int id) {
        noBeachBiomes.add(id);
    }
}
