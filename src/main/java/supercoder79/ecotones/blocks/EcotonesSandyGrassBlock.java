package supercoder79.ecotones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class EcotonesSandyGrassBlock extends EcotonesGrassBlock {
    protected EcotonesSandyGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.down());
        return (downState == Blocks.SAND.getDefaultState() || downState == Blocks.RED_SAND.getDefaultState());
    }
}
