package com.jaskarth.ecotones.world.worldgen.biome;

import com.jaskarth.ecotones.world.worldgen.biome.base.hot.*;
import com.jaskarth.ecotones.world.worldgen.biome.base.warm.*;
import com.jaskarth.ecotones.world.worldgen.biome.cave.LimestoneCaveBiome;
import com.jaskarth.ecotones.world.worldgen.biome.cave.WrappedCaveBiome;
import com.jaskarth.ecotones.world.worldgen.biome.climatic.*;
import com.jaskarth.ecotones.world.worldgen.biome.special.*;
import com.jaskarth.ecotones.world.worldgen.biome.technical.*;

public final class EcotonesBiomes {
    public static void init() {
        // Hot biomes
        DesertBiome.init();
        ScrublandBiome.init();
        SteppeBiome.init();
        TropicalGrasslandBiome.init();
        RockyGrasslandBiome.init();
        WoodlandThicketBiome.init();
        BluebellWoodBiome.init();
        TropicalRainforestBiome.init();

        // Warm biomes
        CoolDesertBiome.init();
        CoolScrublandBiome.init();
        DriedForestBiome.init();
        PrairieBiome.init();
        LichenWoodlandBiome.init();
        SpruceForestBiome.init();
        LushShrublandBiome.init();
        TemperateRainforestBiome.init();

        // technical biomes
        TropicalBeachBiome.init();
        DryBeachBiome.init();
        GravelBeachBiome.init();
        RiverBiome.init();
        MountainLakeBiome.init();

        // special biomes
        ChasmBiome.init();
        GreenSpiresBiome.init();

        // Biome replacements
        HazelGroveBiome.init();
        PinePeaksBiome.init();
        FlowerPrairieBiome.init();
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
        ClayBasinBiome.init();
        RockyMountainBiome.init();
        GraniteSpringsBiome.init();
        LarchForestBiome.init();
        BirchLakesBiome.init();
        LowlandsBiome.init();
        DandelionFieldBiome.init();
        ThornBrushBiome.init();
        SpruceShrublandBiome.init();
        HotPineForestBiome.init();
        RedRockRidgeBiome.init();
        SpruceGlenBiome.init();
        BirchGroveBiome.init();
        DesertShrublandBiome.init();
        MountainPeaksBiome.init();
        LushFoothillsBiome.init();
        SparseAlpineForestBiome.init();
        MontaneFieldsBiome.init();
        RockySlopesBiome.init();
        SparseTundraBiome.init();
        LavenderFieldBiome.init();

        // Cave biomes
        LimestoneCaveBiome.init();
        WrappedCaveBiome.init();

        RoseFieldBiome.init();
    }
}
