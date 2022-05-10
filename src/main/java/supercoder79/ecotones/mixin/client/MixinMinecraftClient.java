package supercoder79.ecotones.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.ecotones.client.ClientRegistrySyncState;
import supercoder79.ecotones.util.AiLog;

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
