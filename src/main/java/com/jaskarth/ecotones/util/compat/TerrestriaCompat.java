package com.jaskarth.ecotones.util.compat;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.world.worldgen.gen.BiomeGenData;
import com.jaskarth.ecotones.world.worldgen.surface.system.ConfiguredSurfaceBuilder;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;

public final class TerrestriaCompat {
    private static Identifier id(String path) {
        return new Identifier("terrestria", path);
    }

    private static RegistryKey<Biome> key(String path) {
        return RegistryKey.of(RegistryKeys.BIOME, id(path));
    }

    public static void associateGenData() {
        BiomeGenData.LOOKUP.put(key("cypress_forest"), new BiomeGenData(0.2, 0.15, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("cypress_swamp"), new BiomeGenData(-0.1, 0.05, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("hemlock_rainforest"), new BiomeGenData(0.2, 0.1, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("japanese_maple_forest"), new BiomeGenData(0.2, 0.125, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("rainbow_rainforest"), new BiomeGenData(-0.02, 0.15, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("redwood_forest"), new BiomeGenData(0.2, 0.15, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("sakura_forest"), new BiomeGenData(0.2, 0.125, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
    }

    public static void init() {
        // cypress forest
        Biome cypressForest = Ecotones.REGISTRY.get(id("cypress_forest"));
        Climate.WARM_MILD.add(cypressForest, 0.2);

        // cypress swamp
        Biome cypressSwamp = Ecotones.REGISTRY.get(id("cypress_swamp"));
        Climate.WARM_HUMID.add(cypressSwamp, 0.2);
        BiomeRegistries.registerNoBeachBiome(cypressSwamp);

        // hemlock rainforest
        Biome hemlockRainforest = Ecotones.REGISTRY.get(id("hemlock_rainforest"));
        Climate.WARM_VERY_HUMID.add(hemlockRainforest, 0.2);
//        BiomeRegistries.registerBiomeVariantChance(hemlockRainforest, 2);
//        BiomeRegistries.registerBiomeVariants(hemlockRainforest, hemlockRainforest, Ecotones.REGISTRY.get(id("hemlock_clearing")));

        // japanese maple forest
        Biome japaneseMapleForest = Ecotones.REGISTRY.get(id("japanese_maple_forest"));
        Climate.WARM_HUMID.add(japaneseMapleForest, 0.2);
//        BiomeRegistries.registerBiomeVariantChance(japaneseMapleForest, 2);
//        BiomeRegistries.registerBiomeVariants(japaneseMapleForest, japaneseMapleForest, Ecotones.REGISTRY.get(id("wooded_japanese_maple_hills")));

        // lush redwood forest
        Biome lushRedwoodForest = Ecotones.REGISTRY.get(id("lush_redwood_forest"));
        Climate.WARM_HUMID.add(lushRedwoodForest, 0.2);
//        BiomeRegistries.registerBiomeVariantChance(lushRedwoodForest, 2);
//        BiomeRegistries.registerBiomeVariants(lushRedwoodForest, lushRedwoodForest, Ecotones.REGISTRY.get(id("lush_redwood_clearing")));

        // rainbow rainforest
        Biome rainbowRainforest = Ecotones.REGISTRY.get(id("rainbow_rainforest"));
        Climate.WARM_RAINFOREST.add(rainbowRainforest, 0.2);
//        BiomeRegistries.registerBiomeVariantChance(rainbowRainforest, 3);
//        BiomeRegistries.registerBiomeVariants(rainbowRainforest, Ecotones.REGISTRY.get(id("rainbow_rainforest_mountains")), Ecotones.REGISTRY.get(id("rainbow_rainforest_lake")));

        // redwood forest
        Biome redwoodForest = Ecotones.REGISTRY.get(id("redwood_forest"));
        Climate.WARM_VERY_HUMID.add(redwoodForest, 0.2);
//        BiomeRegistries.registerBiomeVariantChance(redwoodForest, 2);
//        BiomeRegistries.registerBiomeVariants(redwoodForest, redwoodForest, Ecotones.REGISTRY.get(id("redwood_clearing")));

        // sakura forest
        Biome sakuraForest = Ecotones.REGISTRY.get(id("sakura_forest"));
        Climate.WARM_MILD.add(sakuraForest, 0.2);
//        BiomeRegistries.registerBiomeVariantChance(sakuraForest, 2);
//        BiomeRegistries.registerBiomeVariants(sakuraForest, sakuraForest, Ecotones.REGISTRY.get(id("wooded_sakura_hills")));
    }
}