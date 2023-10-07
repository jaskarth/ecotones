package com.jaskarth.ecotones.world.worldgen.biome.climatic;

import com.google.common.collect.ImmutableSet;
import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.features.mc.RandomPatchFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.TreeType;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.PatchFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;

public class DeadSpruceForestBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("dead_spruce_forest", new DeadSpruceForestBiome(0.5F, 0.025F, false));
        Biome clearing = EarlyBiomeRegistry.register("dead_spruce_forest_clearing", new DeadSpruceForestBiome(0.5F, 0.025F, true));

        BiomeRegistries.registerBiomeVariantChance(biome, 3);
        BiomeRegistries.registerBiomeVariants(biome, clearing);
        Climate.WARM_VERY_HUMID.add(biome, 0.1);
        Climate.WARM_HUMID.add(biome, 0.05);
    }

    public DeadSpruceForestBiome(float depth, float scale, boolean clearing) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(0.8f);
        this.downfall(0.6f);
        this.precipitation(Biome.Precipitation.RAIN);
        this.associate(BiomeAssociations.LONLEY_SPRUCE_LIKE);

        BiomeDecorator.addSurfaceRocks(this);
        BiomeDecorator.addGrass(this, FeatureConfigHolder.SHORT_GRASS_CONFIG, 20);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(4))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(clearing ? 0.9 : 0.15))));

        if (clearing) {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.IMPROVED_BIRCH.configure(TreeType.RARE_DEAD_SPRUCE)
                            .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_DEAD_SPRUCE.decorationData)));
        } else {
            this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                    EcotonesFeatures.IMPROVED_BIRCH.configure(TreeType.DEAD_SPRUCE)
                            .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.DEAD_SPRUCE.decorationData)));
        }

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.TAIGA_FLOWERS)
                .decorate(new Spread32Decorator())
                .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                .spreadHorizontally()
                .repeat(5)
        );

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE, 3))).tries(64)
                        .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).cannotProject().build())
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(3));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.MOSS)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(4)
        );

        this.addFeature(GenerationStep.Feature.LAKES,
                EcotonesFeatures.PODZOL.configure(FeatureConfig.DEFAULT)
                        .spreadHorizontally()
                        .repeat(2));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.GROUND_PATCH.configure(new PatchFeatureConfig(EcotonesBlocks.PEAT_BLOCK.getDefaultState(), Blocks.GRASS_BLOCK, UniformIntProvider.create(1, 4)))
                        .spreadHorizontally()
                        .repeat(3)
                        .applyChance(24));

        DefaultBiomeFeatures.addForestFlowers(this.getGenerationSettings());
        DefaultBiomeFeatures.addMossyRocks(this.getGenerationSettings());

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());

        BiomeHelper.addDefaultFeatures(this);
    }
}
