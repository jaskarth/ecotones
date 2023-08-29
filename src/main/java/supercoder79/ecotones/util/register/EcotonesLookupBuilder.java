package supercoder79.ecotones.util.register;

import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;
import supercoder79.ecotones.api.FeatureList;

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
        list.add(featureStep, entry.value());
        return this;
    }

    public GenerationSettings.LookupBackedBuilder carver(GenerationStep.Carver carverStep, RegistryKey<ConfiguredCarver<?>> carverKey) {
        this.carver(carverStep, configuredCarverLookup.getOrThrow(carverKey));
        return this;
    }
}