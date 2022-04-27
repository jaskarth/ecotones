package supercoder79.ecotones.api;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.world.edge.EdgeDecorationCollector;
import supercoder79.ecotones.world.edge.EdgeDecorator;
import supercoder79.ecotones.world.layers.generation.MountainLayer;
import supercoder79.ecotones.world.river.deco.RiverDecorationCollector;
import supercoder79.ecotones.world.river.deco.RiverDecorator;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public final class BiomeRegistries {
    public static final Map<RegistryKey<Biome>, IntFunction<Boolean>> SPECIAL_BIOMES = new HashMap<>();
    public static final Map<RegistryKey<Biome>, Integer> BIG_SPECIAL_BIOMES = new HashMap<>();
    public static final Map<RegistryKey<Biome>, Integer> SMALL_SPECIAL_BIOMES = new HashMap<>();
    public static final List<RegistryKey<Biome>> NO_BEACH_BIOMES = new ArrayList<>();
    public static final List<RegistryKey<Biome>> NO_RIVER_BIOMES = new ArrayList<>();
    public static final Map<RegistryKey<Biome>, Double> MOUNTAIN_BIOMES = new HashMap<>();
    public static final Map<ClimateType, List<RegistryKey<Biome>>> TYPED_MOUNTAIN_BIOMES = new HashMap<>();
    static {
        TYPED_MOUNTAIN_BIOMES.put(ClimateType.MOUNTAIN_FOOTHILLS, new ArrayList<>());
        TYPED_MOUNTAIN_BIOMES.put(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, new ArrayList<>());
        TYPED_MOUNTAIN_BIOMES.put(ClimateType.MOUNTAIN_PLAINS, new ArrayList<>());
        TYPED_MOUNTAIN_BIOMES.put(ClimateType.MOUNTAIN_PEAKS, new ArrayList<>());
    }

    public static final Map<RegistryKey<Biome>, Integer> BIOME_VARIANT_CHANCE = new HashMap<>();
    public static final Map<RegistryKey<Biome>, RegistryKey<Biome>[]> BIOME_VARIANTS = new HashMap<>();
    public static final List<RegistryKey<Biome>> SLIME_SPAWN_BIOMES = new ArrayList<>();
    public static final Map<RegistryKey<Biome>, RiverDecorator> RIVER_DECORATORS = new HashMap<>();
    public static final Map<RegistryKey<Biome>, EdgeDecorator> EDGE_DECORATORS = new HashMap<>();

    public static void registerSpecialBiome(Biome biome, IntFunction<Boolean> rule) {
        SPECIAL_BIOMES.put(key(biome), rule);
    }
    public static void registerAllSpecial(IntFunction<Boolean> rule, Biome... biomes) {
        for (Biome biome : biomes) {
            SPECIAL_BIOMES.put(key(biome), rule);
        }
    }

    public static void registerBigSpecialBiome(Biome biome, int chance) {
        BIG_SPECIAL_BIOMES.put(key(biome), chance);
    }

    public static void registerSmallSpecialBiome(Biome biome, int chance) {
        SMALL_SPECIAL_BIOMES.put(key(biome), chance);
    }

    public static void registerBiomeVariantChance(Biome biome, int chance) {
        BIOME_VARIANT_CHANCE.put(key(biome), chance);
    }

    public static void registerRiverDecorator(Biome biome, Consumer<RiverDecorationCollector> acceptor) {
        RiverDecorator decorator = RIVER_DECORATORS.getOrDefault(key(biome), new RiverDecorator());
        acceptor.accept(decorator.getDecorations());
        RIVER_DECORATORS.put(key(biome), decorator);
    }

    public static void registerEdgeDecorator(Biome biome, Consumer<EdgeDecorationCollector> acceptor) {
        EdgeDecorator decorator = EDGE_DECORATORS.getOrDefault(key(biome), new EdgeDecorator());
        acceptor.accept(decorator.getDecorations());
        EDGE_DECORATORS.put(key(biome), decorator);
    }

    public static void registerBiomeVariants(Biome parent, Biome... variants) {
        RegistryKey<Biome>[] ids = new RegistryKey[variants.length];
        for (int i = 0; i < variants.length; i++) {
            ids[i] = key(variants[i]);
        }

        BIOME_VARIANTS.put(key(parent), ids);
    }

    public static void registerMountains(Biome base, Biome hilly, Biome mountainous) {
        MountainLayer.BIOME_TO_MOUNTAINS.put(key(base), new RegistryKey[]{key(hilly), key(mountainous)});
    }

    public static void addMountainBiome(Biome biome) {
        addMountainBiome(biome, 1.0);
    }

    public static void addMountainBiome(Biome biome, double weight) {
        MOUNTAIN_BIOMES.put(key(biome), weight);
    }

    public static void addMountainType(ClimateType type, Biome biome) {
        if (type == ClimateType.REGULAR) {
            throw new IllegalArgumentException("Can't add mountain type of regular!");
        }

        TYPED_MOUNTAIN_BIOMES.get(type).add(key(biome));
    }

    public static void registerNoBeachBiome(Biome biome) {
        NO_BEACH_BIOMES.add(key(biome));
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
        NO_RIVER_BIOMES.add(key(biome));
    }

    public static void registerSlimeSpawnBiome(Biome biome) {
        SLIME_SPAWN_BIOMES.add(key(biome));
    }

    public static void registerSlimeSpawnBiomes(Biome... biomes) {
        for (Biome biome : biomes) {
            registerSlimeSpawnBiome(biome);
        }
    }

    public static RegistryKey<Biome> key(Biome biome) {
        Optional<RegistryKey<Biome>> optional = BuiltinRegistries.BIOME.getKey(biome);

        if (optional.isEmpty()) {
            if (Ecotones.REGISTRY == null) {
                return BiomeKeys.PLAINS; // This really shouldn't exist!!
            }
        }

        return optional.orElseGet(() -> Ecotones.REGISTRY.getKey(biome)
                .orElseThrow(() -> new IllegalStateException("Impossible state when trying to get biome key")));
    }
}