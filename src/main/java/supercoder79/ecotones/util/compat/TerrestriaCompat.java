package supercoder79.ecotones.util.compat;

import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;

public final class TerrestriaCompat {
    private static Identifier id(String path) {
        return new Identifier("terrestria", path);
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
        BiomeRegistries.registerBiomeVariantChance(hemlockRainforest, 2);
        BiomeRegistries.registerBiomeVariants(hemlockRainforest, hemlockRainforest, Ecotones.REGISTRY.get(id("hemlock_clearing")));

        // japanese maple forest
        Biome japaneseMapleForest = Ecotones.REGISTRY.get(id("japanese_maple_forest"));
        Climate.WARM_HUMID.add(japaneseMapleForest, 0.2);
        BiomeRegistries.registerBiomeVariantChance(japaneseMapleForest, 2);
        BiomeRegistries.registerBiomeVariants(japaneseMapleForest, japaneseMapleForest, Ecotones.REGISTRY.get(id("wooded_japanese_maple_hills")));

        // lush redwood forest
        Biome lushRedwoodForest = Ecotones.REGISTRY.get(id("lush_redwood_forest"));
        Climate.WARM_HUMID.add(lushRedwoodForest, 0.2);
        BiomeRegistries.registerBiomeVariantChance(lushRedwoodForest, 2);
        BiomeRegistries.registerBiomeVariants(lushRedwoodForest, lushRedwoodForest, Ecotones.REGISTRY.get(id("lush_redwood_clearing")));

        // rainbow rainforest
        Biome rainbowRainforest = Ecotones.REGISTRY.get(id("rainbow_rainforest"));
        Climate.WARM_RAINFOREST.add(rainbowRainforest, 0.2);
        BiomeRegistries.registerBiomeVariantChance(rainbowRainforest, 3);
        BiomeRegistries.registerBiomeVariants(rainbowRainforest, Ecotones.REGISTRY.get(id("rainbow_rainforest_mountains")), Ecotones.REGISTRY.get(id("rainbow_rainforest_lake")));

        // redwood forest
        Biome redwoodForest = Ecotones.REGISTRY.get(id("redwood_forest"));
        Climate.WARM_VERY_HUMID.add(redwoodForest, 0.2);
        BiomeRegistries.registerBiomeVariantChance(redwoodForest, 2);
        BiomeRegistries.registerBiomeVariants(redwoodForest, redwoodForest, Ecotones.REGISTRY.get(id("redwood_clearing")));

        // sakura forest
        Biome sakuraForest = Ecotones.REGISTRY.get(id("sakura_forest"));
        Climate.WARM_MILD.add(sakuraForest, 0.2);
        BiomeRegistries.registerBiomeVariantChance(sakuraForest, 2);
        BiomeRegistries.registerBiomeVariants(sakuraForest, sakuraForest, Ecotones.REGISTRY.get(id("wooded_sakura_hills")));
    }
}