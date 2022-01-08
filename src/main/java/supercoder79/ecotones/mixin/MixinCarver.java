package supercoder79.ecotones.mixin;

import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.CarvingMask;
import net.minecraft.world.gen.chunk.AquiferSampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supercoder79.ecotones.world.carver.EcotonesCarverContext;

import java.util.function.Function;

@Mixin(Carver.class)
public class MixinCarver<C extends CarverConfig> {
    @Inject(method = "carveRegion", at = @At("HEAD"), cancellable = true)
    private void ecotonesOldCarveRegion(CarverContext context, C config, Chunk chunk, Function<BlockPos, Biome> posToBiome, AquiferSampler aquiferSampler, double d, double e, double f, double g, double h, CarvingMask mask, Carver.SkipPredicate skipPredicate, CallbackInfoReturnable<Boolean> cir) {
        if (context instanceof EcotonesCarverContext) {
            ChunkPos chunkPos = chunk.getPos();
            int x = chunkPos.getStartX();
            int z = chunkPos.getStartZ();
            int m = Math.max(MathHelper.floor(d - g) - x - 1, 0);
            int n = Math.min(MathHelper.floor(d + g) - x, 15);
            int o = Math.max(MathHelper.floor(e - h) - 1, context.getMinY() + 1);
            int p = Math.min(MathHelper.floor(e + h) + 1, context.getMinY() + context.getHeight() - 8);
            int q = Math.max(MathHelper.floor(f - g) - z - 1, 0);
            int r = Math.min(MathHelper.floor(f + g) - z, 15);

            if (this.isRegionUncarvable(chunk, m, n, o, p, q, r)) {
                cir.setReturnValue(false);
                return;
            }
        }
    }

    protected boolean isRegionUncarvable(Chunk chunk, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        ChunkPos chunkPos = chunk.getPos();
        int i = chunkPos.getStartX();
        int j = chunkPos.getStartZ();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int k = minX; k <= maxX; ++k) {
            for(int l = minZ; l <= maxZ; ++l) {
                for(int m = minY - 1; m <= maxY + 1; ++m) {
                    mutable.set(i + k, m, j + l);
                    if (chunk.getFluidState(mutable).getFluid() == Fluids.WATER) {
                        return true;
                    }

                    if (m != maxY + 1 && !isOnBoundary(k, l, minX, maxX, minZ, maxZ)) {
                        m = maxY;
                    }
                }
            }
        }

        return false;
    }

    private static boolean isOnBoundary(int x, int z, int minX, int maxX, int minZ, int maxZ) {
        return x == minX || x == maxX || z == minZ || z == maxZ;
    }
}
