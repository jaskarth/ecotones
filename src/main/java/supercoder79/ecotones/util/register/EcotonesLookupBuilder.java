package supercoder79.ecotones.util.register;

import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;

public class EcotonesLookupBuilder extends GenerationSettings.LookupBackedBuilder {
    private final RegistryEntryLookup<PlacedFeature> placedFeatureLookup;
    private final RegistryEntryLookup<ConfiguredCarver<?>> configuredCarverLookup;

    public EcotonesLookupBuilder() {
        super(null, null);
        this.placedFeatureLookup = EarlyRegistrationState.globalBuiltins.createRegistryLookup().getOrThrow(RegistryKeys.PLACED_FEATURE);
        this.configuredCarverLookup = EarlyRegistrationState.globalBuiltins.createRegistryLookup().getOrThrow(RegistryKeys.CONFIGURED_CARVER);
    }

    public GenerationSettings.LookupBackedBuilder feature(GenerationStep.Feature featureStep, RegistryKey<PlacedFeature> featureKey) {
        this.addFeature(featureStep.ordinal(), placedFeatureLookup.getOrThrow(featureKey));
        return this;
    }

    public GenerationSettings.LookupBackedBuilder carver(GenerationStep.Carver carverStep, RegistryKey<ConfiguredCarver<?>> carverKey) {
        this.carver(carverStep, configuredCarverLookup.getOrThrow(carverKey));
        return this;
    }
}