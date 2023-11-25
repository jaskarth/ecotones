package com.jaskarth.ecotones.world.blocks.entity;

import com.jaskarth.ecotones.world.blocks.entity.types.SmokeEmitter;
import com.jaskarth.ecotones.world.worldgen.features.FeatureHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ChimneyBlockEntity extends BlockEntity {
    public ChimneyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ChimneyBlockEntity blockEntity) {
        // TODO: how should this iterate? should the top block look for all attached smoke emitters?

        int nearby = 0;
        for (Direction dir : FeatureHelper.HORIZONTAL) {
            if (world.getBlockEntity(pos) instanceof SmokeEmitter emitter) {
                if (emitter.smokeEnabled() && emitter.smokeFacing().equals(dir.getOpposite())) {
                    nearby++;
                }
            }
        }

        if (nearby > 0) {
            
        }
    }
}
