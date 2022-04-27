package supercoder79.ecotones.world.edge;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.world.biome.base.warm.LichenWoodlandBiome;
import supercoder79.ecotones.world.biome.base.warm.PrairieBiome;
import supercoder79.ecotones.world.biome.base.warm.SpruceForestBiome;
import supercoder79.ecotones.world.biome.climatic.TemperateGrasslandBiome;
import supercoder79.ecotones.world.decorator.EcotonesDecorators;
import supercoder79.ecotones.world.features.EcotonesConfiguredFeature;
import supercoder79.ecotones.world.features.EcotonesFeatures;
import supercoder79.ecotones.world.features.config.FeatureConfigHolder;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

public final class EcotonesEdgeDecorations {
    public static void init() {
        BiomeRegistries.registerEdgeDecorator(PrairieBiome.INSTANCE, EcotonesEdgeDecorations::lichenWoodland);
        BiomeRegistries.registerEdgeDecorator(TemperateGrasslandBiome.INSTANCE, EcotonesEdgeDecorations::lichenWoodland);
        BiomeRegistries.registerEdgeDecorator(PrairieBiome.INSTANCE, EcotonesEdgeDecorations::spruceForest);
        BiomeRegistries.registerEdgeDecorator(TemperateGrasslandBiome.INSTANCE, EcotonesEdgeDecorations::spruceForest);
    }

    private static void lichenWoodland(EdgeDecorationCollector deco) {
        deco.add(LichenWoodlandBiome.INSTANCE, d ->
                    EcotonesFeatures.SMALL_SPRUCE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.SPRUCE_LEAVES.getDefaultState()))
                            .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(d * 3.5)))
        );

        deco.add(LichenWoodlandBiome.INSTANCE, d ->
                EcotonesFeatures.PODZOL.configure(FeatureConfig.DEFAULT)
                        .spreadHorizontally()
                        .applyChance((int) ((1 - d) * 25))
        );

        deco.add(LichenWoodlandBiome.INSTANCE, d ->
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.OAK_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(d * 0.2)))
        );
    }

    private static void spruceForest(EdgeDecorationCollector deco) {
        deco.add(SpruceForestBiome.INSTANCE, d ->
                EcotonesConfiguredFeature.wrap(Feature.TREE, FeatureConfigHolder.SPRUCE_TREE_CONFIG)
                        .decorate(EcotonesDecorators.SIMPLE_TREE_DECORATOR.configure(new SimpleTreeDecorationData(d * 3.75)))
        );

        deco.add(SpruceForestBiome.INSTANCE, d ->
                EcotonesFeatures.PODZOL.configure(FeatureConfig.DEFAULT)
                        .spreadHorizontally()
                        .applyChance((int) ((1 - d) * 25))
        );

        deco.add(SpruceForestBiome.INSTANCE, d ->
                EcotonesFeatures.DEAD_TREE.configure(new SimpleTreeFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), Blocks.AIR.getDefaultState()))
                        .decorate(EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR.configure(new SimpleTreeDecorationData(d * 0.2)))
        );
    }
}
