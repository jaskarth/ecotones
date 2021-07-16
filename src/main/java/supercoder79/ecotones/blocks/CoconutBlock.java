package supercoder79.ecotones.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class CoconutBlock extends HorizontalFacingBlock {
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(9, 5, 5, 15, 12, 11);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(1, 5, 5, 7, 12, 11);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(5, 5, 1, 11, 12, 7);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(5, 5, 9, 11, 12, 15);

    public CoconutBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
        switch(state.get(FACING)) {
            case SOUTH:
                return SOUTH_SHAPE;
            case NORTH:
            default:
                return NORTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            case EAST:
                return EAST_SHAPE;
        }
    }
}
