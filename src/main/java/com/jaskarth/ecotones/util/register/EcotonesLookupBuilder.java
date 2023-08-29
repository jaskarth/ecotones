package com.jaskarth.ecotones.util.register;

import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;
import com.jaskarth.ecotones.api.FeatureList;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;

public class EcotonesLookupBuilder extends GenerationSettings.LookupBackedBuilder {
    private final RegistryEntryLookup<PlacedFeature> placedFeatureLookup;
    private final RegistryEntryLookup<ConfiguredCarver<?>> configuredCarverLookup;
    private final FeatureList list;

    public EcotonesLookupBuilder(FeatureList list) {
        super(null, null);
        this.list = list;
        this.placedFeatureLookup = EarlyRegistrationState.globalBuiltins.createRegistryLookup().getOrThrow(RegistryKeys.PLACED_FEATURE);
        this.configuredCarverLookup = EarlyRegistrationState.globalBuiltins.createRegistryLookup().getOrThrow(RegistryKeys.CONFIGURED_CARVER);
    }

    public GenerationSettings.LookupBackedBuilder feature(GenerationStep.Feature featureStep, RegistryKey<PlacedFeature> featureKey) {
        RegistryEntry.Reference<PlacedFeature> entry = placedFeatureLookup.getOrThrow(featureKey);

        PlacedFeature value = entry.value();

        PlacedFeature plf = new PlacedFeature(value.feature(), value.placementModifiers().stream().filter(m -> !(m instanceof BiomePlacementModifier)).toList());

        list.add(featureStep, plf);
        return this;
    }

    public GenerationSettings.LookupBackedBuilder carver(GenerationStep.Carver carverStep, RegistryKey<ConfiguredCarver<?>> carverKey) {
        this.carver(carverStep, configuredCarverLookup.getOrThrow(carverKey));
        return this;
    }
}