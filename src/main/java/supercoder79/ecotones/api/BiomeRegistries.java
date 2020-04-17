package supercoder79.ecotones.api;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.biome.EcotonesBiome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

public class BiomeRegistries {
    public static final Map<Integer, IntFunction<Boolean>> specialBiomes = new HashMap<>();
    public static final List<Integer> noBeachBiomes = new ArrayList<>();

    public static void registerSpecialBiome(int id, IntFunction<Boolean> rule) {
        specialBiomes.put(id, rule);
    }

    //yes i know this is stupid shuddup
    //TODO: remove this and replace with other
    public static void registerAllSpecial(IntFunction<Boolean> rule, int... ids) {
        for (int id : ids) {
            specialBiomes.put(id, rule);
        }
    }

    public static void registerAllSpecial(IntFunction<Boolean> rule, EcotonesBiome... biomes) {
        for (EcotonesBiome biome : biomes) {
            specialBiomes.put(Registry.BIOME.getRawId(biome), rule);
        }
    }

    public static void registerNoBeachBiome(int id) {
        noBeachBiomes.add(id);
    }
}
