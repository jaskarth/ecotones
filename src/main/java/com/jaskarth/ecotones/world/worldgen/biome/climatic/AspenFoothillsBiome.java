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
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.ClimateType;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.world.worldgen.decorator.ChanceDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.DuckweedFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.RockFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.structure.EcotonesStructures;
import com.jaskarth.ecotones.world.worldgen.surface.EcotonesSurfaces;

public class AspenFoothillsBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("aspen_foothills", new AspenFoothillsBiome(1.5F, 0.075F, 5, 0.95, false));
        Biome clearing = EarlyBiomeRegistry.register("aspen_foothills_clearing", new AspenFoothillsBiome(1.5F, 0.025F, 5, 0.95, true));

        BiomeRegistries.registerBiomeVariantChance(biome, 3);
        BiomeRegistries.registerBiomeVariants(biome, clearing);
        Climate.WARM_VERY_HUMID.add(biome, 0.1);
        Climate.WARM_HUMID.add(biome, 0.2);
        Climate.WARM_MILD.add(biome, 0.1);

        BiomeRegistries.addMountainBiome(biome);
        BiomeRegistries.addMountainBiome(clearing);
        BiomeRegistries.addMountainType(ClimateType.MOUNTAIN_FOOTHILLS, biome);
        BiomeRegistries.addMountainType(ClimateType.MOUNTAIN_FOOTHILLS, clearing);

        Climate.WARM_MILD.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 0.3);
        Climate.WARM_HUMID.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 0.3);
        Climate.WARM_VERY_HUMID.add(ClimateType.MOUNTAIN_FOOTHILLS, biome, 0.3);
    }

    public AspenFoothillsBiome(float depth, float scale, double hilliness, double volatility, boolean clearing) {
        this.surfaceBuilder(EcotonesSurfaces.GRASS_MOUNTAIN, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(0.575F);
        this.downfall(0.825F);
        this.precipitation(Biome.Precipitation.RAIN);

        this.foliageColor(0xffe261);

        this.hilliness(hilliness);
        this.volatility(volatility);
        this.addStructureFeature(EcotonesStructures.CAMPFIRE_BIRCH);
        this.associate(BiomeAssociations.MOUNTAIN_LIKE, BiomeAssociations.BIRCH);

        DefaultBiomeFeatures.addForestFlowers(this.getGenerationSettings());

        BiomeDecorator.addClover(this, 3);

        BiomeDecorator.addRock(this, 4);

        BiomeDecorator.addSurfaceRocks(this);

        BiomeDecorator.addGrass(this, FeatureConfigHolder.SHORT_GRASS_CONFIG, 7);

        BiomeDecorator.addShrubs(this, Blocks.BIRCH_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 1, clearing ? 4 : 3);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.ASPEN_TREE.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(clearing ? 0.2 : 4.2))));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.ASPEN_TREE.configure(new SimpleTreeFeatureConfig(Blocks.BIRCH_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(clearing ? 0.15 : 0.08))));

        this.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION,
                EcotonesFeatures.DUCK_NEST.configure(DefaultFeatureConfig.INSTANCE)
                        .decorate(EcotonesDecorators.DUCK_NEST.configure(new ShrubDecoratorConfig(0.2))));

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

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.SMALL_ROCK.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(6));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION,
                EcotonesFeatures.ROCK_SPIRE.configure(FeatureConfigHolder.STONE_SPIRE)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .applyChance(8));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());

        BiomeHelper.addDefaultFeatures(this);
    }
}
