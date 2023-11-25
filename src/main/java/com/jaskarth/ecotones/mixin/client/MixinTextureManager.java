package com.jaskarth.ecotones.mixin.client;

import com.jaskarth.ecotones.client.tex.CooktopTextureGenerator;
import com.jaskarth.ecotones.client.tex.EcotonesDynamicTextures;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextureManager.class)
public class MixinTextureManager {
    @Inject(method = "loadTexture", at = @At("HEAD"), cancellable = true)
    private void ecotones$loadDynamicTextures(Identifier id, AbstractTexture texture, CallbackInfoReturnable<AbstractTexture> cir) {
//        System.out.println(id);
//        if (id.toString().startsWith("ecotones:generated/")) {
//            if (id.getPath().equals("generated/brick_cooktop_front/on")) {
//                cir.setReturnValue(CooktopTextureGenerator.register((TextureManager) (Object) this));
//            }
//        }
    }

    @Inject(method = "method_18167", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;send(Ljava/lang/Runnable;)V", shift = At.Shift.BEFORE))
    private void ecotones$registerDynamicTextures(CallbackInfo ci) {
        EcotonesDynamicTextures.init(MinecraftClient.getInstance().getTextureManager());
    }
}
