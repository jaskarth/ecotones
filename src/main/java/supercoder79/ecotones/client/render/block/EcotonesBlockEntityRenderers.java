package supercoder79.ecotones.client.render.block;

import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import supercoder79.ecotones.blocks.entity.EcotonesBlockEntities;

public final class EcotonesBlockEntityRenderers {
    public static void init() {
        BlockEntityRendererRegistry.INSTANCE.register(EcotonesBlockEntities.SAP_DISTILLERY, SapDistilleryBlockEntityRenderer::new);
    }
}
