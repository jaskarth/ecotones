package com.jaskarth.ecotones.util.deco;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

public class DefaultBlockDecoration implements BlockDecoration {
    private final BlockState state;

    public DefaultBlockDecoration(BlockState state) {
        this.state = state;
    }

    @Override
    public void generate(StructureWorldAccess world, BlockPos pos, Direction direction) {
        world.setBlockState(pos, this.state, 3);
    }
}
