package com.jaskarth.ecotones.util;

import net.minecraft.world.biome.BiomeKeys;
import com.jaskarth.ecotones.world.worldgen.gen.BiomeGenData;
import com.jaskarth.ecotones.world.worldgen.surface.system.ConfiguredSurfaceBuilder;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;

public class VanillaBiomeData {
    public static void init() {
        BiomeGenData.LOOKUP.put(BiomeKeys.OCEAN, new BiomeGenData( -1.0, 0.1, 1.0, 1.0, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(BiomeKeys.WARM_OCEAN, new BiomeGenData( -1.0, 0.1, 1.0, 1.0, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.SAND_SAND_UNDERWATER_CONFIG)));
        BiomeGenData.LOOKUP.put(BiomeKeys.LUKEWARM_OCEAN, new BiomeGenData( -1.0, 0.1, 1.0, 1.0, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(BiomeKeys.COLD_OCEAN, new BiomeGenData( -1.0, 0.1, 1.0, 1.0, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        // FIXME icebergs!
        BiomeGenData.LOOKUP.put(BiomeKeys.FROZEN_OCEAN, new BiomeGenData( -1.0, 0.1, 1.0, 1.0, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));

        BiomeGenData.LOOKUP.put(BiomeKeys.DEEP_OCEAN, new BiomeGenData( -1.8, 0.1, 1.0, 1.0, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(BiomeKeys.DEEP_LUKEWARM_OCEAN, new BiomeGenData( -1.8, 0.1, 1.0, 1.0, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        BiomeGenData.LOOKUP.put(BiomeKeys.DEEP_COLD_OCEAN, new BiomeGenData( -1.8, 0.1, 1.0, 1.0, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
        // FIXME icebergs!
        BiomeGenData.LOOKUP.put(BiomeKeys.DEEP_FROZEN_OCEAN, new BiomeGenData( -1.8, 0.1, 1.0, 1.0, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)));
    }
}
