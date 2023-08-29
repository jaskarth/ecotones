package com.jaskarth.ecotones.world.blocks.sapling;

import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import org.jetbrains.annotations.Nullable;
import com.jaskarth.ecotones.world.worldgen.biome.climatic.HazelGroveBiome;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesConfiguredFeature;

public final class HazelSaplingGenerator extends EcotonesSaplingGenerator {
    @Override
    protected @Nullable ConfiguredFeature<?, ?> getFeature(Random random, boolean bees) {
        return EcotonesConfiguredFeature.wrap(Feature.TREE, HazelGroveBiome.HAZEL_CONFIG).vanilla();
    }
}
