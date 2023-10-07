package com.jaskarth.ecotones.world.worldgen.biome.climatic;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.placementmodifier.HeightmapPlacementModifier;
import net.minecraft.world.gen.placementmodifier.NoiseThresholdCountPlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.util.compat.LambdaFoxesCompat;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.decorator.SpreadDoubleDecorator;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.mc.RandomPatchFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.river.deco.CommonRiverDecorations;
import com.jaskarth.ecotones.world.worldgen.surface.DesertScrubSurfaceBuilder;
import com.jaskarth.ecotones.world.worldgen.surface.EcotonesSurfaces;

public class DesertShrublandBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("desert_shrubland", new DesertShrublandBiome(0.4f, 0.1f, 2.4, 0.94));

        Climate.HOT_DESERT.add(biome, 0.3);
        Climate.WARM_DESERT.add(biome, 0.2);

        BiomeRegistries.registerRiverDecorator(biome, CommonRiverDecorations::buildDesertLushness);
    }


    protected DesertShrublandBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(EcotonesSurfaces.DESERT_SCRUB_BUILDER, DesertScrubSurfaceBuilder.DESERT_SHRUBLAND_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(2F);
        this.downfall(0.1F);
        this.associate(BiomeAssociations.DESERT_LIKE);

        this.precipitation(Biome.Precipitation.NONE);

        this.hilliness(hilliness);
        this.volatility(volatility);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.CACTUS.getDefaultState())).tries(10).cannotProject().build())
                        .decorate(new SpreadDoubleDecorator()).decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(20));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.DEAD_BUSH.getDefaultState())).tries(12).build())
                        .decorate(new SpreadDoubleDecorator()).decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(5));

        BiomeDecorator.addGrass(this, FeatureConfigHolder.DESERT_GRASS_CONFIG, 12);
        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.SMALL_CACTUS, 4);

        BiomeDecorator.addAcaciaShrubs(this, 1.5, 8.5);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BIG_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(0.25))));

        BiomeDecorator.addPatch(this, FeatureConfigHolder.SWITCHGRASS_CONFIG, 3);
        BiomeDecorator.addPatch(this, FeatureConfigHolder.ONLY_TALL_GRASS_CONFIG, 2);
        BiomeDecorator.addGrass(this, FeatureConfigHolder.SCRUBLAND_CONFIG, 14);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.25))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.25))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.FLAME_LILY)
                        .decorate(new Spread32Decorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(6));


        

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);

        if (LambdaFoxesCompat.isEnabled()) {
            this.addSpawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
        }
    }
}
