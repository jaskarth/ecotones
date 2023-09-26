package com.jaskarth.ecotones.world.worldgen.biome.climatic;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesConfiguredFeature;
import com.jaskarth.ecotones.world.worldgen.features.mc.RandomPatchFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.CattailFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.DuckweedFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.structure.EcotonesStructures;
import com.jaskarth.ecotones.world.worldgen.surface.EcotonesSurfaces;
import net.minecraft.world.gen.YOffset;

public class BirchLakesBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register(new Identifier("ecotones", "birch_lakes"), new BirchLakesBiome(0.25F, 0.075F, 6.0, 0.92).build());

        Climate.WARM_VERY_HUMID.add(biome, 0.3);
        Climate.WARM_HUMID.add(biome, 0.3);
        Climate.WARM_MILD.add(biome, 0.2);
    }

    public BirchLakesBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(EcotonesSurfaces.BIRCH_LAKES, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(0.5f);
        this.downfall(0.7f);
        this.precipitation(Biome.Precipitation.RAIN);
        this.associate(BiomeAssociations.FOREST_LIKE, BiomeAssociations.BIRCH);

        this.hilliness(hilliness);
        this.volatility(volatility);

        this.waterColor(0x407fd6);

        BiomeHelper.addDefaultFeatures(this);

        this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, EcotonesConfiguredFeature.wrap(Feature.ORE, (
                        new OreFeatureConfig(new BlockMatchRuleTest(Blocks.STONE), Blocks.DIRT.getDefaultState(), 33)))
                .uniformRange(YOffset.fixed(0), YOffset.fixed(256))
                .spreadHorizontally()
                .repeat(10));

        this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, EcotonesConfiguredFeature.wrap(Feature.ORE, (
                new OreFeatureConfig(new BlockMatchRuleTest(Blocks.STONE), Blocks.GRAVEL.getDefaultState(), 33)))
                .uniformRange(YOffset.fixed(0), YOffset.fixed(256))
                .spreadHorizontally()
                .repeat(8));

        this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, EcotonesConfiguredFeature.wrap(Feature.ORE, (
                new OreFeatureConfig(new BlockMatchRuleTest(Blocks.STONE), Blocks.ANDESITE.getDefaultState(), 33)))
                .uniformRange(YOffset.fixed(0), YOffset.fixed(80))
                .spreadHorizontally()
                .repeat(10));

        this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, EcotonesConfiguredFeature.wrap(Feature.ORE, (
                new OreFeatureConfig(new BlockMatchRuleTest(Blocks.STONE), Blocks.GRANITE.getDefaultState(), 33)))
                .uniformRange(YOffset.fixed(0), YOffset.fixed(80))
                .spreadHorizontally()
                .repeat(10));

        this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, EcotonesConfiguredFeature.wrap(Feature.ORE, (
                new OreFeatureConfig(new BlockMatchRuleTest(Blocks.STONE), Blocks.DIORITE.getDefaultState(), 33)))
                .uniformRange(YOffset.fixed(0), YOffset.fixed(80))
                .spreadHorizontally()
                .repeat(10));

        this.addStructureFeature(EcotonesStructures.CAMPFIRE_BIRCH);

        BiomeDecorator.addSurfaceRocks(this);
        BiomeDecorator.addOakShrubs(this, 0.2, 0.15);
        BiomeDecorator.addBirchShrubs(this, 0.6, 0.55);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(6.6))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.05))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.3))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(64, 96), true, UniformIntProvider.create(10, 14)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4)
                        .repeat(3));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(12, 16), true, UniformIntProvider.create(10, 14)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(2)
                        .repeat(4));

        BiomeDecorator.addGrass(this, FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG, 16);

        BiomeDecorator.addLilacs(this, 1);

        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.LAVENDER, 3);
        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.WILDFLOWERS, 2);
        BiomeDecorator.addPatch(this, FeatureConfigHolder.MOSS, 2);

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
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(BlockStateProvider.of(Blocks.LILY_PAD.getDefaultState())).tries(10).build())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.PLACE_WATER.configure(FeatureConfigHolder.DIORITE_WATER_PATCH)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(8));

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.PLACE_WATER.configure(FeatureConfigHolder.DIORITE_WATER_POOL_PATCH)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(10));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.ROCK_SPIRE.configure(FeatureConfigHolder.DIORITE_SPIRE)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4));

        this.addFeature(GenerationStep.Feature.LAKES,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(8));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.GEYSER_PATCH.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(18));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
    }

}
