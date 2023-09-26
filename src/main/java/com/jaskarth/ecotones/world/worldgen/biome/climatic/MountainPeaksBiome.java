package com.jaskarth.ecotones.world.worldgen.biome.climatic;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.ClimateType;
import com.jaskarth.ecotones.world.worldgen.decorator.ChanceDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.RockFeatureConfig;

public class MountainPeaksBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register(new Identifier("ecotones", "mountain_peaks"), new MountainPeaksBiome(7.5F, 0.175F, 9, 0.9).build());
        BiomeRegistries.addMountainBiome(biome);
        BiomeRegistries.addMountainType(ClimateType.MOUNTAIN_PEAKS, biome);

        Climate.HOT_DESERT.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.HOT_VERY_DRY.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.HOT_DRY.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.HOT_MODERATE.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.HOT_MILD.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.HOT_HUMID.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.HOT_VERY_HUMID.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.HOT_RAINFOREST.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);

        Climate.WARM_DESERT.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.WARM_VERY_DRY.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.WARM_DRY.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.WARM_MODERATE.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.WARM_MILD.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.WARM_HUMID.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.WARM_VERY_HUMID.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
        Climate.WARM_RAINFOREST.add(ClimateType.MOUNTAIN_PEAKS, biome, 1.0);
    }

    public MountainPeaksBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.STONE_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(0.05F);
        this.downfall(0.3F);
        this.precipitation(Biome.Precipitation.SNOW);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.associate(BiomeAssociations.MOUNTAIN_LIKE);

        BiomeDecorator.addRock(this, 4);
        BiomeDecorator.addSurfaceRocks(this);
        BiomeDecorator.addGrass(this, FeatureConfigHolder.SHORT_GRASS_CONFIG, 7);

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(6));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.ROCK_SPIRE.configure(FeatureConfigHolder.STONE_SPIRE)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(8));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());

        BiomeHelper.addDefaultFeatures(this);
    }
}
