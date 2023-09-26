package com.jaskarth.ecotones.world.worldgen.biome.special;

import com.google.common.collect.ImmutableList;
import com.jaskarth.ecotones.world.worldgen.biome.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.heightprovider.BiasedToBottomHeightProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import com.jaskarth.ecotones.world.worldgen.decorator.CountExtraDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesConfiguredFeature;
import com.jaskarth.ecotones.world.worldgen.surface.system.SurfaceBuilder;
import net.minecraft.world.gen.treedecorator.CocoaBeansTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import com.jaskarth.ecotones.api.BiomeRegistries;
import com.jaskarth.ecotones.world.worldgen.decorator.EcotonesDecorators;
import com.jaskarth.ecotones.world.worldgen.decorator.ShrubDecoratorConfig;
import com.jaskarth.ecotones.world.worldgen.decorator.Spread32Decorator;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeatures;
import com.jaskarth.ecotones.world.worldgen.features.config.FeatureConfigHolder;
import com.jaskarth.ecotones.world.worldgen.features.config.SimpleTreeFeatureConfig;
import com.jaskarth.ecotones.world.worldgen.surface.EcotonesSurfaces;

public class GreenSpiresBiome extends EcotonesBiomeBuilder {
    public static final TreeFeatureConfig JUNGLE_TREE = new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.JUNGLE_LOG.getDefaultState()),
            new StraightTrunkPlacer(4, 8, 0),
            BlockStateProvider.of(Blocks.JUNGLE_LEAVES.getDefaultState()),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
            new TwoLayersFeatureSize(1, 0, 1))
            .decorators(ImmutableList.of(
                    new CocoaBeansTreeDecorator(0.2F),
                    TrunkVineTreeDecorator.INSTANCE,
                    new LeavesVineTreeDecorator(0.2f))
            ).ignoreVines().build();

    public static void init() {
        Biome biome = EarlyBiomeRegistry.register(new Identifier("ecotones", "green_spires"), new GreenSpiresBiome().build());
        BiomeRegistries.registerSpecialBiome(biome, id -> true);
        BiomeRegistries.registerBigSpecialBiome(biome, 400);
        BiomeRegistries.registerNoBeachBiome(biome);
    }


    protected GreenSpiresBiome() {
        this.surfaceBuilder(EcotonesSurfaces.GREEN_SPIRES_BUILDER, SurfaceBuilder.GRASS_CONFIG);

        this.depth(0.2f);
        this.scale(1.75F);
        this.temperature(1F);
        this.downfall(1F);

        this.precipitation(Biome.Precipitation.RAIN);
        this.associate(BiomeAssociations.JUNGLE_LIKE);

        this.skyColor(0xadc1cc);
        this.grassColor(0x73a859);
        this.foliageColor(0x74ad57);
        this.waterColor(0x74ad57);
        this.waterFogColor(0x73a859);

        BiomeDecorator.addSurfaceRocks(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesFeatures.JUNGLE_PALM_TREE.configure(JUNGLE_TREE)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE))
                        .spreadHorizontally()
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(2, 0.5f, 1)))
        );

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                EcotonesConfiguredFeature.wrap(Feature.TREE, JUNGLE_TREE)
                        .decorate(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN)))
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE))
                        .spreadHorizontally()
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(6, 0.5f, 1)))
        );

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, EcotonesConfiguredFeature.wrap(Feature.SEAGRASS, (new ProbabilityConfig(0.75f)))
                .decorate(HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG))
                .spreadHorizontally()
                .repeat(152));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, EcotonesConfiguredFeature.wrap(Feature.VINES, (FeatureConfig.DEFAULT))
                .range(BiasedToBottomHeightProvider.create(YOffset.fixed(64), YOffset.fixed(192), 32))
                .spreadHorizontally()
                .repeat(2)
                .repeat(256)
        );

        BiomeDecorator.addJungleShrubs(this, 8, 4);
        BiomeDecorator.addGrass(this, FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG, 20);

        

        BiomeHelper.addDefaultSpawns(this.getSpawnSettings());
        BiomeHelper.addDefaultFeatures(this);
    }
}
