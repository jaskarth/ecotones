package com.jaskarth.ecotones.world.worldgen.biome.climatic;

import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import com.jaskarth.ecotones.util.compat.AurorasDecoCompat;
import com.jaskarth.ecotones.world.data.EcotonesData;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.api.Climate;
import com.jaskarth.ecotones.api.SimpleTreeDecorationData;
import com.jaskarth.ecotones.api.TreeType;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.OakTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.structure.EcotonesStructures;

public class FlowerPrairieBiome extends EcotonesBiomeBuilder {
    public static void init() {
        Biome biome = EarlyBiomeRegistry.register("flower_prairie", new FlowerPrairieBiome(0.5F, 0.025F));

        Climate.WARM_MODERATE.add(biome, 0.2);
        Climate.WARM_MILD.add(biome, 0.2);
    }

    public FlowerPrairieBiome(float depth, float scale) {
        this.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG);

        this.depth(depth);
        this.scale(scale);
        this.temperature(1F);
        this.downfall(0.4F);

        this.grassColor(0xabcf59);
        this.foliageColor(0xabcf59);

        this.hilliness(2.0);
        this.volatility(1.1);

        this.precipitation(Biome.Precipitation.RAIN);
        this.associate(BiomeAssociations.PLAINS_LIKE);

        this.addStructureFeature(EcotonesStructures.CAMPFIRE_OAK);

        DefaultBiomeFeatures.addForestFlowers(this.getGenerationSettings());
        this.getGenerationSettings().feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.FLOWER_FLOWER_FOREST);

        BiomeDecorator.addSurfaceRocks(this);

        BiomeDecorator.addGrass(this, FeatureConfigHolder.PRAIRIE_CONFIG, 18);

        BiomeDecorator.addClover(this, 1);
        BiomeDecorator.addOakShrubs(this, 1, 0.85);

        DefaultBiomeFeatures.addForestFlowers(this.getGenerationSettings());

        BiomeDecorator.addPatchChance(this, FeatureConfigHolder.SWITCHGRASS_CONFIG, 2);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.DENSE_LAVENDER_LILAC)
                        .decorate(EcotonesDecorators.DATA_FUNCTION.configure(EcotonesData.FLOWER_MOSAIC)));

        AurorasDecoCompat.applyDaffodils(this);

        this.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
                EcotonesFeatures.BEEHIVES.configure(FeatureConfig.DEFAULT)
                        .applyChance(32)
                        .spreadHorizontally()
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING)));

        BiomeDecorator.addLilacs(this, 2);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.STRAIGHT_OAK.configure(new OakTreeFeatureConfig(12, 7))
                        .decorate(EcotonesDecorators.TREE_DECORATOR.configure(TreeType.RARE_LARGE_CLUSTERED_OAK.decorationData)));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(0.1))));

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);
    }
}
