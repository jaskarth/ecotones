package supercoder79.ecotones.mixin;

import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import supercoder79.ecotones.generation.EcotonesChunkGenerator;
import supercoder79.ecotones.util.ImprovedChunkRandom;

@Mixin(ChunkGenerator.class)
public class MixinChunkGenerator {

    @Redirect(method = "setStructureStarts", at = @At(value = "NEW", target = "net/minecraft/world/gen/ChunkRandom"))
    public ChunkRandom provideImproved() {
        if (((ChunkGenerator)(Object) this) instanceof EcotonesChunkGenerator) {
            return new ImprovedChunkRandom();
        } else {
            return new ChunkRandom();
        }
    }
}
