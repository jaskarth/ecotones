package com.jaskarth.ecotones.world.worldgen.biome.climatic;

import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.TreeType;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.decorator.ChanceDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.CattailFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.RockFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.structure.EcotonesStructures;

public class MapleForestBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("maple_forest", new MapleForestBiome(0.5f, 0.1f, 1.4, 0.98));

        Climate.WARM_VERY_HUMID.add(biome, 0.1);
        Climate.WARM_HUMID.add(biome, 0.15);
    }

    protected MapleForestBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);
        this.precipitation(Biome.Precipitation.RAIN);
        this.depth(depth);
        this.scale(scale);
        this.temperature(0.8F);
        this.downfall(0.8F);
        this.associate(BiomeAssociations.FOREST_LIKE);

        this.hilliness(hilliness);
        this.volatility(volatility);

        this.addStructureFeature(EcotonesStructures.CAMPFIRE_OAK);

        BiomeDecorator.addRock(this, Blocks.COBBLESTONE.getDefaultState(), 1, 4);

        BiomeDecorator.addClover(this, 1);

        BiomeDecorator.addShrubs(this, Blocks.SPRUCE_LOG.getDefaultState(), EcotonesBlocks.MAPLE_LEAVES.getDefaultState(), 2.0, 1.5);

        BiomeDecorator.addSpruceShrubs(this, 0.05, 0.5);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.MAPLE_TREE.configure(TreeType.STANDARD_MAPLE)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.STANDARD_MAPLE.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.MAPLE_TREE.configure(TreeType.DEAD_MAPLE)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.DEAD_MAPLE.decorationData)));

        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.BLUEBELL_PATCH, 3);

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
                EcotonesFeatures.SMALL_SPRUCE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.65))));

        BiomeDecorator.addGrass(this, FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG, 12);

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);
    }
}
