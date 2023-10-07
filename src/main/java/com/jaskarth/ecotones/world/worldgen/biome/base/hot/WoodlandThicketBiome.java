package com.jaskarth.ecotones.world.worldgen.biome.base.hot;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.HeightmapPlacementModifier;
import net.minecraft.world.gen.placementmodifier.NoiseThresholdCountPlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesConfiguredFeature;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.structure.EcotonesStructures;

import java.util.OptionalInt;

public class WoodlandThicketBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("woodland_thicket", new WoodlandThicketBiome(0.5F, 0.125F, 2.1, 1, 8));
        Biome clearing = EarlyBiomeRegistry.register("woodland_clearing", new WoodlandThicketBiome(0.5F, 0.0F, 1.2, 0.88, 2));
        Biome hilly = EarlyBiomeRegistry.register("woodland_hilly", new WoodlandThicketBiome(1.2F, 0.8F, 3.2, 0.7, 6));
        Biome hillyClearing = EarlyBiomeRegistry.register("woodland_clearing_hilly", new WoodlandThicketBiome(1.2F, 0.2F, 1.6, 0.8, 2));

        BiomeRegistries.registerBiomeVariantChance(biome, 4);
        BiomeRegistries.registerBiomeVariants(biome, clearing, hilly, hillyClearing);
        Climate.WARM_VERY_HUMID.add(biome, 0.3);
        Climate.HOT_VERY_HUMID.add(biome, 0.2);
        Climate.HOT_HUMID.add(biome, 1);
    }

    public WoodlandThicketBiome(float depth, float scale, double hilliness, double volatility, int treeAmt) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(1F);
        this.downfall(0.4F);

        this.precipitation(Biome.Precipitation.RAIN);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.associate(BiomeAssociations.FOREST_LIKE);

        this.addStructureFeature(EcotonesStructures.CAMPFIRE_OAK);

        
        BiomeHelper.addDefaultFeatures(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SURFACE_ROCKS)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(1)
        );

        BiomeDecorator.addGrass(this, FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG, 20);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(6))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesConfiguredFeature.wrap(Feature.TREE, (new TreeFeatureConfig.Builder(
                                BlockStateProvider.of(Blocks.OAK_LOG.getDefaultState()),
                                new LargeOakTrunkPlacer(3, 11, 0),
                                BlockStateProvider.of(Blocks.OAK_LEAVES.getDefaultState()),
                                new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4),
                                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                                .ignoreVines().build()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(treeAmt + 0.5))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.SWITCHGRASS_CONFIG)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(2));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.POPLAR_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.3))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.125))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(2))));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(12));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
    }
}
