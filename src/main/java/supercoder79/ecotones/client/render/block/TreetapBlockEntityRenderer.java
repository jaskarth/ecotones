package supercoder79.ecotones.client.render.block;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import org.joml.Matrix4f;
import supercoder79.ecotones.blocks.entity.TreetapBlockEntity;
import supercoder79.ecotones.client.render.EcotonesRenderLayers;

public class TreetapBlockEntityRenderer implements BlockEntityRenderer<TreetapBlockEntity> {
    private static final float FLOOR_OFFSET = 1 / 16.0f;
    private static final float MAX_HEIGHT = 7 / 16.0f;
    private static final float LIQUID_SIZE = 6 / 16.0f;
    public TreetapBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

    }

    @Override
    public void render(TreetapBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        int sapAmount = entity.getSapAmount();
        if (sapAmount > 0) {
            float height = FLOOR_OFFSET + ((sapAmount / 8000.f) * (MAX_HEIGHT - FLOOR_OFFSET));

            VertexConsumer consumer = vertexConsumers.getBuffer(EcotonesRenderLayers.SYRUP_TEX_LAYER);

            Matrix4f model = matrices.peek().getPositionMatrix();
            float xStart = xStart(entity.getDirection());
            float zStart = zStart(entity.getDirection());

            consumer.vertex(model, xStart, height, zStart + LIQUID_SIZE).color(247, 184, 49, 255).texture(0.0F, LIQUID_SIZE).next();
            consumer.vertex(model, xStart + LIQUID_SIZE, height, zStart + LIQUID_SIZE).color(247, 184, 49, 255).texture(LIQUID_SIZE, LIQUID_SIZE).next();
            consumer.vertex(model, xStart + LIQUID_SIZE, height, zStart).color(247, 184, 49, 255).texture(LIQUID_SIZE, 0.0F).next();
            consumer.vertex(model, xStart, height, zStart).color(247, 184, 49, 255).texture(0.0F, 0.0F).next();
        }

        matrices.pop();
    }

    private static float xStart(Direction direction) {
        switch (direction) {
            case NORTH:
                return 5 / 16.f;
            case EAST:
                return 7 / 16.f;
            case SOUTH:
                return 5 / 16.f;
            case WEST:
                return 3 / 16.f;
            default:
                throw new RuntimeException("We've got a sticky situation here!");
        }
    }

    private static float zStart(Direction direction) {
        switch (direction) {
            case NORTH:
                return 3 / 16.f;
            case EAST:
                return 5 / 16.f;
            case SOUTH:
                return 7 / 16.f;
            case WEST:
                return 5 / 16.f;
            default:
                throw new RuntimeException("We've got a sticky situation here!");
        }
    }
}
