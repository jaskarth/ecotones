package supercoder79.ecotones.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.ecotones.util.AiLog;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "close", at = @At("HEAD"))
    private void onShutDown(CallbackInfo ci) {
        AiLog.close();
    }
}
