package supercoder79.ecotones.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class EcotonesSandyPlantBlock extends EcotonesPlantBlock {
    protected EcotonesSandyPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.down());
        return (downState.isOf(Blocks.SAND) || downState.isOf(Blocks.RED_SAND) || downState.isOf(EcotonesBlocks.RED_ROCK));
    }
}
