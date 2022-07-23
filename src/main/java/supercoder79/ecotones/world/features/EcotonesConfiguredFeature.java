package supercoder79.ecotones.world.features;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.placementmodifier.*;
import supercoder79.ecotones.world.decorator.ChanceDecoratorConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EcotonesConfiguredFeature<FC extends FeatureConfig, F extends Feature<FC>> {
    private final F feature;
    private final FC config;

    public EcotonesConfiguredFeature(F feature, FC config) {

        this.feature = feature;
        this.config = config;
    }

    private final List<PlacementModifier> decorators = new ArrayList<>();

    public static <FC extends FeatureConfig, F extends Feature<FC>> EcotonesConfiguredFeature<FC, F> wrap(ConfiguredFeature<FC, F> feature) {
        return new EcotonesConfiguredFeature<>(feature.feature(), feature.config());
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> EcotonesConfiguredFeature<FC, F> wrap(F feature, FC config) {
        return new EcotonesConfiguredFeature<>(feature, config);
    }

    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos origin) {
        return vanilla().generate(world, chunkGenerator, new CheckedRandom(random.nextLong()), origin);
    }

    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, net.minecraft.util.math.random.Random random, BlockPos origin) {
        return vanilla().generate(world, chunkGenerator, random, origin);
    }

    // 1.17 style back->front
    public EcotonesConfiguredFeature<FC, F> decorate(PlacementModifier decorator) {
        decorators.add(0, decorator);

        return this;
    }

    // 1.18 style front->back
    public EcotonesConfiguredFeature<FC, F> decorateAll(PlacementModifier... decorator) {
        decorators.addAll(List.of(decorator));

        return this;
    }

    public EcotonesConfiguredFeature<FC, ?> spreadHorizontally() {
        return decorate(SquarePlacementModifier.of());
    }

    public EcotonesConfiguredFeature<FC, ?> applyChance(int chance) {
        return decorate(RarityFilterPlacementModifier.of(chance));
    }

    public EcotonesConfiguredFeature<FC, ?> repeat(IntProvider count) {
        return decorate(CountPlacementModifier.of(count));
    }

    public EcotonesConfiguredFeature<FC, ?> repeat(int count) {
        return decorate(CountPlacementModifier.of(count));
    }

    public EcotonesConfiguredFeature<FC, ?> repeat(int min, int max) {
        return decorate(CountPlacementModifier.of(UniformIntProvider.create(min, max)));
    }

    public EcotonesConfiguredFeature<FC, ?> uniformRange(YOffset min, YOffset max) {
        return decorate(HeightRangePlacementModifier.of(UniformHeightProvider.create(min, max)));
    }

    public EcotonesConfiguredFeature<FC, ?> range(HeightProvider config) {
        return decorate(HeightRangePlacementModifier.of(config));
    }

    public ConfiguredFeature<FC, F> vanilla() {
        return new ConfiguredFeature<>(this.feature, this.config);
    }

    public PlacedFeature placed() {
        return new PlacedFeature(new RegistryEntry.Direct<>(vanilla()), decorators);
    }

    public PlacedFeature placed(RegistryEntry<ConfiguredFeature<?, ?>> holder) {
        return new PlacedFeature(holder, decorators);
    }
}
