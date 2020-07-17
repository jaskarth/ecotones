package supercoder79.ecotones.world.biome;

import supercoder79.ecotones.world.biome.alternative.*;
import supercoder79.ecotones.world.biome.base.HotBiomes;
import supercoder79.ecotones.world.biome.base.SwampBiomes;
import supercoder79.ecotones.world.biome.base.WarmBiomes;
import supercoder79.ecotones.world.biome.special.*;
import supercoder79.ecotones.world.biome.technical.BeachBiome;
import supercoder79.ecotones.world.biome.technical.RiverBiome;

public class EcotonesBiomes {
    public static void init() {
        // base biomes
        HotBiomes.init();
        WarmBiomes.init();
        SwampBiomes.init();

        // technical biomes
        BeachBiome.init();
        RiverBiome.init();

        // volcanic biomes
        VolcanicBiome.init();
        SuperVolcanicBiome.init();
        HotSpringsBiome.init();
        BlessedSpringsBiome.init();

        // special biomes
        OasisBiome.init();
        ThePitsBiome.init();
        GreenSpiresBiome.init();
        HazelGroveBiome.init();
        PinePeaksBiome.init();
        UluruBiome.init();
        FlowerPrairieBiome.init();
        WoodlandThicketBiome.init();
        WastelandBiome.init();
        CloverFieldsBiome.init();
        PoplarForestBiome.init();
        TemperateGrasslandBiome.init();
        BirchForestBiome.init();
        FloodedSavannahBiome.init();
        DeadSpruceForestBiome.init();
        PalmForestBiome.init();
        MoorBiome.init();
        AspenFoothillsBiome.init();
        LushDesertBiome.init();
        DrySteppeBiome.init();
        DrySavannaBiome.init();
        FertileValleyBiome.init();
        BluebellWoodBiome.init();
        SpruceMarshBiome.init();
        HotMangroveBiome.init();
    }
}
