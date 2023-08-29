package com.jaskarth.ecotones.world.worldgen.gen;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.jaskarth.ecotones.world.worldgen.surface.system.ConfiguredSurfaceBuilder;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public record BiomeGenData(double depth, double scale, double volatility, double hilliness, ConfiguredSurfaceBuilder<?> surface) {

    public BiomeGenData(double depth, double scale, ConfiguredSurfaceBuilder<?> surface) {
        this(depth, scale, 1.0, 1.0, surface);
    }

    public static final Map<RegistryKey<Biome>, BiomeGenData> LOOKUP = new LinkedHashMap<>();
    public static final BiomeGenData DEFAULT = new BiomeGenData(0.1, 0.05, 1.0, 1.0, new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG));

}
