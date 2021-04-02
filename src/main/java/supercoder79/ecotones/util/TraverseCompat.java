package supercoder79.ecotones.util;

import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;

public final class TraverseCompat {
    private static Identifier id(String path) {
        return new Identifier("traverse", path);
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