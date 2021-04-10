package supercoder79.ecotones.client.render.block;

import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import supercoder79.ecotones.blocks.entity.SapDistilleryBlockEntity;
import supercoder79.ecotones.client.tex.SyrupTextureGenerator;

public class SapDistilleryBlockEntityRenderer implements BlockEntityRenderer<SapDistilleryBlockEntity> {
    private static final RenderLayer LAYER = RenderLayer.of("sap_distillery", VertexFormats.POSITION_COLOR_TEXTURE,
    VertexFormat.DrawMode.QUADS, 256, false, false,
            RenderLayer.MultiPhaseParameters.builder()
                    .texture(new RenderPhase.Texture(SyrupTextureGenerator.ID, false, false))
            .build(false));

    private static final float FLOOR_OFFSET = 1 / 16.0f;
    private static final float MAX_HEIGHT = 1 / 2.0f;

    public SapDistilleryBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

    }

    @Override
    public void render(SapDistilleryBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        int syrupAmt = entity.getSyrupAmount();
        if (syrupAmt > 0) {
            float height = FLOOR_OFFSET + ((syrupAmt / 5000.f) * (MAX_HEIGHT - FLOOR_OFFSET));

            VertexConsumer consumer = vertexConsumers.getBuffer(LAYER);

            Matrix4f model = matrices.peek().getModel();
            consumer.vertex(model, 0.1875F, height, 0.8125F).color(229, 134, 50, 255).texture(0.0F, 1.0F).next();
            consumer.vertex(model, 0.8125F, height, 0.8125F).color(229, 134, 50, 255).texture(1.0F, 1.0F).next();
            consumer.vertex(model, 0.8125F, height, 0.1875F).color(229, 134, 50, 255).texture(1.0F, 0.0F).next();
            consumer.vertex(model, 0.1875F, height, 0.1875F).color(229, 134, 50, 255).texture(0.0F, 0.0F).next();
        }

        matrices.pop();
    }
}
