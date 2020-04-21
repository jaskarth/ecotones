package supercoder79.ecotones.compat;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.biome.BiomeUtil;

public class TerrestriaCompat {
    public static void init() {
        // cypress forest
        Biome cypressForest = Registry.BIOME.get(new Identifier("terrestria:cypress_forest"));
        BiomeRegistries.registerSpecialBiome(cypressForest, id -> BiomeUtil.contains(id, "forest"));
        BiomeRegistries.registerBigSpecialBiome(cypressForest, 60);

        // cypress swamp
        Biome cypressSwamp = Registry.BIOME.get(new Identifier("terrestria:cypress_swamp"));
        BiomeRegistries.registerSpecialBiome(cypressSwamp, id -> BiomeUtil.contains(id, "forest"));
        BiomeRegistries.registerBigSpecialBiome(cypressSwamp, 90);

        // hemlock rainforest
        Biome hemlockRainforest = Registry.BIOME.get(new Identifier("terrestria:hemlock_rainforest"));
        BiomeRegistries.registerSpecialBiome(hemlockRainforest, id -> BiomeUtil.contains(id, "spruce_forest"));
        BiomeRegistries.registerBigSpecialBiome(hemlockRainforest, 50);
        BiomeRegistries.registerBiomeVariantChance(hemlockRainforest, 2);
        BiomeRegistries.registerBiomeVariants(hemlockRainforest, hemlockRainforest, Registry.BIOME.get(new Identifier("terrestria:hemlock_clearing")));

        // japanese maple forest
        Biome japaneseMapleForest = Registry.BIOME.get(new Identifier("terrestria:japanese_maple_forest"));
        BiomeRegistries.registerSpecialBiome(japaneseMapleForest, id -> BiomeUtil.contains(id, "forest"));
        BiomeRegistries.registerBigSpecialBiome(japaneseMapleForest, 50);
        BiomeRegistries.registerBiomeVariantChance(japaneseMapleForest, 2);
        BiomeRegistries.registerBiomeVariants(japaneseMapleForest, japaneseMapleForest, Registry.BIOME.get(new Identifier("terrestria:wooded_japanese_maple_hills")));

        // lush redwood forest
        Biome lushRedwoodForest = Registry.BIOME.get(new Identifier("terrestria:lush_redwood_forest"));
        BiomeRegistries.registerSpecialBiome(lushRedwoodForest, id -> BiomeUtil.contains(id, "spruce_forest"));
        BiomeRegistries.registerBigSpecialBiome(lushRedwoodForest, 50);
        BiomeRegistries.registerBiomeVariantChance(lushRedwoodForest, 2);
        BiomeRegistries.registerBiomeVariants(lushRedwoodForest, lushRedwoodForest, Registry.BIOME.get(new Identifier("terrestria:lush_redwood_clearing")));

        // rainbow rainforest
        Biome rainbowRainforest = Registry.BIOME.get(new Identifier("terrestria:rainbow_rainforest"));
        BiomeRegistries.registerSpecialBiome(rainbowRainforest, id -> BiomeUtil.contains(id, "rainforest"));
        BiomeRegistries.registerBigSpecialBiome(rainbowRainforest, 80);
        BiomeRegistries.registerBiomeVariantChance(rainbowRainforest, 3);
        BiomeRegistries.registerBiomeVariants(rainbowRainforest, Registry.BIOME.get(new Identifier("terrestria:rainbow_rainforest_mountains")), Registry.BIOME.get(new Identifier("terrestria:rainbow_rainforest_lake")));

        // redwood forest
        Biome redwoodForest = Registry.BIOME.get(new Identifier("terrestria:redwood_forest"));
        BiomeRegistries.registerSpecialBiome(redwoodForest, id -> BiomeUtil.contains(id, "spruce_forest"));
        BiomeRegistries.registerBigSpecialBiome(redwoodForest, 40);
        BiomeRegistries.registerBiomeVariantChance(redwoodForest, 2);
        BiomeRegistries.registerBiomeVariants(redwoodForest, redwoodForest, Registry.BIOME.get(new Identifier("terrestria:redwood_clearing")));

        // sakura forest
        Biome sakuraForest = Registry.BIOME.get(new Identifier("terrestria:sakura_forest"));
        BiomeRegistries.registerSpecialBiome(sakuraForest, id -> BiomeUtil.contains(id, "forest"));
        BiomeRegistries.registerBigSpecialBiome(sakuraForest, 50);
        BiomeRegistries.registerBiomeVariantChance(sakuraForest, 2);
        BiomeRegistries.registerBiomeVariants(sakuraForest, sakuraForest, Registry.BIOME.get(new Identifier("terrestria:wooded_sakura_hills")));

    }
}
