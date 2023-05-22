package supercoder79.ecotones.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.client.ClientSidedServerData;
import supercoder79.ecotones.client.render.magnifying.MagnifyingGlassDispatcher;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class MixinClientWorld extends World {
    @Shadow @Final private MinecraftClient client;

    protected MixinClientWorld(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess, maxChainedNeighborUpdates);
    }

    @Inject(method = "getSkyColor", at = @At("RETURN"), cancellable = true)
    private void addEcotonesFancySkyColor(Vec3d vec3d, float f, CallbackInfoReturnable<Vec3d> cir) {
        if (ClientSidedServerData.isInEcotonesWorld && Ecotones.CONFIG.client.useEcotonesSky) {
            Vec3d color = cir.getReturnValue();

            double red = color.x;
            double green = color.y;
            double blue = color.z;

            // TODO: implement this
//            double cloudCount = CloudHandler.getCloudTexCount() / 65536.0; // 256^2
//            cloudCount -= 0.75;
//            cloudCount *= 4;

            long time = this.getLunarTime();
            time += 2000;
            time %= 24000;
            double delta = time / 24000.0;
            double lerp = MathHelper.clampedMap(delta, 0.5, 0.55, 0, 1);
            lerp *= MathHelper.clampedMap(delta, 0.95, 1, 1, 0);
            if (delta > 0.5) {
                red += 0.05 * lerp;
                green += 0.025 * lerp;
                blue += 0.1 * lerp;
            } else {
//                red = MathHelper.clampedLerp(red, 0.9, cloudCount);
//                green = MathHelper.clampedLerp(green, 0.9, cloudCount);
//                blue = MathHelper.clampedLerp(blue, 0.9, cloudCount);
            }

            cir.setReturnValue(new Vec3d(red, green, blue));
        }
    }

    @Inject(method = "doRandomBlockDisplayTicks", at = @At("HEAD"))
    private void extraParticleRendering(int centerX, int centerY, int centerZ, CallbackInfo ci) {
        MagnifyingGlassDispatcher.dispatch(this.client.player);
    }
}
