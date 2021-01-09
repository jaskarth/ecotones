package supercoder79.ecotones.api;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.world.layers.generation.MountainLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

public class BiomeRegistries {
    public static final Map<RegistryKey<Biome>, IntFunction<Boolean>> SPECIAL_BIOMES = new HashMap<>();
    public static final Map<RegistryKey<Biome>, Integer> BIG_SPECIAL_BIOMES = new HashMap<>();
    public static final Map<RegistryKey<Biome>, Integer> SMALL_SPECIAL_BIOMES = new HashMap<>();
    public static final List<RegistryKey<Biome>> NO_BEACH_BIOMES = new ArrayList<>();
    public static final List<RegistryKey<Biome>> NO_RIVER_BIOMES = new ArrayList<>();

    public static final Map<RegistryKey<Biome>, Integer> BIOME_VARANT_CHANCE = new HashMap<>();
    public static final Map<RegistryKey<Biome>, RegistryKey<Biome>[]> BIOME_VARIANTS = new HashMap<>();

    public static void registerSpecialBiome(Biome biome, IntFunction<Boolean> rule) {
        SPECIAL_BIOMES.put(BuiltinRegistries.BIOME.getKey(biome).get(), rule);
    }
    public static void registerAllSpecial(IntFunction<Boolean> rule, Biome... biomes) {
        for (Biome biome : biomes) {
            SPECIAL_BIOMES.put(BuiltinRegistries.BIOME.getKey(biome).get(), rule);
        }
    }

    public static void registerBigSpecialBiome(Biome biome, int chance) {
        BIG_SPECIAL_BIOMES.put(BuiltinRegistries.BIOME.getKey(biome).get(), chance);
    }

    public static void registerSmallSpecialBiome(Biome biome, int chance) {
        SMALL_SPECIAL_BIOMES.put(BuiltinRegistries.BIOME.getKey(biome).get(), chance);
    }

    public static void registerBiomeVariantChance(Biome biome, int chance) {
        BIOME_VARANT_CHANCE.put(BuiltinRegistries.BIOME.getKey(biome).get(), chance);
    }

    public static void registerBiomeVariants(Biome parent, Biome... variants) {
        RegistryKey<Biome>[] ids = new RegistryKey[variants.length];
        for (int i = 0; i < variants.length; i++) {
            ids[i] = BuiltinRegistries.BIOME.getKey(variants[i]).get();
        }

        BIOME_VARIANTS.put(BuiltinRegistries.BIOME.getKey(parent).get(), ids);
    }

    public static void registerMountains(Biome base, Biome hilly, Biome mountainous) {
        MountainLayer.BIOME_TO_MOUNTAINS.put(BuiltinRegistries.BIOME.getKey(base).get(), new RegistryKey[]{BuiltinRegistries.BIOME.getKey(hilly).get(), BuiltinRegistries.BIOME.getKey(mountainous).get()});
    }

    public static void registerNoBeachBiome(Biome biome) {
        NO_BEACH_BIOMES.add(BuiltinRegistries.BIOME.getKey(biome).get());
    }

    public static void registerNoBeachBiomes(Biome... biomes) {
        for (Biome biome : biomes) {
            registerNoBeachBiome(biome);
        }
    }

    public static void registerNoRiverBiomes(Biome... biomes) {
        for (Biome biome : biomes) {
            registerNoRiverBiome(biome);
        }
    }

    public static void registerNoRiverBiome(Biome biome) {
        NO_RIVER_BIOMES.add(BuiltinRegistries.BIOME.getKey(biome).get());
    }
}
