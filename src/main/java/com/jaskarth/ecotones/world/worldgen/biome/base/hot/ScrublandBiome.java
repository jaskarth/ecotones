package com.jaskarth.ecotones.world.worldgen.biome.base.hot;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.client.particle.EcotonesParticles;
import com.jaskarth.ecotones.util.compat.LambdaFoxesCompat;
import com.jaskarth.ecotones.world.worldgen.decorator.*;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.RockFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.mc.RandomPatchFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.river.deco.CommonRiverDecorations;
import com.jaskarth.ecotones.world.worldgen.surface.DesertScrubSurfaceBuilder;
import com.jaskarth.ecotones.world.worldgen.surface.EcotonesSurfaces;

public class ScrublandBiome extends EcotonesBiomeBuilder {

    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("scrubland", new ScrublandBiome(0.5f, 0.075f, 1.6, 1));

        Climate.HOT_VERY_DRY.add(biome, 1);

        BiomeRegistries.registerRiverDecorator(biome, CommonRiverDecorations::buildDesertLushness);
    }

    protected ScrublandBiome(float depth, float scale, double hilliness, double volatility) {
        this.surfaceBuilder(EcotonesSurfaces.DESERT_SCRUB_BUILDER, DesertScrubSurfaceBuilder.SCRUB_CONFIG);
        this.precipitation(Biome.Precipitation.NONE);
        this.depth(depth);
        this.scale(scale);
        this.temperature(1.9F);
        this.downfall(0.2F);
        this.particleConfig(new BiomeParticleConfig(EcotonesParticles.SAND, 0.00125F));

        this.hilliness(hilliness);
        this.volatility(volatility);
        associate(BiomeAssociations.SAVANNA_LIKE);

        BiomeDecorator.addRock(this, Blocks.COBBLESTONE.getDefaultState(), 1, 8);
        BiomeDecorator.addAcaciaShrubs(this, 4.5, 0.05);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.DEAD_BUSH.getDefaultState())).tries(12).build())
                        .decorate(new Spread32Decorator())
                        .spreadHorizontally()
                        .repeat(3));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.CACTUS.getDefaultState())).tries(10).cannotProject().build())
                        .decorate(new SpreadDoubleDecorator())
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .repeat(40));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                                .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.15))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.CACTI.configure(FeatureConfig.DEFAULT)
                        .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(2))));

        BiomeDecorator.addSurfaceRocks(this);

        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.SMALL_CACTUS, 4);
        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.ONLY_TALL_GRASS_CONFIG, 4);

        BiomeDecorator.addGrass(this, FeatureConfigHolder.SCRUBLAND_CONFIG, 10);

        BiomeDecorator.addPatch(this, FeatureConfigHolder.DESERT_GRASS_CONFIG, 3);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.BIG_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.ACACIA_LOG.getDefaultState(), Blocks.ACACIA_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.ABOVE_QUALITY.configure()));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DESERTIFY_SOIL.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.5f, 2))));

        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.FLAME_LILY, 6);

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);

        if (LambdaFoxesCompat.isEnabled()) {
            this.addSpawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));
        }
    }
}
