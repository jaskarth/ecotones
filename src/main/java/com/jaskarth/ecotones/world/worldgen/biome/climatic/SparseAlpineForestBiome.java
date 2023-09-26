package com.jaskarth.ecotones.world.worldgen.biome.climatic;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import com.jaskarth.ecotones.world.worldgen.decorator.ChanceDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.ClimateType;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.*;

public class SparseAlpineForestBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register(new Identifier("ecotones", "sparse_alpine_forest"), new SparseAlpineForestBiome(3f, 0.125f, 2.2, 0.98).build());
        BiomeRegistries.addMountainBiome(biome);
        BiomeRegistries.addMountainType(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome);

        Climate.HOT_MILD.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome, 1.0);
        Climate.HOT_HUMID.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome, 1.0);
        Climate.HOT_VERY_HUMID.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome, 1.0);
        Climate.HOT_RAINFOREST.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome, 1.0);

        Climate.WARM_DESERT.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome, 1.0);
        Climate.WARM_VERY_DRY.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome, 1.0);
        Climate.WARM_DRY.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome, 1.0);
        Climate.WARM_MODERATE.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome, 1.0);
        Climate.WARM_MILD.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome, 1.0);
        Climate.WARM_HUMID.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome, 1.0);
        Climate.WARM_VERY_HUMID.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome, 1.0);
        Climate.WARM_RAINFOREST.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, biome, 1.0);
    }

    protected SparseAlpineForestBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);
        this.precipitation(Biome.Precipitation.NONE);
        this.depth(depth);
        this.scale(scale);
        this.temperature(0.5F);
        this.downfall(0.6F);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.associate(BiomeAssociations.MOUNTAIN_LIKE);

        
        BiomeHelper.addDefaultFeatures(this);

        BiomeDecorator.addRock(this, Blocks.COBBLESTONE.getDefaultState(), 1, 8);
        BiomeDecorator.addSpruceShrubs(this, 1.8, 1.65);
        BiomeDecorator.addOakShrubs(this, 0.3, 0.3);
        BiomeDecorator.addGrass(this, FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG, 12);

        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.SWITCHGRASS_CONFIG, 8);
        BiomeDecorator.addPatch(this, FeatureConfigHolder.ONLY_TALL_GRASS_CONFIG, 1);
        BiomeDecorator.addPatch(this, FeatureConfigHolder.LAVENDER, 3);
        BiomeDecorator.addPatch(this, FeatureConfigHolder.MOSS, 3);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DUCKWEED.configure(new DuckweedFeatureConfig(UniformIntProvider.create(8, 16), UniformIntProvider.create(4, 6)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SMALL_SPRUCE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.2, true))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BARREN_PINE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.4, true))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.TALL_PINE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.8, true))));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.GROUND_PATCH.configure(new PatchFeatureConfig(EcotonesBlocks.PEAT_BLOCK.getDefaultState(), Blocks.GRASS_BLOCK, UniformIntProvider.create(1, 4)))
                        .spreadHorizontally()
                        .repeat(3)
                        .applyChance(128));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
    }
}
