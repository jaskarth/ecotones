package supercoder79.ecotones.world.river.deco;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.decorator.HeightmapPlacementModifier;
import net.minecraft.world.gen.decorator.NoiseThresholdCountPlacementModifier;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.CocoaBeansTreeDecorator;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import supercoder79.ecotones.world.decorator.CountExtraDecoratorConfig;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.decorator.ShrubDecoratorConfig;
import supercoder79.ecotones.world.decorator.Spread32Decorator;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public final class CommonRiverDecorations {
    private static final TreeFeatureConfig JUNGLE_TREE = new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.JUNGLE_LOG.getDefaultState()),
            new StraightTrunkPlacer(4, 8, 0),
            BlockStateProvider.of(Blocks.JUNGLE_LEAVES.getDefaultState()),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
            new TwoLayersFeatureSize(1, 0, 1))
            .decorators(ImmutableList.of(
                    new CocoaBeansTreeDecorator(0.2F))
            ).ignoreVines().build();

    public static void buildDesertLushness(DecorationCollector decorations) {
        // Grass replacing sand
        decorations.add(EcotonesFeatures.SURFACE_PATCH.configure(FeatureConfig.DEFAULT)
                .spreadHorizontally()
                .repeat(6), true);

        // Tall grass
        decorations.add(EcotonesFeatures.RANDOM_PATCH.configure(FeatureConfigHolder.RARELY_SHORT_GRASS_CONFIG)
                .decorate(new Spread32Decorator())
                .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                .spreadHorizontally()
                .decorate(NoiseThresholdCountPlacementModifier.of(-0.8D, 12, 20)), true);


        decorations.add(EcotonesFeatures.SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(3.5))), true);


        decorations.add(EcotonesFeatures.WIDE_SHRUB.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()))
                .decorate(EcotonesDecorators.SHRUB_PLACEMENT_DECORATOR.configure(new ShrubDecoratorConfig(1.2))), true);


        decorations.add(EcotonesFeatures.JUNGLE_PALM_TREE.configure(JUNGLE_TREE)
                .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                .spreadHorizontally()
                .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.15F, 1))), true);

        decorations.add(
                EcotonesFeatures.CACTI.configure(FeatureConfig.DEFAULT)
                        .decorate(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING))
                        .spreadHorizontally()
                        .decorate(EcotonesDecorators.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(2, 0.75f, 1))), true);
    }
}
