package com.jaskarth.ecotones.world.worldgen.biome.base.warm;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import com.jaskarth.ecotones.world.worldgen.decorator.ChanceDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesConfiguredFeature;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.api.TreeType;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.RockFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;

public class TemperateRainforestBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("temperate_rainforest", new TemperateRainforestBiome(0.5f, 0.4f, 1.8, 0.93));

        Climate.WARM_RAINFOREST.add(biome, 1);
    }

    protected TemperateRainforestBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);
        this.precipitation(Biome.Precipitation.RAIN);
        this.depth(depth);
        this.scale(scale);
        this.temperature(0.8F);
        this.downfall(1.0F);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.associate(BiomeAssociations.JUNGLE_LIKE);

        

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.ROCK.configure(new RockFeatureConfig(Blocks.COBBLESTONE.getDefaultState(), 1))
                        .decorate(EcotonesDecorators.LARGE_ROCK.configure(new ChanceDecoratorConfig(6))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.MOSS)
                        .decorate(new Spread32Decorator())
                        .spreadHorizontally()
                        .repeat(8));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.JUNGLE_LOG.getDefaultState(), Blocks.JUNGLE_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(5.0))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(3.0))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(2.0))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BRANCHING_OAK.configure(TreeType.LUSH_JUNGLE)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.LUSH_JUNGLE.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BRANCHING_OAK.configure(TreeType.RARE_VARYING_OAK)
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_VARYING_OAK.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.JUNGLE_PALM_TREE.configure(FeatureConfigHolder.JUNGLE)
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(3.25, true))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesConfiguredFeature.wrap(Feature.TREE, FeatureConfigHolder.MEGA_JUNGLE)
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(2.75, true))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.MOSS)
                        .decorate(new Spread32Decorator())
                        .spreadHorizontally()
                        .repeat(4));

        BiomeDecorator.addGrass(this, FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG, 20);

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);
    }
}
