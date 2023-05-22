package supercoder79.ecotones.blocks.sapling;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.world.biome.climatic.HazelGroveBiome;
import supercoder79.ecotones.world.features.EcotonesConfiguredFeature;

public final class HazelSaplingGenerator extends EcotonesSaplingGenerator {
    @Override
    protected @Nullable ConfiguredFeature<?, ?> getFeature(Random random, boolean bees) {
        return EcotonesConfiguredFeature.wrap(Feature.TREE, HazelGroveBiome.HAZEL_CONFIG).vanilla();
    }
}
