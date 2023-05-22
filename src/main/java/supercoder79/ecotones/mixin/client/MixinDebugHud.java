package supercoder79.ecotones.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.world.ServerWorld;
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
import supercoder79.ecotones.client.debug.EcotonesClientDebug;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.text.DecimalFormat;
import java.util.List;

@Mixin(DebugHud.class)
public class MixinDebugHud {
    private static final Identifier CLOUDS = new Identifier("textures/environment/clouds.png");

    @Unique
    private static final DecimalFormat FORMAT = new DecimalFormat("#.###");
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "getLeftText", at = @At("TAIL"))
    private void addFogData(CallbackInfoReturnable<List<String>> cir) {
        if (ClientSidedServerData.isInEcotonesWorld) {
//            long time = this.client.world.getLunarTime();
//            double noise = FogHandler.noiseFor(time);
//            double offset = FogHandler.offsetFor(time);
//            List<String> list = cir.getReturnValue();

//            list.add("FN: " + FORMAT.format(noise));
//            list.add("FO: " + FORMAT.format(offset));
//            list.add("FM: " + FORMAT.format((FogHandler.multiplierFor(noise, offset))));
        }

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            IntegratedServer server = client.getServer();
            if (server != null) {
                ServerWorld world = server.getWorld(this.client.world.getRegistryKey());

                if (world.getChunkManager().getChunkGenerator() instanceof EcotonesChunkGenerator generator) {
                    double x = this.client.player.getX();
                    double z = this.client.player.getZ();

                    List<String> list = cir.getReturnValue();
                    list.add("Drainage: " + FORMAT.format(generator.getSoilDrainageNoise().sample(x, z)));
                    list.add("Rockiness: " + FORMAT.format(generator.getSoilRockinessNoise().sample(x, z)));
                    list.add("Quality: " + FORMAT.format(generator.getSoilQualityAt(x, z)));
                    list.add("pH: " + FORMAT.format(generator.getSoilPhAt(x, z)));
                }
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void renderCloudTex(DrawContext context, CallbackInfo ci) {
        if (ClientSidedServerData.isInEcotonesWorld && EcotonesClientDebug.RENDER_CLOUDS_TEX && !this.client.options.debugProfilerEnabled) {
//            RenderSystem.setShader(GameRenderer::getPositionTexShader);
//            RenderSystem.setShaderTexture(0, CLOUDS);
            context.drawTexture(CLOUDS, this.client.getWindow().getScaledWidth() - 128, this.client.getWindow().getScaledHeight() - 128, 0.0F, 0.0F, 128, 128, 128, 128);
//            DrawableHelper.drawTexture(matrices, );
        }
    }
}
