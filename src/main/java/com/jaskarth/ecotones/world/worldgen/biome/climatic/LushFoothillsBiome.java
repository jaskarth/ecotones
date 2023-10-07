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
import com.jaskarth.ecotones.world.worldgen.decorator.ChanceDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesConfiguredFeature;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.*;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.*;
import com.jaskarth.ecotones.world.worldgen.structure.EcotonesStructures;

public class LushFoothillsBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("lush_foothills", new LushFoothillsBiome(1.5F, 0.15F, 2, 0.94));
        BiomeRegistries.addMountainBiome(biome);
        BiomeRegistries.addMountainType(ClimateType.MOUNTAIN_FOOTHILLS, biome);
        BiomeRegistries.registerBiomeVariantChance(biome, 8);
        BiomeRegistries.registerBiomeVariants(biome, EarlyBiomeRegistry.get("mountain_lake"));

        Climate.HOT_MILD.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 1.0);
        Climate.HOT_HUMID.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 1.0);
        Climate.HOT_VERY_HUMID.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 1.0);
        Climate.HOT_RAINFOREST.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 1.0);

        Climate.WARM_DESERT.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 1.0);
        Climate.WARM_VERY_DRY.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 1.0);
        Climate.WARM_DRY.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 1.0);
        Climate.WARM_MODERATE.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 1.0);
        Climate.WARM_MILD.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 1.0);
        Climate.WARM_HUMID.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 1.0);
        Climate.WARM_VERY_HUMID.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 1.0);
        Climate.WARM_RAINFOREST.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 1.0);
    }

    public LushFoothillsBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(0.575F);
        this.downfall(0.825F);
        this.precipitation(Biome.Precipitation.RAIN);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.addStructureFeature(EcotonesStructures.CAMPFIRE_BIRCH);
        this.associate(BiomeAssociations.MOUNTAIN_LIKE);

        

        DefaultBiomeFeatures.addForestFlowers(this.getGenerationSettings());

        BiomeDecorator.addSurfaceRocks(this);
        BiomeDecorator.addClover(this, 4);
        BiomeDecorator.addLilacs(this, 1);
        BiomeDecorator.addLavender(this, 1);
        BiomeDecorator.addPatch(this, FeatureConfigHolder.MOSS, 3);
        BiomeDecorator.addRock(this, 4);
        BiomeDecorator.addGrass(this, FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG, 8);

        BiomeDecorator.addSpruceShrubs(this, 1, 0.2);
        BiomeDecorator.addOakShrubs(this, 0.05, 1.2);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.IMPROVED_BIRCH.configure(TreeType.RARE_DEAD_SPRUCE)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_DEAD_SPRUCE.decorationData)));

        this.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION,
                EcotonesFeatures.DUCK_NEST.configure(DefaultFeatureConfig.INSTANCE)
                        .decorate(EcotonesDecorators.DUCK_NEST.configure(new ShrubDecoratorConfig(0.2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesConfiguredFeature.wrap(Feature.TREE, FeatureConfigHolder.SPRUCE_TREE_CONFIG)
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(6))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(64, 96), true, UniformIntProvider.create(10, 14)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(12, 16), true, UniformIntProvider.create(10, 14)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(2)
                        .repeat(4));

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
