package supercoder79.ecotones.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.world.biome.climatic.SparseTundraBiome;

import java.util.Random;

@Mixin(Biome.class)
public abstract class MixinBiome {
    private static final ChunkRandom RANDOM = new ChunkRandom(0);
    @Shadow public abstract float getTemperature();

    @Inject(method = "computeTemperature", at = @At("HEAD"), cancellable = true)
    private void injectEcotonesTemperatures(BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (BiomeRegistries.key((Biome) (Object)this) == SparseTundraBiome.KEY) {
            float temp = this.getTemperature();


            RANDOM.setDeepslateSeed(0, pos.getX(), 0, pos.getZ());

            if (RANDOM.nextInt(16) == 0) {
                cir.setReturnValue(0.0f);
            } else {
                cir.setReturnValue(temp);
            }
        }
    }
}
