package supercoder79.ecotones.api;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.biome.EcotonesBiome;

import java.util.*;
import java.util.function.IntFunction;

public class BiomeRegistries {
    public static final Map<Integer, IntFunction<Boolean>> SPECIAL_BIOMES = new HashMap<>();
    public static final Map<Integer, Integer> BIG_SPECIAL_BIOMES = new HashMap<>();
    public static final Map<Integer, Integer> SMALL_SPECIAL_BIOMES = new HashMap<>();
    public static final List<Integer> NO_BEACH_BIOMES = new ArrayList<>();

    public static final Map<Integer, Integer> BIOME_VARANT_CHANCE = new HashMap<>();
    public static final Map<Integer, int[]> BIOME_VARIANTS = new HashMap<>();

    public static void registerSpecialBiome(Biome biome, IntFunction<Boolean> rule) {
        SPECIAL_BIOMES.put(Registry.BIOME.getRawId(biome), rule);
    }

    //yes i know this is stupid shuddup
    //TODO: remove this and replace with other
    public static void registerAllSpecial(IntFunction<Boolean> rule, int... ids) {
        for (int id : ids) {
            SPECIAL_BIOMES.put(id, rule);
        }
    }

    public static void registerAllSpecial(IntFunction<Boolean> rule, Biome... biomes) {
        for (Biome biome : biomes) {
            SPECIAL_BIOMES.put(Registry.BIOME.getRawId(biome), rule);
        }
    }

    public static void registerBigSpecialBiome(Biome biome, int chance) {
        BIG_SPECIAL_BIOMES.put(Registry.BIOME.getRawId(biome), chance);
    }

    public static void registerSmallSpecialBiome(Biome biome, int chance) {
        SMALL_SPECIAL_BIOMES.put(Registry.BIOME.getRawId(biome), chance);
    }

    public static void registerBiomeVariantChance(Biome biome, int chance) {
        BIOME_VARANT_CHANCE.put(Registry.BIOME.getRawId(biome), chance);
    }

    public static void registerBiomeVariants(Biome parent, Biome... variants) {
        int[] ids = new int[variants.length];
        for (int i = 0; i < variants.length; i++) {
            ids[i] = Registry.BIOME.getRawId(variants[i]);
        }

        BIOME_VARIANTS.put(Registry.BIOME.getRawId(parent), ids);
    }

    public static void registerNoBeachBiome(Biome biome) {
        NO_BEACH_BIOMES.add(Registry.BIOME.getRawId(biome));
    }

    public static void compile() {
        //TODO: this doesnt seem like its doing anything
        Map<Integer, Integer> tempMap = new HashMap<>();
        BIG_SPECIAL_BIOMES.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> tempMap.put(x.getKey(), x.getValue()));
        BIG_SPECIAL_BIOMES.clear();

        tempMap.forEach(BIG_SPECIAL_BIOMES::put);

        tempMap.clear();

        SMALL_SPECIAL_BIOMES.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> tempMap.put(x.getKey(), x.getValue()));
        SMALL_SPECIAL_BIOMES.clear();

        tempMap.forEach(SMALL_SPECIAL_BIOMES::put);
    }
}
