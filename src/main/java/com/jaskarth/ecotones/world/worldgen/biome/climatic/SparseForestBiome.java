package com.jaskarth.ecotones.world.worldgen.biome.climatic;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import com.jaskarth.ecotones.world.worldgen.features.mc.RandomPatchFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import com.jaskarth.ecotones.world.worldgen.decorator.*;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.api.TreeType;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.*;
import com.jaskarth.ecotones.world.worldgen.structure.EcotonesStructures;

public class SparseForestBiome extends EcotonesBiomeBuilder {

    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("sparse_forest", new SparseForestBiome(0.3F, 0.065F, 2.8, 0.97));
        Biome shrubs = EarlyBiomeRegistry.register("sparse_forest_shrubs", new SparseForestBiome(0.3F, 0.025F, 1.4, 0.97, 5.4, 64));
        Biome lakes = EarlyBiomeRegistry.register("sparse_forest_lakes", new SparseForestBiome(-0.235F, 0.025F, 0.4, 0.97, 4.2, 52));
        Biome bog = EarlyBiomeRegistry.register("sparse_forest_bog", new SparseForestBiome(-0.165F, 0.035F, 0.8, 0.97, 4.2, 10));

        BiomeRegistries.registerBiomeVariantChance(biome, 3);
        BiomeRegistries.registerBiomeVariants(biome, shrubs, lakes, bog);

        Climate.HOT_DRY.add(biome, 0.1);
        Climate.HOT_MODERATE.add(biome, 0.1);
    }

    public SparseForestBiome(float depth, float scale, double hilliness, double volatility) {
        this(depth, scale, hilliness, volatility, 2.1, 64);
    }

    public SparseForestBiome(float depth, float scale, double hilliness, double volatility, double shrubCount, int peatChance) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(1F);
        this.downfall(0.3F);

        this.precipitation(Biome.Precipitation.RAIN);
        this.associate(BiomeAssociations.FOREST_LIKE);

        this.hilliness(hilliness);
        this.volatility(volatility);

        this.addStructureFeature(EcotonesStructures.CAMPFIRE_OAK);

        BiomeDecorator.addRock(this, Blocks.COBBLESTONE.getDefaultState(), 1, 10);

        BiomeDecorator.addSurfaceRocks(this);
        BiomeDecorator.addGrass(this, FeatureConfigHolder.PRAIRIE_CONFIG, 3);
        BiomeDecorator.addClover(this, 1);
        BiomeDecorator.addPatch(this, FeatureConfigHolder.MOSS, 1);
        BiomeDecorator.addOakShrubs(this, shrubCount, shrubCount / 3.0);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.225))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BRANCHING_OAK.configure(TreeType.MEDIUM_SMALL_RARE_OAK)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.MEDIUM_SMALL_RARE_OAK.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BRANCHING_OAK.configure(TreeType.RARE_LARGE_OAK)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_LARGE_OAK.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.IMPROVED_BIRCH.configure(TreeType.RARER_DEAD_BIRCH)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARER_DEAD_BIRCH.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BRANCHING_DARK_OAK.configure(TreeType.RARE_DARK_OAK)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_DARK_OAK.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(64, 96), true, UniformIntProvider.create(10, 14)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(4)
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CATTAIL.configure(new CattailFeatureConfig(EcotonesBlocks.WATERGRASS.getDefaultState(), UniformIntProvider.create(12, 16), true, UniformIntProvider.create(6, 8)))
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

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(BlockStateProvider.of(Blocks.LILY_PAD.getDefaultState())).tries(10).build())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SWITCHGRASS_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(8));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.GROUND_PATCH.configure(new PatchFeatureConfig(EcotonesBlocks.PEAT_BLOCK.getDefaultState(), Blocks.GRASS_BLOCK, UniformIntProvider.create(1, 4)))
                        .spreadHorizontally()
                        .repeat(3)
                        .applyChance(peatChance));

        this.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION,
                EcotonesFeatures.PODZOL.configure(FeatureConfig.DEFAULT)
                        .spreadHorizontally()
                        .applyChance(7));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);
        BiomeHelper.addTemperateSpawns(this.getSpawnSettings());
    }
}
