package com.jaskarth.ecotones.world.worldgen.biome.climatic;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.FeatureConfig;
import com.jaskarth.ecotones.world.data.EcotonesData;
import com.jaskarth.ecotones.world.worldgen.decorator.ChanceDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.ClimateType;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.*;

public class HotPineForestBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register(new Identifier("ecotones", "hot_pine_forest"), new HotPineForestBiome(0.3f, 0.075f, 2.2, 0.96).build());
        Biome clearing = EarlyBiomeRegistry.register(new Identifier("ecotones", "hot_pine_forest_clearing"), new HotPineForestBiome(0.3f, 0.085f, 1.4, 0.975, 2.4).build());
        Biome lake = EarlyBiomeRegistry.register(new Identifier("ecotones", "hot_pine_forest_lake"), new HotPineForestBiome(-0.25f, 0.075f, 0.8, 0.975, 5.4).build());

        Climate.HOT_VERY_DRY.add(biome, 0.2);
        Climate.HOT_DRY.add(biome, 0.4);
        Climate.HOT_MODERATE.add(biome, 0.3);
        Climate.HOT_MILD.add(biome, 0.3);

        Climate.WARM_VERY_DRY.add(biome, 0.4);
        Climate.WARM_DRY.add(biome, 0.2);

        BiomeRegistries.registerBiomeVariantChance(biome, 3);
        BiomeRegistries.registerBiomeVariants(biome, lake, clearing);

        Biome montane = EarlyBiomeRegistry.register(new Identifier("ecotones", "montane_hot_pine_forest"), new HotPineForestBiome(3.0f, 0.125f, 2.2, 0.96).build());
        BiomeRegistries.addMountainBiome(montane);
        BiomeRegistries.addMountainType(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, montane);

        Climate.HOT_DESERT.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, montane, 1.0);
        Climate.HOT_VERY_DRY.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, montane, 1.0);
        Climate.HOT_DRY.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, montane, 1.0);
        Climate.HOT_MODERATE.add(ClimateType.MOUNTAIN_FOOTHILLS_UPPER, montane, 1.0);
    }

    protected HotPineForestBiome(float depth, float scale, double hilliness, double volatility) {
        this(depth, scale, hilliness, volatility, 8.4);
    }

    protected HotPineForestBiome(float depth, float scale, double hilliness, double volatility, double treeCount) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);
        this.precipitation(Biome.Precipitation.NONE);
        this.depth(depth);
        this.scale(scale);
        this.temperature(0.8F);
        this.downfall(0.15F);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.associate(BiomeAssociations.LONELY_SAVANNA_LIKE);

        
        BiomeHelper.addDefaultFeatures(this);

        BiomeDecorator.addRock(this, Blocks.COBBLESTONE.getDefaultState(), 1, 10);

        BiomeDecorator.addSpruceShrubs(this, 2.4, 1.65);
        BiomeDecorator.addOakShrubs(this, 0, 0.2);
        BiomeDecorator.addGrass(this, FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG, 16);
        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.SWITCHGRASS_CONFIG, 12);
        BiomeDecorator.addPatch(this, FeatureConfigHolder.ONLY_TALL_GRASS_CONFIG, 2);
        BiomeDecorator.addLavender(this, 1);
        BiomeDecorator.addLilacs(this, 1);
        BiomeDecorator.addPatch(this, FeatureConfigHolder.MOSS, 4);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.DENSE_LAVENDER_LILAC)
                        .decorate(EcotonesDecorators.DATA_FUNCTION.configure(EcotonesData.FLOWER_MOSAIC)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(64, 96), true, UniformIntProvider.create(10, 14)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(6, 14), true, UniformIntProvider.create(3, 5)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(2)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DUCKWEED.configure(new DuckweedFeatureConfig(UniformIntProvider.create(8, 16), UniformIntProvider.create(4, 6)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DUCKWEED.configure(new DuckweedFeatureConfig(UniformIntProvider.create(64, 96), UniformIntProvider.create(10, 14)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BARREN_PINE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.3))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.TALL_PINE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(treeCount))));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.GROUND_PATCH.configure(new PatchFeatureConfig(EcotonesBlocks.PEAT_BLOCK.getDefaultState(), Blocks.GRASS_BLOCK, UniformIntProvider.create(1, 4)))
                        .spreadHorizontally()
                        .repeat(3)
                        .applyChance(64));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(10));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
    }
}
