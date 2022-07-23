package supercoder79.ecotones.mixin;

import com.mojang.datafixers.DataFixer;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerEntityManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.StructureLocator;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.ecotones.world.gen.EcotonesBiomeSource;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerWorld.class)
public abstract class MixinServerWorld {
    @Mutable
    @Shadow @Final private ServerChunkManager chunkManager;

    @Mutable
    @Shadow @Final private StructureLocator structureLocator;

    @Shadow @Final private ServerEntityManager<Entity> entityManager;

    @Shadow public abstract DynamicRegistryManager getRegistryManager();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void buildEcotonesGenerator(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List spawners, boolean shouldTickTime, CallbackInfo ci) {
        if (dimensionOptions.getChunkGenerator() instanceof EcotonesChunkGenerator) {
            // Found dummy ecotones chunk gen, replace with live ver

            long l = server.getSaveProperties().getGeneratorOptions().getSeed();

            DataFixer dataFixer = server.getDataFixer();
            boolean bl = server.syncChunkWrites();

            // TODO: structure set correct?
            DynamicRegistryManager.Immutable registryManager = server.getRegistryManager();
            ChunkGenerator chunkGenerator = new EcotonesChunkGenerator(BuiltinRegistries.STRUCTURE_SET,
                    new EcotonesBiomeSource(registryManager.get(Registry.BIOME_KEY), l, true), l);

            this.chunkManager = new ServerChunkManager(
                    (ServerWorld)(Object) this,
                    session,
                    dataFixer,
                    server.getStructureTemplateManager(),
                    workerExecutor,
                    chunkGenerator,
                    server.getPlayerManager().getViewDistance(),
                    server.getPlayerManager().getSimulationDistance(),
                    bl,
                    worldGenerationProgressListener,
                    this.entityManager::updateTrackingStatus,
                    () -> server.getOverworld().getPersistentStateManager()
            );
            chunkGenerator.computeStructurePlacementsIfNeeded(this.chunkManager.getNoiseConfig());

            this.structureLocator = new StructureLocator(
                    this.chunkManager.getChunkIoWorker(),
                    this.getRegistryManager(),
                    server.getStructureTemplateManager(),
                    worldKey,
                    chunkGenerator,
                    this.chunkManager.getNoiseConfig(),
                    (ServerWorld)(Object) this,
                    chunkGenerator.getBiomeSource(),
                    l,
                    dataFixer
            );
        }
    }
}
