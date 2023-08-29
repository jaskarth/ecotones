package com.jaskarth.ecotones.mixin;

import net.minecraft.util.Util;
import net.minecraft.world.ChunkRegion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Supplier;

@Mixin(ChunkRegion.class)
public class MixinChunkRegion {
    @Shadow @Nullable private Supplier<String> currentlyGeneratingStructureName;

    @Redirect(method = "isValidForSetBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;error(Ljava/lang/String;)V"))
    private void filterEcotones(String message) {
        if (!(this.currentlyGeneratingStructureName != null && this.currentlyGeneratingStructureName.get().contains("ecotones"))) {
            Util.error(message);
        }
    }
}
