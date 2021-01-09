package supercoder79.ecotones.mixin;

import net.minecraft.client.color.world.BiomeColors;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BiomeColors.class)
public class MixinBiomeColors {

//    @SuppressWarnings("UnresolvedMixinReference") // Synthetic lambda method
//    @Inject(method = "method_23791", at = @At("HEAD"), cancellable = true)
//    private static void handleFoliageColors(Biome biome, double x, double z, CallbackInfoReturnable<Integer> cir) {
//        if (biome instanceof FoliageColorResolver) {
//            cir.setReturnValue(((FoliageColorResolver)biome).getFoliageColorAt(x, z));
//        }
//    }
}
