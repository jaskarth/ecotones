package com.jaskarth.ecotones.world.worldgen.biome.climatic;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import com.jaskarth.ecotones.world.worldgen.decorator.*;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesConfiguredFeature;
import com.jaskarth.ecotones.world.worldgen.river.deco.CommonRiverDecorations;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.RockFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.surface.EcotonesSurfaces;

public class DrySteppeBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("dry_steppe", new DrySteppeBiome(0.5F, 0.025F, 3, 0.96));
        Biome thicket = EarlyBiomeRegistry.register("dry_steppe_thicket", new DrySteppeBiome(0.5F, 0.025F, 3, 0.96, 6.4));

        BiomeRegistries.registerBiomeVariantChance(biome, 4);
        BiomeRegistries.registerBiomeVariants(biome, thicket);

        Climate.HOT_VERY_DRY.add(biome, 0.6);
        Climate.HOT_DRY.add(biome, 0.6);
        Climate.WARM_DRY.add(biome, 0.3);

        BiomeRegistries.registerRiverDecorator(biome, CommonRiverDecorations::buildDesertLushness);
    }

    public DrySteppeBiome(float depth, float scale, double hilliness, double volatility) {
        this(depth, scale, hilliness, volatility, 1.1);
    }

    public DrySteppeBiome(float depth, float scale, double hilliness, double volatility, double treeCount) {
        this.surfaceBuilder(EcotonesSurfaces.DRY_STEPPE, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(1.5f);
        this.downfall(0.05f);
        this.precipitation(Biome.Precipitation.NONE);
        this.associate(BiomeAssociations.SAVANNA_LIKE);

        this.grassColor(0xc4b956);
        this.foliageColor(0xaba252);

        this.hilliness(hilliness);
        this.volatility(volatility);

        BiomeDecorator.addRock(this, Blocks.COBBLESTONE.getDefaultState(), 1, 4);
        BiomeDecorator.addRock(this, Blocks.COBBLESTONE.getDefaultState(), 0, 2);
        BiomeDecorator.addRock(this, 4);
        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.SMALL_CACTUS, 4);
        BiomeDecorator.addGrass(this, FeatureConfigHolder.DRY_STEPPE_CONFIG, 8);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesConfiguredFeature.wrap(Feature.TREE, FeatureConfigHolder.DRY_STEPPE_TREE)
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(treeCount))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DESERTIFY_SOIL.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(5, 0.75f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CACTI.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(2, 0.75f, 1))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.LARGE_CACTUS_PATCH)
                        .decorate(new SpreadDoubleDecorator()).decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(20));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(8));

        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.FLAME_LILY, 6);

        BiomeHelper.addDefaultFeatures(this);
        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
    }
}
