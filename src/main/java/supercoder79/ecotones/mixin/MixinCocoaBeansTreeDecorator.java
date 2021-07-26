package supercoder79.ecotones.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.treedecorator.CocoaBeansTreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

@Mixin(CocoaBeansTreeDecorator.class)
public class MixinCocoaBeansTreeDecorator {
    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void fixCrash(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, CallbackInfo ci) {
        if (logPositions.size() == 0) {
            ci.cancel(); // Not sure how this happens but it does
        }
    }
}
