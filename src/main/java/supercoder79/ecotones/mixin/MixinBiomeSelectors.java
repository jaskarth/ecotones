package supercoder79.ecotones.mixin;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = {"net.fabricmc.fabric.api.biome.v1.BiomeSelectors", "org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors"})
@Pseudo
public class MixinBiomeSelectors {
    // Somehow this works, as both lambdas are 3rd in sequence in the classfile
    @Inject(method = "lambda$foundInOverworld$3", at = @At("HEAD"), cancellable = true, require = 0)
    private static void selectEcotonesAsOverworld(BiomeSelectionContext context, CallbackInfoReturnable<Boolean> cir) {
        if (context.getBiomeKey().getValue().getNamespace().equals("ecotones")) {
            cir.setReturnValue(true);
        }
    }
}
