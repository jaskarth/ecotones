package supercoder79.ecotones.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supercoder79.ecotones.client.ClientSidedServerData;
import supercoder79.ecotones.client.FogHandler;
import supercoder79.ecotones.client.debug.EcotonesClientDebug;

import java.text.DecimalFormat;
import java.util.List;

@Mixin(DebugHud.class)
public class MixinDebugHud {
    private static final Identifier CLOUDS = new Identifier("textures/environment/clouds.png");

    @Unique
    private static final DecimalFormat FORMAT = new DecimalFormat("#.####");
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "getLeftText", at = @At("TAIL"))
    private void addFogData(CallbackInfoReturnable<List<String>> cir) {
        if (ClientSidedServerData.isInEcotonesWorld) {
            long time = this.client.world.getTime();
            double noise = FogHandler.noiseFor(time);
            double offset = FogHandler.offsetFor(time);
            List<String> list = cir.getReturnValue();

            list.add("FN: " + FORMAT.format(noise));
            list.add("FO: " + FORMAT.format(offset));
            list.add("FM: " + FORMAT.format((FogHandler.multiplierFor(noise, offset))));
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderCloudTex(MatrixStack matrices, CallbackInfo ci) {
        if (ClientSidedServerData.isInEcotonesWorld && EcotonesClientDebug.RENDER_CLOUDS_TEX && !this.client.options.debugProfilerEnabled) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(CLOUDS);
            DrawableHelper.drawTexture(matrices, this.client.getWindow().getScaledWidth() - 128, this.client.getWindow().getScaledHeight() - 128, 0.0F, 0.0F, 128, 128, 128, 128);
        }
    }
}
