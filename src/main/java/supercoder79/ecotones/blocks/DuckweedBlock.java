package supercoder79.ecotones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class DuckweedBlock extends EcotonesLeafPileBlock {
    public DuckweedBlock(Settings settings) {
        super(settings);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        FluidState fluid = world.getFluidState(pos);
        FluidState up = world.getFluidState(pos.up());
        return (fluid.getFluid() == Fluids.WATER) && up.getFluid() == Fluids.EMPTY;
    }
}
