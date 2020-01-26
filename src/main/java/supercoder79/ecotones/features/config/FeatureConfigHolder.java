package supercoder79.ecotones.features.config;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.WeightedStateProvider;
import supercoder79.ecotones.blocks.EcotonesBlocks;

public class FeatureConfigHolder {
    public static RandomPatchFeatureConfig REEDS_CONFIG =
            (new RandomPatchFeatureConfig.Builder((new WeightedStateProvider()).addState(EcotonesBlocks.reeds.getDefaultState(), 1).addState(EcotonesBlocks.shortGrass.getDefaultState(), 3), new SimpleBlockPlacer())).tries(8).build();
    public static RandomPatchFeatureConfig MOSTLY_SHORT_GRASS_CONFIG =
            (new RandomPatchFeatureConfig.Builder((new WeightedStateProvider()).addState(EcotonesBlocks.shortGrass.getDefaultState(), 8).addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer())).tries(32).build();
    public static RandomPatchFeatureConfig SHORT_GRASS_CONFIG =
            (new RandomPatchFeatureConfig.Builder((new WeightedStateProvider()).addState(EcotonesBlocks.shortGrass.getDefaultState(), 2).addState(Blocks.GRASS.getDefaultState(), 1), new SimpleBlockPlacer())).tries(32).build();
    public static RandomPatchFeatureConfig RARELY_SHORT_GRASS_CONFIG =
            (new RandomPatchFeatureConfig.Builder((new WeightedStateProvider()).addState(EcotonesBlocks.shortGrass.getDefaultState(), 1).addState(Blocks.GRASS.getDefaultState(), 4), new SimpleBlockPlacer())).tries(32).build();
}
