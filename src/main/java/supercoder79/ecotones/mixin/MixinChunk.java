package supercoder79.ecotones.mixin;

import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import supercoder79.ecotones.world.storage.ChunkDataStorage;
import supercoder79.ecotones.world.storage.ChunkStorageView;

@Mixin(Chunk.class)
public class MixinChunk implements ChunkStorageView {
    private ChunkDataStorage ecotonesDataStorage = new ChunkDataStorage();

    @Override
    public ChunkDataStorage getEcotonesStorageContainer() {
        return ecotonesDataStorage;
    }

    @Override
    public void setEcotonesStorageContainer(ChunkDataStorage data) {
        ecotonesDataStorage = data;
    }
}
