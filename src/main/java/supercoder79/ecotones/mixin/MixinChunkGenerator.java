package supercoder79.ecotones.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.World;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.world.gen.NetherGen;

@Mixin(ChunkGenerator.class)
public class MixinChunkGenerator {
    @Inject(method = "generateFeatures", at = @At("TAIL"))
    private void injectEcotonesNetherGen(ChunkRegion region, StructureAccessor accessor, CallbackInfo ci) {
        // Generate ecotones if we're in an ecotones world
        if (Ecotones.isServerEcotones && region.toServerWorld().getRegistryKey() == World.NETHER) {
            ChunkPos chunkPos = region.getCenterPos();
            int x = chunkPos.getStartX();
            int z = chunkPos.getStartZ();
            BlockPos blockPos = new BlockPos(x, region.getBottomY(), z);
            NetherGen.generate(region, accessor, (ChunkGenerator) (Object) this, blockPos);
        }
    }
}
