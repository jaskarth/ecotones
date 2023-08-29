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

public final class TraverseCompat {
    private static Identifier id(String path) {
        return new Identifier("traverse", path);
    }

    private static RegistryKey<Biome> key(String path) {
        return RegistryKey.of(RegistryKeys.BIOME, id(path));
    }

    public static void associateGenData() {
        BiomeGenData.LOOKUP.put(key("autumnal_woods"), new BiomeGenData( 0.2, 0.1, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("coniferous_forest"), new BiomeGenData( 0.2, 0.1, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("lush_swamp"), new BiomeGenData( -0.1, 0.05, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("flatlands"), new BiomeGenData( 0.2, 0.10, 0.95, 1.6, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("woodlands"), new BiomeGenData( 0.2, 0.1, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
    }

    public static void init() {
        // autumnal woods
        Biome autumnalForest = Ecotones.REGISTRY.get(id("autumnal_woods"));
        Climate.WARM_MILD.add(autumnalForest, 0.2);

        // coniferous forest
        Biome coniferousForest = Ecotones.REGISTRY.get(id("coniferous_forest"));
        Climate.WARM_HUMID.add(coniferousForest, 0.2);

        // lush swamp
        Biome lushSwamp = Ecotones.REGISTRY.get(id("lush_swamp"));
        Climate.HOT_VERY_HUMID.add(lushSwamp, 0.3);
        BiomeRegistries.registerNoBeachBiome(lushSwamp);

        // flatlands
        Biome meadow = Ecotones.REGISTRY.get(id("flatlands"));
        Climate.WARM_MODERATE.add(meadow, 0.3);
        Climate.WARM_MILD.add(meadow, 0.3);

        // woodlands
        Biome woodlands = Ecotones.REGISTRY.get(id("woodlands"));
        Climate.HOT_VERY_HUMID.add(woodlands, 0.3);
    }
}