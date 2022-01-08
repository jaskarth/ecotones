package supercoder79.ecotones.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supercoder79.ecotones.client.ClientSidedServerData;

//@Mixin(SkyProperties.Overworld.class)
public class MixinSkyProperties {
//    @Inject(method = "adjustFogColor", at = @At("RETURN"), cancellable = true)
//    private void useEcotonesFancyFogColor(Vec3d color, float sunHeight, CallbackInfoReturnable<Vec3d> cir) {
//        if (ClientSidedServerData.isInEcotonesWorld) {
//            MinecraftClient client = MinecraftClient.getInstance();
//            if (sunHeight <= 0.0) {
//                double heightMultiplier = MathHelper.clamp(client.player.getY() - 128, 0, 4) / 4;
//                Vec3d adjustedColor = cir.getReturnValue();
//                cir.setReturnValue(adjustedColor.add(new Vec3d(0.05, 0.025, 0.1).multiply(heightMultiplier)));
//            }
//        }
//    }
}
