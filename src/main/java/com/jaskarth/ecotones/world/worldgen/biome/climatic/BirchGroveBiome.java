package com.jaskarth.ecotones.world.worldgen.biome.climatic;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.world.worldgen.decorator.ChanceDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.DuckweedFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.RockFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.structure.EcotonesStructures;

public class BirchGroveBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("birch_grove", new BirchGroveBiome(0.25F, 0.065F, 4.2, 0.93));

        Climate.WARM_VERY_HUMID.add(biome, 0.2);
        Climate.WARM_HUMID.add(biome, 0.2);
        Climate.WARM_MILD.add(biome, 0.1);
    }

    public BirchGroveBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(0.575F);
        this.downfall(0.825F);
        this.precipitation(Biome.Precipitation.RAIN);

        this.grassColor(0x70bd6d);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.addStructureFeature(EcotonesStructures.CAMPFIRE_BIRCH);
        this.associate(BiomeAssociations.FOREST_LIKE, BiomeAssociations.BIRCH);

        DefaultBiomeFeatures.addForestFlowers(this.getGenerationSettings());

        BiomeDecorator.addClover(this, 2);

        BiomeDecorator.addPatch(this, FeatureConfigHolder.MOSS, 1);

        BiomeDecorator.addRock(this, 4);

        BiomeDecorator.addSurfaceRocks(this);

        BiomeDecorator.addGrass(this, FeatureConfigHolder.BIRCH_GROVE_CONFIG, 12);

        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.LILY_OF_THE_VALLEY_PATCH, 6);

        BiomeDecorator.addBirchShrubs(this, 1, 0.6);
        BiomeDecorator.addShrubs(this, Blocks.BIRCH_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 0, 0.2);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.ASPEN_TREE.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.5))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.ASPEN_TREE.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.05))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DUCKWEED.configure(new DuckweedFeatureConfig(UniformIntProvider.create(64, 96), UniformIntProvider.create(10, 14)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DUCKWEED.configure(new DuckweedFeatureConfig(UniformIntProvider.create(8, 16), UniformIntProvider.create(4, 6)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(12));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());

        BiomeHelper.addDefaultFeatures(this);
    }
}
