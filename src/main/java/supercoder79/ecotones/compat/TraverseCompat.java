package supercoder79.ecotones.compat;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.world.biome.BiomeUtil;

public class TraverseCompat {
    public static void init() {
        // arid highlands
        Biome aridHighlands = Registry.BIOME.get(new Identifier("traverse:arid_highlands"));
        Climate.WARM_DRY.add(aridHighlands, 0.2);
        Climate.WARM_VERY_DRY.add(aridHighlands, 0.2);

        // autumnal woods
        Biome autumnalForest = Registry.BIOME.get(new Identifier("traverse:autumnal_woods"));
        Climate.WARM_MILD.add(autumnalForest, 0.2);
        BiomeRegistries.registerBiomeVariantChance(autumnalForest, 2);
        BiomeRegistries.registerBiomeVariants(autumnalForest, autumnalForest, Registry.BIOME.get(new Identifier("traverse:autumnal_wooded_hills")));

        // coniferous forest
        Biome coniferousForest = Registry.BIOME.get(new Identifier("traverse:coniferous_forest"));
        Climate.WARM_HUMID.add(coniferousForest, 0.2);
        BiomeRegistries.registerBiomeVariantChance(coniferousForest, 3);
        BiomeRegistries.registerBiomeVariants(coniferousForest, Registry.BIOME.get(new Identifier("traverse:coniferous_wooded_hills")), Registry.BIOME.get(new Identifier("traverse:high_coniferous_forest")));

        // lush swamp
        Biome lushSwamp = Registry.BIOME.get(new Identifier("traverse:lush_swamp"));
        Climate.HOT_VERY_HUMID.add(lushSwamp, 0.3);

        // meadow
        Biome meadow = Registry.BIOME.get(new Identifier("traverse:meadow"));
        Climate.WARM_MODERATE.add(meadow, 0.3);
        Climate.WARM_MILD.add(meadow, 0.3);

        // woodlands
        Biome woodlands = Registry.BIOME.get(new Identifier("traverse:woodlands"));
        Climate.HOT_VERY_HUMID.add(woodlands, 0.3);
    }
}