package supercoder79.ecotones.world.features;

import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import supercoder79.ecotones.world.decorator.ChanceDecoratorConfig;

import java.util.ArrayList;
import java.util.List;

public class EcotonesConfiguredFeature<FC extends FeatureConfig, F extends Feature<FC>> extends ConfiguredFeature<FC, F> {
    public EcotonesConfiguredFeature(F feature, FC config) {
        super(feature, config);
    }

    private final List<PlacementModifier> decorators = new ArrayList<>();

    public static <FC extends FeatureConfig, F extends Feature<FC>> EcotonesConfiguredFeature<FC, F> wrap(ConfiguredFeature<FC, F> feature) {
        return new EcotonesConfiguredFeature<>(feature.feature, feature.config);
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

    public EcotonesConfiguredFeature<FC, ?> range(RangeDecoratorConfig config) {
        return decorate(HeightRangePlacementModifier.of(config.heightProvider));
    }

    public PlacedFeature placed() {
        return new PlacedFeature(() -> this, decorators);
    }
}
