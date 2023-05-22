package supercoder79.ecotones.mixin;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.UserCache;
import net.minecraft.util.math.random.RandomSequencesState;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.world.gen.EcotonesBiomeSource;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.net.Proxy;
import java.util.List;
import java.util.concurrent.Executor;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
    @Shadow public abstract ServerWorld getOverworld();

    // From fabric api- event doesn't seem to work??
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;loadFavicon()Ljava/util/Optional;", ordinal = 0), method = "runServer")
    private void onServerStart(CallbackInfo info) {
        Ecotones.isServerEcotones = getOverworld().getChunkManager().getChunkGenerator() instanceof EcotonesChunkGenerator;
        // FIXME: check to see this actually works
    }

    @Redirect(method = "createWorlds", at = @At(value = "NEW", target = "net/minecraft/server/world/ServerWorld"))
    private ServerWorld redirectCtor(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List spawners, boolean shouldTickTime, RandomSequencesState randomSequencesState) {
        if (dimensionOptions.chunkGenerator() instanceof EcotonesChunkGenerator) {
            long l = server.getSaveProperties().getGeneratorOptions().getSeed();
            LevelProperties props = (LevelProperties) server.getSaveProperties();
            DynamicRegistryManager.Immutable registryManager = server.getRegistryManager();

            ChunkGenerator chunkGenerator = new EcotonesChunkGenerator(
                    new EcotonesBiomeSource(registryManager.get(RegistryKeys.BIOME), l, true), l);
            ((DimensionOptionsAccessor)dimensionOptions).setChunkGenerator(chunkGenerator);
        }

        return new ServerWorld(server, workerExecutor, session, properties, worldKey, dimensionOptions, worldGenerationProgressListener, debugWorld, seed, spawners, shouldTickTime, randomSequencesState);
    }
}
