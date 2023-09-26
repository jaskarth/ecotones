package com.jaskarth.ecotones.world.worldgen.biome.base.hot;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import com.jaskarth.ecotones.world.worldgen.decorator.*;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.*;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.CattailFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.RockFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;

public class TropicalGrasslandBiome extends EcotonesBiomeBuilder {

    public static void init() {
        Biome biome = EarlyBiomeRegistry.register(new Identifier("ecotones", "tropical_grassland"), new TropicalGrasslandBiome(0.5f, 0.15f, 1.8, 0.88).build());
        Biome shrubs = EarlyBiomeRegistry.register(new Identifier("ecotones", "tropical_grassland_shrubs"), new TropicalGrasslandBiome(0.5f, 0.15f, 1.8, 0.88, 2.8, 0.4).build());
        Biome thicket = EarlyBiomeRegistry.register(new Identifier("ecotones", "tropical_grassland_thicket"), new TropicalGrasslandBiome(0.5f, 0.075f, 1.8, 0.88, 1.4, 2.6).build());

        BiomeRegistries.registerBiomeVariantChance(biome, 3);
        BiomeRegistries.registerBiomeVariants(biome, shrubs, thicket);

        Climate.HOT_MODERATE.add(biome, 1);

        Biome montane = EarlyBiomeRegistry.register(new Identifier("ecotones", "montane_tropical_grassland"), new TropicalGrasslandBiome(4.5f, 0.15f, 1.8, 0.97, 1.4, 0.1).build());
        BiomeRegistries.addMountainBiome(montane);
        BiomeRegistries.addMountainType(ClimateType.MOUNTAIN_PLAINS, montane);

        Climate.HOT_DESERT.add(ClimateType.MOUNTAIN_PLAINS, montane, 1.0);
        Climate.HOT_VERY_DRY.add(ClimateType.MOUNTAIN_PLAINS, montane, 1.0);
        Climate.HOT_DRY.add(ClimateType.MOUNTAIN_PLAINS, montane, 1.0);
        Climate.HOT_MODERATE.add(ClimateType.MOUNTAIN_PLAINS, montane, 1.0);
    }

    protected TropicalGrasslandBiome(float depth, float scale, double hilliness, double volatility) {
        this(depth, scale, hilliness, volatility, 1.2, 0.3);
    }

    protected TropicalGrasslandBiome(float depth, float scale, double hilliness, double volatility, double shrubCount, double treeCount) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);
        this.precipitation(Biome.Precipitation.RAIN);
        this.depth(depth);
        this.scale(scale);
        this.temperature(1.7F);
        this.downfall(0.225F);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.associate(BiomeAssociations.SAVANNA_LIKE);

        BiomeDecorator.addAcaciaShrubs(this, shrubCount, 0);
        BiomeDecorator.addOakShrubs(this, shrubCount * 2, 0);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.095))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DESERTIFY_SOIL.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(2, 0.5f, 1))));

        BiomeDecorator.addRock(this, Blocks.COBBLESTONE.getDefaultState(), 1, 12);

        BiomeDecorator.addSurfaceRocks(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SMALL_ACACIA.configure(TreeType.RARE_SMALL_ACACIA)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_SMALL_ACACIA.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BIG_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.ABOVE_QUALITY.configure()));

        BiomeDecorator.addPatchRepeatedChance(this, FeatureConfigHolder.ONLY_TALL_GRASS_CONFIG, 2, 3);

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

        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.SWITCHGRASS_CONFIG, 3);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.FAN_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(treeCount))));

        BiomeDecorator.addGrass(this, FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG, 18);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.ROSEMARY.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.ROSEMARY.configure(new ShrubDecoratorConfig(0.15))));

        BiomeDecorator.addPatch(this, FeatureConfigHolder.FLAME_LILY, 2);

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);
    }
}
