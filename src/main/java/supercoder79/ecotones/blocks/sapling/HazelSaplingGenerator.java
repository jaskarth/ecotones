package supercoder79.ecotones.blocks.sapling;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.ecotones.world.biome.climatic.HazelGroveBiome;
import supercoder79.ecotones.world.features.EcotonesConfiguredFeature;

public final class HazelSaplingGenerator extends SaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bl) {
        return new RegistryEntry.Direct<>(EcotonesConfiguredFeature.wrap(Feature.TREE, HazelGroveBiome.HAZEL_CONFIG).vanilla());
    }
}
