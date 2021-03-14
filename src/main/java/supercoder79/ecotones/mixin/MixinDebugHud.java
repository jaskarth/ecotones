package supercoder79.ecotones.mixin;

import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import supercoder79.ecotones.client.ClientSidedServerData;
import supercoder79.ecotones.client.FogHandler;

import java.text.DecimalFormat;
import java.util.List;

@Mixin(DebugHud.class)
public class MixinDebugHud {
    @Unique
    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");
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
}
