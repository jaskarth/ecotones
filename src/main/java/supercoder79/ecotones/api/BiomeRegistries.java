package supercoder79.ecotones.api;

import it.unimi.dsi.fastutil.ints.IntComparators;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.biome.EcotonesBiome;

import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class BiomeRegistries {
    public static final Map<Integer, IntFunction<Boolean>> specialBiomes = new HashMap<>();
    public static final Map<Integer, Integer> bigSpecialBiomes = new HashMap<>();
    public static final Map<Integer, Integer> smallSpecialBiomes = new HashMap<>();
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

    public static void registerBigSpecialBiome(EcotonesBiome biome, int chance) {
        bigSpecialBiomes.put(Registry.BIOME.getRawId(biome), chance);
    }

    public static void registerSmallSpecialBiome(EcotonesBiome biome, int chance) {
        smallSpecialBiomes.put(Registry.BIOME.getRawId(biome), chance);
    }

    public static void registerNoBeachBiome(int id) {
        noBeachBiomes.add(id);
    }

    public static void compile() {
        //TODO: this doesnt seem like its doing anything
        Map<Integer, Integer> tempMap = new HashMap<>();
        bigSpecialBiomes.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> tempMap.put(x.getKey(), x.getValue()));
        bigSpecialBiomes.clear();

        tempMap.forEach(bigSpecialBiomes::put);

        tempMap.clear();

        smallSpecialBiomes.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> tempMap.put(x.getKey(), x.getValue()));
        smallSpecialBiomes.clear();

        tempMap.forEach(smallSpecialBiomes::put);
    }
}
