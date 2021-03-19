package supercoder79.ecotones.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supercoder79.ecotones.client.ClientSidedServerData;
import supercoder79.ecotones.client.CloudHandler;

@Mixin(ClientWorld.class)
public class MixinClientWorld {
    @Inject(method = "method_23777", at = @At("RETURN"), cancellable = true)
    private void addEcotonesFancySkyColor(Vec3d vec3d, float f, CallbackInfoReturnable<Vec3d> cir) {
        if (ClientSidedServerData.isInEcotonesWorld) {
            Vec3d color = cir.getReturnValue();

            double red = color.x;
            double green = color.y;
            double blue = color.z;

            double cloudCount = CloudHandler.getCloudTexCount() / 65536.0; // 256^2
            cloudCount -= 0.75;
            cloudCount *= 4;

            // TODO: lerp based on time
            red = MathHelper.clampedLerp(red, 0.9, cloudCount);
            green = MathHelper.clampedLerp(green, 0.9, cloudCount);
            blue = MathHelper.clampedLerp(blue, 0.9, cloudCount);

            cir.setReturnValue(new Vec3d(red, green, blue));
        }
    }
}
