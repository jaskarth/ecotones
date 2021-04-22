package supercoder79.ecotones.world.biome;

import supercoder79.ecotones.world.biome.base.hot.*;
import supercoder79.ecotones.world.biome.base.warm.*;
import supercoder79.ecotones.world.biome.cave.LimestoneCaveBiome;
import supercoder79.ecotones.world.biome.climatic.*;
import supercoder79.ecotones.world.biome.special.*;
import supercoder79.ecotones.world.biome.technical.DryBeachBiome;
import supercoder79.ecotones.world.biome.technical.GravelBeachBiome;
import supercoder79.ecotones.world.biome.technical.RiverBiome;
import supercoder79.ecotones.world.biome.technical.TropicalBeachBiome;

public final class EcotonesBiomes {
    public static void init() {
        // Hot biomes
        DesertBiome.init();
        ScrublandBiome.init();
        SteppeBiome.init();
        TropicalGrasslandBiome.init();
        LushSavannaBiome.init();
        TropicalWoodlandBiome.init();
        LushForestBiome.init();
        TropicalRainforestBiome.init();

        // Warm biomes
        CoolDesertBiome.init();
        CoolScrublandBiome.init();
        CoolSteppeBiome.init();
        PrairieBiome.init();
        LichenWoodlandBiome.init();
        SpruceForestBiome.init();
        TemperateForestBiome.init();
        TemperateRainforestBiome.init();

        // technical biomes
        TropicalBeachBiome.init();
        DryBeachBiome.init();
        GravelBeachBiome.init();
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
        UluruBiome.init();

        // Biome replacements
        HazelGroveBiome.init();
        PinePeaksBiome.init();
        FlowerPrairieBiome.init();
        WoodlandThicketBiome.init();
        CloverFieldsBiome.init();
        PoplarForestBiome.init();
        TemperateGrasslandBiome.init();
        BirchForestBiome.init();
        FloodedSavannaBiome.init();
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
        MangroveSwampBiome.init();
        SparseForestBiome.init();
        ShieldTaigaBiome.init();
        DarkOakThicketBiome.init();
        SunflowerPlainsBiome.init();
        SavannaMesaBiome.init();
        WhiteMesaBiome.init();
        MapleForestBiome.init();
        PineForestBiome.init();
        SpruceFieldsBiome.init();
        PineBarrensBiome.init();
        PumpkinPatchBiome.init();
        DriedForestBiome.init();
        ClayBasinBiome.init();
        RockyMountainBiome.init();
        LushShrublandBiome.init();
        GraniteSpringsBiome.init();
        LarchForestBiome.init();
        BirchLakesBiome.init();
        LowlandsBiome.init();

        // Cave biomes
        LimestoneCaveBiome.init();

        // Rose Field biome
        RoseFieldBiome.init();
    }
}
