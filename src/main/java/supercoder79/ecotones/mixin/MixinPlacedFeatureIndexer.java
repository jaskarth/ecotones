package supercoder79.ecotones.mixin;

import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.util.PlacedFeatureIndexer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Function;

@Mixin(PlacedFeatureIndexer.class)
public class MixinPlacedFeatureIndexer {
    @Inject(method = "collectIndexedFeatures", at = @At("HEAD"), cancellable = true)
    private static <T> void rejectForEcotones(List<T> biomes, Function<T, List<RegistryEntryList<PlacedFeature>>> biomesToPlacedFeaturesList, boolean listInvolvedBiomesOnFailure, CallbackInfoReturnable<List<PlacedFeatureIndexer.IndexedFeatures>> cir) {
        for (T biome : biomes) {
            if (biome instanceof RegistryKey<?> rk) {
                if (rk.getValue().getNamespace().equals("ecotones")) {
                    // Ecotones generator does not use this
                    cir.setReturnValue(null);
                }
            }
        }
    }
}
