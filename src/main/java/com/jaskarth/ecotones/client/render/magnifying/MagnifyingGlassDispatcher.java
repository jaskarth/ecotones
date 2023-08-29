package com.jaskarth.ecotones.client.render.magnifying;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import com.jaskarth.ecotones.world.items.EcotonesItems;

public final class MagnifyingGlassDispatcher {
    public static void dispatch(ClientPlayerEntity player) {
        if (player.getMainHandStack().isOf(EcotonesItems.MAGNIFYING_GLASS)) {

            // TODO: implement locking
            HitResult res = player.raycast(20.0D, 0.0F, false);

            if (res.getType() == HitResult.Type.BLOCK) {
                BlockHitResult bres = ((BlockHitResult) res);

                BlockPos pos = bres.getBlockPos();
                MagnifyingGlassRenderer renderer = EcotonesMagnifyingGlassRenderers.rendererFor(player.clientWorld.getBlockState(pos).getBlock());

                if (renderer != null) {
                    renderer.render(pos, player);
                }
            }
        }
    }
}
