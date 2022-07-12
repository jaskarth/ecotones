package supercoder79.ecotones.client.render.block;

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import supercoder79.ecotones.blocks.entity.EcotonesBlockEntities;

public final class EcotonesBlockEntityRenderers {
    public static void init() {
        BlockEntityRendererRegistry.register(EcotonesBlockEntities.SAP_DISTILLERY, SapDistilleryBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(EcotonesBlockEntities.TREETAP, TreetapBlockEntityRenderer::new);
    }
}
