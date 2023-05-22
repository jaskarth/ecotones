package supercoder79.ecotones.mixin;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.ecotones.util.register.EarlyRegistrationState;

@Mixin(BuiltinBiomes.class)
public class MixinBuiltinBiomes {
    @Inject(method = "bootstrap", at = @At("HEAD"))
    private static void ecotonesBootstrap(Registerable<Biome> biomeRegisterable, CallbackInfo ci) {
    }
}
