package supercoder79.ecotones.util.compat;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.world.gen.BiomeGenData;
import supercoder79.ecotones.world.surface.system.ConfiguredSurfaceBuilder;
import supercoder79.ecotones.world.surface.system.SurfaceBuilder;

public final class TraverseCompat {
    private static Identifier id(String path) {
        return new Identifier("traverse", path);
    }

    private static RegistryKey<Biome> key(String path) {
        return RegistryKey.of(Registry.BIOME_KEY, id(path));
    }

    public static void associateGenData() {
        BiomeGenData.LOOKUP.put(key("arid_highlands"), new BiomeGenData( 0.2, 0.1, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("autumnal_woods"), new BiomeGenData( 0.2, 0.1, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("autumnal_wooded_hills"), new BiomeGenData( 0.3, 0.25, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("coniferous_forest"), new BiomeGenData( 0.2, 0.1, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("coniferous_wooded_hills"), new BiomeGenData( 0.3, 0.25, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("high_coniferous_forest"), new BiomeGenData( 1.2, 0.25, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("lush_swamp"), new BiomeGenData( -0.1, 0.05, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("meadow"), new BiomeGenData( 0.2, 0.10, 0.95, 1.6, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(key("woodlands"), new BiomeGenData( 0.2, 0.1, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
    }

    public static void init() {
        // arid highlands
        Biome aridHighlands = Ecotones.REGISTRY.get(id("arid_highlands"));
        Climate.WARM_DRY.add(aridHighlands, 0.2);
        Climate.WARM_VERY_DRY.add(aridHighlands, 0.2);

        // autumnal woods
        Biome autumnalForest = Ecotones.REGISTRY.get(id("autumnal_woods"));
        Climate.WARM_MILD.add(autumnalForest, 0.2);
        BiomeRegistries.registerBiomeVariantChance(autumnalForest, 2);
        BiomeRegistries.registerBiomeVariants(autumnalForest, autumnalForest, Ecotones.REGISTRY.get(id("autumnal_wooded_hills")));

        // coniferous forest
        Biome coniferousForest = Ecotones.REGISTRY.get(id("coniferous_forest"));
        Climate.WARM_HUMID.add(coniferousForest, 0.2);
        BiomeRegistries.registerBiomeVariantChance(coniferousForest, 3);
        BiomeRegistries.registerBiomeVariants(coniferousForest, Ecotones.REGISTRY.get(id("coniferous_wooded_hills")), Ecotones.REGISTRY.get(id("high_coniferous_forest")));

        // lush swamp
        Biome lushSwamp = Ecotones.REGISTRY.get(id("lush_swamp"));
        Climate.HOT_VERY_HUMID.add(lushSwamp, 0.3);
        BiomeRegistries.registerNoBeachBiome(lushSwamp);

        // meadow
        Biome meadow = Ecotones.REGISTRY.get(id("meadow"));
        Climate.WARM_MODERATE.add(meadow, 0.3);
        Climate.WARM_MILD.add(meadow, 0.3);

        // woodlands
        Biome woodlands = Ecotones.REGISTRY.get(id("woodlands"));
        Climate.HOT_VERY_HUMID.add(woodlands, 0.3);
    }
}