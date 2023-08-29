package com.jaskarth.ecotones.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.jaskarth.ecotones.world.storage.ChunkDataStorage;
import com.jaskarth.ecotones.world.storage.ChunkStorageView;

@Mixin(ChunkSerializer.class)
public class MixinChunkSerializer {
    @Inject(method = "deserialize", at = @At("TAIL"), cancellable = true)
    private static void ecotonesDeserialize(ServerWorld world, PointOfInterestStorage poiStorage, ChunkPos chunkPos, NbtCompound nbt, CallbackInfoReturnable<ProtoChunk> cir) {
        ProtoChunk chunk = cir.getReturnValue();

        NbtCompound storage = nbt.getCompound("EcotonesDataStorage");

        ((ChunkStorageView)chunk).setEcotonesStorageContainer(ChunkDataStorage.deserialize(storage));
    }

    @Inject(method = "serialize", at = @At("TAIL"), cancellable = true)
    private static void ecotonesSerialize(ServerWorld world, Chunk chunk, CallbackInfoReturnable<NbtCompound> cir) {
        NbtCompound nbt = cir.getReturnValue();

        NbtCompound storage = new NbtCompound();
        ChunkStorageView.getStorage(chunk).serialize(storage);
        nbt.put("EcotonesDataStorage", storage);
    }
}
