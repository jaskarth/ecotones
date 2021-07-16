package supercoder79.ecotones.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.ecotones.util.DynamicEatTimeItem;

@Mixin(HeldItemRenderer.class)
public class MixinHeldItemRenderer {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "applyEatOrDrinkTransformation", at = @At("HEAD"), cancellable = true)
    private void eatItemBetter(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack, CallbackInfo ci) {
        if (stack.getItem() instanceof DynamicEatTimeItem) {
            float f = (float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0F;
            float g = f / (float)stack.getMaxUseTime();
            float i;
            double fractional = ((stack.getMaxUseTime() - 6.4) / stack.getMaxUseTime());
            double exponent = Math.log(0.0025) / Math.log(fractional);

            if (g < fractional) {
                i = MathHelper.abs(MathHelper.cos(f / 4.0F * 3.1415927F) * 0.1F);
                matrices.translate(0.0D, (double)i, 0.0D);
            }

            i = 1.0F - (float)Math.pow((double)g, exponent);
            int j = arm == Arm.RIGHT ? 1 : -1;
            matrices.translate((double)(i * 0.6F * (float)j), (double)(i * -0.5F), (double)(i * 0.0F));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)j * i * 90.0F));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(i * 10.0F));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float)j * i * 30.0F));

            ci.cancel();
        }
    }
}
