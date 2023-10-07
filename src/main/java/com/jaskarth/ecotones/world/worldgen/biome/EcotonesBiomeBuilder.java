package com.jaskarth.ecotones.world.worldgen.biome;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.structure.Structure;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.FeatureList;
import com.jaskarth.ecotones.util.RegistryReport;
import com.jaskarth.ecotones.util.register.EcotonesLookupBuilder;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesConfiguredFeature;
import com.jaskarth.ecotones.world.worldgen.gen.BiomeGenData;
import com.jaskarth.ecotones.world.worldgen.surface.system.ConfiguredSurfaceBuilder;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceConfig;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class EcotonesBiomeBuilder {
    public static final Map<Biome, BiomeGenData> OBJ2DATA = new HashMap<>();
    private final Biome.Builder builder;
    private final SpawnSettings.Builder spawnSettings;
    private final EcotonesLookupBuilder generationSettings;
    private final BiomeEffects.Builder biomeEffects;
    private final List<RegistryEntry<Structure>> structures = new ArrayList<>();
    private final FeatureList features = new FeatureList();

    private double depth = 0.1;
    private double scale = 0.05;
    private double hilliness = 1.0;
    private double volatility = 1.0;
    private ConfiguredSurfaceBuilder<?> configuredSurfaceBuilder;
    private List<TagKey<Biome>> tags = new ArrayList<>();

    public EcotonesBiomeBuilder() {
        this.builder = new Biome.Builder();
        this.spawnSettings = new SpawnSettings.Builder();
        this.generationSettings = new EcotonesLookupBuilder(this.features);
        this.biomeEffects = new BiomeEffects.Builder();

        // Defaults
        this.biomeEffects.waterColor(0x3F76E4);
        this.biomeEffects.waterFogColor(0x050533);
        this.biomeEffects.fogColor(0xC0D8FF);
        associate(BiomeAssociations.DEFAULT);
    }

    protected void associate(BiomeAssociations.BiomeAssociation... associations) {
        this.tags = Arrays.stream(associations).flatMap(a -> Arrays.stream(a.tags())).toList();
    }

    protected void surfaceBuilder(ConfiguredSurfaceBuilder<?> surfaceBuilder) {
        this.configuredSurfaceBuilder = surfaceBuilder;
    }

    protected <C extends SurfaceConfig> void surfaceBuilder(SurfaceBuilder<C> surfaceBuilder, C config) {
        surfaceBuilder(surfaceBuilder.withConfig(config));
    }

    protected void depth(float depth) {
        this.depth = depth;
    }

    protected void scale(float scale) {
        this.scale = scale;
    }

    protected void hilliness(double hilliness) {
        this.hilliness = hilliness;
    }

    protected void volatility(double volatility) {
        this.volatility = volatility;
    }

    protected void downfall(float downfall) {
        this.builder.downfall(downfall);
    }

    protected void temperature(float temperature) {
        this.biomeEffects.skyColor(getSkyColor(temperature));
        this.builder.temperature(temperature);
    }

    protected void precipitation(Biome.Precipitation precipitation) {
        this.builder.precipitation(precipitation != Biome.Precipitation.NONE);
    }

    protected void addSpawn(SpawnGroup group, SpawnSettings.SpawnEntry entry) {
        this.spawnSettings.spawn(group, entry);
    }

    protected void grassColor(int grassColor) {
        this.biomeEffects.grassColor(grassColor);
    }

    protected void foliageColor(int foliageColor) {
        this.biomeEffects.foliageColor(foliageColor);
    }

    protected void skyColor(int skyColor) {
        this.biomeEffects.skyColor(skyColor);
    }

    protected void waterColor(int waterColor) {
        this.biomeEffects.waterColor(waterColor);
    }

    protected void waterFogColor(int waterFogColor) {
        this.biomeEffects.waterFogColor(waterFogColor);
    }

    protected void particleConfig(BiomeParticleConfig particleConfig) {
        this.biomeEffects.particleConfig(particleConfig);
    }

    public void addFeature(GenerationStep.Feature step, EcotonesConfiguredFeature<?, ?> ecfeature) {
        ConfiguredFeature<?, ?> feature = ecfeature.vanilla();

        PlacedFeature plf = ecfeature.placed(RegistryEntry.of(feature));

        RegistryReport.increment("Configured Feature");
        features.add(step, plf);
    }

    protected void addStructureFeature(RegistryEntry<Structure> structure) {
        structures.add(structure);
    }

    public Biome build() {
        this.builder.effects(this.biomeEffects.build());
        this.builder.generationSettings(this.generationSettings.build());
        this.builder.spawnSettings(this.spawnSettings.build());

        Biome biome = builder.build();
        for (RegistryEntry<Structure> structure : structures) {
            BiomeRegistries.registerStructure(structure, biome);
        }

        for (TagKey<Biome> tag : tags) {
            BiomeRegistries.associateTag(tag, biome);
        }

        BiomeRegistries.registerFeatureList(biome, features);

        OBJ2DATA.put(biome, new BiomeGenData(this.depth, this.scale, this.volatility, this.hilliness, this.configuredSurfaceBuilder));

        return biome;
    }

    public EcotonesLookupBuilder getGenerationSettings() {
        return generationSettings;
    }

    public SpawnSettings.Builder getSpawnSettings() {
        return spawnSettings;
    }

    private static int getSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = MathHelper.clamp(f, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }
}
