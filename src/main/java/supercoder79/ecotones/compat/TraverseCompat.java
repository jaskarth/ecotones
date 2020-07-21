package supercoder79.ecotones.compat;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.Climate;

public class TraverseCompat {
    public static void init() {
        BiomeRegistries.dispatch(ImmutableList.of(new Identifier("traverse:arid_highlands")), () -> {
            // arid highlands
            Biome aridHighlands = Registry.BIOME.get(new Identifier("traverse:arid_highlands"));
            Climate.WARM_DRY.add(aridHighlands, 0.2);
            Climate.WARM_VERY_DRY.add(aridHighlands, 0.2);
        });

        BiomeRegistries.dispatch(ImmutableList.of(new Identifier("traverse:autumnal_woods"), new Identifier("traverse:autumnal_wooded_hills")), () -> {
            // autumnal woods
            Biome autumnalForest = Registry.BIOME.get(new Identifier("traverse:autumnal_woods"));
            Climate.WARM_MILD.add(autumnalForest, 0.2);
            BiomeRegistries.registerBiomeVariantChance(autumnalForest, 2);
            BiomeRegistries.registerBiomeVariants(autumnalForest, autumnalForest, Registry.BIOME.get(new Identifier("traverse:autumnal_wooded_hills")));
        });

        BiomeRegistries.dispatch(ImmutableList.of(new Identifier("traverse:coniferous_forest"), new Identifier("traverse:coniferous_wooded_hills"), new Identifier("traverse:high_coniferous_forest")), () -> {
            // coniferous forest
            Biome coniferousForest = Registry.BIOME.get(new Identifier("traverse:coniferous_forest"));
            Climate.WARM_HUMID.add(coniferousForest, 0.2);
            BiomeRegistries.registerBiomeVariantChance(coniferousForest, 3);
            BiomeRegistries.registerBiomeVariants(coniferousForest, Registry.BIOME.get(new Identifier("traverse:coniferous_wooded_hills")), Registry.BIOME.get(new Identifier("traverse:high_coniferous_forest")));
        });

        BiomeRegistries.dispatch(ImmutableList.of(new Identifier("traverse:lush_swamp")), () -> {
            // lush swamp
            Biome lushSwamp = Registry.BIOME.get(new Identifier("traverse:lush_swamp"));
            Climate.HOT_VERY_HUMID.add(lushSwamp, 0.3);
            BiomeRegistries.registerNoBeachBiome(lushSwamp);
        });

        BiomeRegistries.dispatch(ImmutableList.of(new Identifier("traverse:meadow")), () -> {
            // meadow
            Biome meadow = Registry.BIOME.get(new Identifier("traverse:meadow"));
            Climate.WARM_MODERATE.add(meadow, 0.3);
            Climate.WARM_MILD.add(meadow, 0.3);
        });

        BiomeRegistries.dispatch(ImmutableList.of(new Identifier("traverse:woodlands")), () -> {
            // woodlands
            Biome woodlands = Registry.BIOME.get(new Identifier("traverse:woodlands"));
            Climate.HOT_VERY_HUMID.add(woodlands, 0.3);
        });
    }
}