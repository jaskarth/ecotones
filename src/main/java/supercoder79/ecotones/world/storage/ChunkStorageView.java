package supercoder79.ecotones.world.storage;

import net.minecraft.world.chunk.Chunk;

public interface ChunkStorageView {
    ChunkDataStorage getEcotonesStorageContainer();
    void setEcotonesStorageContainer(ChunkDataStorage data);

    static ChunkDataStorage getStorage(Chunk chunk) {
        return ((ChunkStorageView)chunk).getEcotonesStorageContainer();
    }
}
