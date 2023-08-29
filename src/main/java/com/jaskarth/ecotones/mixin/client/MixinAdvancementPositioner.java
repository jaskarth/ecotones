package com.jaskarth.ecotones.mixin.client;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementPositioner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.jaskarth.ecotones.client.gui.advancement.EcotonesAdvancementPositioning;

@Mixin(AdvancementPositioner.class)
public class MixinAdvancementPositioner {
    @Inject(method = "arrangeForTree", at = @At("HEAD"), cancellable = true)
    private static void ecotonesArrange(Advancement root, CallbackInfo ci) {
        if (root.getId().getNamespace().equals("ecotones")) {
            EcotonesAdvancementPositioning.position(root);
            ci.cancel();
        }
    }
}
