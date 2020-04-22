package supercoder79.ecotones.blocks.sapling;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.ecotones.biome.special.HazelGroveBiome;

import java.util.Random;

public class HazelSaplingGenerator extends SaplingGenerator {
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return Feature.TREE.configure(HazelGroveBiome.HAZEL_CONFIG);
    }
}
