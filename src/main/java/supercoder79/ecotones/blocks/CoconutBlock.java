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
    private static final VoxelShape AGE_TO_EAST_SHAPE = Block.createCuboidShape(9.0D, 5.0D, 5.0D, 15.0D, 12.0D, 11.0D);
    private static final VoxelShape AGE_TO_WEST_SHAPE = Block.createCuboidShape(1.0D, 5.0D, 5.0D, 7.0D, 12.0D, 11.0D);
    private static final VoxelShape AGE_TO_NORTH_SHAPE = Block.createCuboidShape(5.0D, 5.0D, 1.0D, 11.0D, 12.0D, 7.0D);
    private static final VoxelShape AGE_TO_SOUTH_SHAPE = Block.createCuboidShape(5.0D, 5.0D, 9.0D, 11.0D, 12.0D, 15.0D);

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
                return AGE_TO_SOUTH_SHAPE;
            case NORTH:
            default:
                return AGE_TO_NORTH_SHAPE;
            case WEST:
                return AGE_TO_WEST_SHAPE;
            case EAST:
                return AGE_TO_EAST_SHAPE;
        }
    }
}
