package com.jaskarth.ecotones.world.worldgen.biome.climatic;

import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import com.jaskarth.ecotones.world.worldgen.decorator.CountExtraDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesConfiguredFeature;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.CattailFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.structure.EcotonesStructures;

public class HazelGroveBiome extends EcotonesBiomeBuilder {
    public static final TreeFeatureConfig HAZEL_CONFIG =
            new TreeFeatureConfig.Builder(
                    BlockStateProvider.of(Blocks.OAK_LOG.getDefaultState()),
                    new StraightTrunkPlacer(6, 2, 0),
                    BlockStateProvider.of(EcotonesBlocks.HAZEL_LEAVES.getDefaultState()),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 4),
                    new TwoLayersFeatureSize(2, 0, 2))
        .ignoreVines().build();

    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("hazel_grove", new HazelGroveBiome(false, false));
        Biome clearing = EarlyBiomeRegistry.register("hazel_grove_clearing", new HazelGroveBiome(true, false));
        Biome hilly = EarlyBiomeRegistry.register("hazel_grove_hilly", new HazelGroveBiome(false, true));
        Biome hillyClearing = EarlyBiomeRegistry.register("hazel_grove_hilly_clearing", new HazelGroveBiome(true, true));

        BiomeRegistries.registerBiomeVariantChance(biome, 3);
        BiomeRegistries.registerBiomeVariants(biome, clearing, hilly, hillyClearing);
        Climate.HOT_VERY_HUMID.add(biome, 0.1);
        Climate.HOT_HUMID.add(biome, 0.1);
    }


    protected HazelGroveBiome(boolean clearing, boolean hilly) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(hilly ? 0.9f : 0.25f);
        this.scale(hilly ? 0.6f : 0.05f);
        this.temperature(1F);
        this.downfall(1F);
        this.associate(BiomeAssociations.FOREST_LIKE);

        this.precipitation(Biome.Precipitation.RAIN);

        this.grassColor(0xaebd11);
        this.foliageColor(0xa67c12);
        this.skyColor(0xc6e4f5);

        this.hilliness(hilly ? 4.0 : 1.5);
        this.volatility(hilly ? 0.8 : 1.0);

        this.addStructureFeature(EcotonesStructures.CAMPFIRE_OAK);

        if (clearing) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesConfiguredFeature.wrap(Feature.TREE, HAZEL_CONFIG)
                            .decorate(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN)))
                            .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                            .spreadHorizontally()
                            .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.5f, 1)))
            );

            BiomeDecorator.addOakShrubs(this, 6, 1.4);
        } else {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesConfiguredFeature.wrap(Feature.TREE, HAZEL_CONFIG)
                            .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(3.5))));

            BiomeDecorator.addOakShrubs(this, 3, 0.95);
        }

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

        BiomeDecorator.addGrass(this, FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG, 8);

        this.addFeature(GenerationStep.Feature.LAKES,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(8));

        

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);
    }
}
