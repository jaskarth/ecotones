package supercoder79.ecotones.mixin;

import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supercoder79.ecotones.util.FoliageColorResolver;

@Mixin(BiomeColors.class)
public class MixinBiomeColors {

    @SuppressWarnings("UnresolvedMixinReference") // Synthetic lambda method
    @Inject(method = "method_23791", at = @At("HEAD"), cancellable = true)
    private static void handleFoliageColors(Biome biome, double x, double z, CallbackInfoReturnable<Integer> cir) {
        if (biome instanceof FoliageColorResolver) {
            cir.setReturnValue(((FoliageColorResolver)biome).getFoliageColorAt(x, z));
        }
    }
}
