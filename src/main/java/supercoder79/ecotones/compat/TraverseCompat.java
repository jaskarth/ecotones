package supercoder79.ecotones.compat;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.world.biome.BiomeUtil;

public class TraverseCompat {
    public static void init() {
        // arid highlands
        Biome aridHighlands = Registry.BIOME.get(new Identifier("traverse:arid_highlands"));
        BiomeRegistries.registerSpecialBiome(aridHighlands, id -> BiomeUtil.contains(id, "steppe") || BiomeUtil.contains(id, "scrub"));
        BiomeRegistries.registerSmallSpecialBiome(aridHighlands, 20);

        // autumnal woods
        Biome autumnalForest = Registry.BIOME.get(new Identifier("traverse:autumnal_woods"));
        BiomeRegistries.registerSpecialBiome(autumnalForest, id -> BiomeUtil.contains(id, "forest"));
        BiomeRegistries.registerSmallSpecialBiome(autumnalForest, 30);
        BiomeRegistries.registerBiomeVariantChance(autumnalForest, 2);
        BiomeRegistries.registerBiomeVariants(autumnalForest, autumnalForest, Registry.BIOME.get(new Identifier("traverse:autumnal_wooded_hills")));

        // coniferous forest
        Biome coniferousForest = Registry.BIOME.get(new Identifier("traverse:coniferous_forest"));
        BiomeRegistries.registerSpecialBiome(coniferousForest, id -> BiomeUtil.contains(id, "spruce_forest") || BiomeUtil.contains(id, "lichen_woodland"));
        BiomeRegistries.registerSmallSpecialBiome(coniferousForest, 40);
        BiomeRegistries.registerBiomeVariantChance(coniferousForest, 3);
        BiomeRegistries.registerBiomeVariants(coniferousForest, Registry.BIOME.get(new Identifier("traverse:coniferous_wooded_hills")), Registry.BIOME.get(new Identifier("traverse:high_coniferous_forest")));

        // lush swamp
        Biome lushSwamp = Registry.BIOME.get(new Identifier("traverse:lush_swamp"));
        BiomeRegistries.registerSpecialBiome(lushSwamp, id -> BiomeUtil.contains(id, "forest"));
        BiomeRegistries.registerSmallSpecialBiome(lushSwamp, 40);

        // meadow
        Biome meadow = Registry.BIOME.get(new Identifier("traverse:meadow"));
        BiomeRegistries.registerSpecialBiome(meadow, id -> BiomeUtil.contains(id, "prairie") || BiomeUtil.contains(id, "lichen_woodland"));
        BiomeRegistries.registerBigSpecialBiome(meadow, 150);

        // woodlands
        Biome woodlands = Registry.BIOME.get(new Identifier("traverse:woodlands"));
        BiomeRegistries.registerSpecialBiome(woodlands, id -> BiomeUtil.contains(id, "prairie") || BiomeUtil.contains(id, "lichen_woodland") || BiomeUtil.contains(id, "forest"));
        BiomeRegistries.registerSmallSpecialBiome(woodlands, 25);
    }
}