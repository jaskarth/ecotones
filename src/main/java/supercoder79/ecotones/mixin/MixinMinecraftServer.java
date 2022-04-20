package supercoder79.ecotones.mixin;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.UserCache;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.net.Proxy;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
    @Shadow public abstract ServerWorld getOverworld();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onServerBootstrap(Thread serverThread, LevelStorage.Session session, ResourcePackManager dataPackManager, SaveLoader saveLoader, Proxy proxy, DataFixer dataFixer, MinecraftSessionService sessionService, GameProfileRepository gameProfileRepo, UserCache userCache, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci) {

    }

    // From fabric api- event doesn't seem to work??
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;setFavicon(Lnet/minecraft/server/ServerMetadata;)V", ordinal = 0), method = "runServer")
    private void onServerStart(CallbackInfo info) {
        Ecotones.isServerEcotones = getOverworld().getChunkManager().getChunkGenerator() instanceof EcotonesChunkGenerator;
    }
}
