package com.jaskarth.ecotones.client.render.block;

import com.jaskarth.ecotones.client.render.EcotonesRenderLayers;
import com.jaskarth.ecotones.client.render.QuadEmitter;
import com.jaskarth.ecotones.world.blocks.CooktopBlock;
import com.jaskarth.ecotones.world.blocks.entity.CooktopBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;

public class CooktopBlockEntityRenderer implements BlockEntityRenderer<CooktopBlockEntity> {
    public CooktopBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

    }

    @Override
    public void render(CooktopBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider consp, int light, int overlay) {
        matrices.push();

        VertexConsumer cons = consp.getBuffer(EcotonesRenderLayers.COOKTOP_FACE);

        int l = 13 << 4;
        MatrixStack.Entry entry = matrices.peek();
        BlockState state = entity.getCachedState();
        if (state.contains(CooktopBlock.FACING)) {
            Direction facing = state.get(CooktopBlock.FACING);
            if (facing == Direction.NORTH) {
                QuadEmitter.buildNorthFacing(cons, entry, 0, 1, 0, 1, 0, 255, 255, 255, 255, l);
            } else if (facing == Direction.SOUTH) {
                QuadEmitter.buildNorthFacing(cons, entry, 0, 1, 0, 1, 1, 255, 255, 255, 255, l);
            } else if (facing == Direction.EAST) {
                QuadEmitter.buildEastFacing(cons, entry, 0, 1, 0, 1, 1, 255, 255, 255, 255, l);
            } else if (facing == Direction.WEST) {
                QuadEmitter.buildEastFacing(cons, entry, 0, 1, 0, 1, 0, 255, 255, 255, 255, l);
            }
        }

        matrices.pop();
    }
}
