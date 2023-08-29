package com.jaskarth.ecotones.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.jaskarth.ecotones.client.ClientRegistrySyncState;
import com.jaskarth.ecotones.util.AiLog;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "close", at = @At("HEAD"))
    private void onShutDown(CallbackInfo ci) {
        AiLog.close();
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"))
    private void onDisconect(Screen screen, CallbackInfo ci) {
        ClientRegistrySyncState.state = ClientRegistrySyncState.State.NONE;
    }
}
