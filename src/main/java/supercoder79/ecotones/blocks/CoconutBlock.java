package supercoder79.ecotones.blocks;

import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class CoconutBlock extends HorizontalFacingBlock {
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(9, 5, 5, 15, 12, 11);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(1, 5, 5, 7, 12, 11);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(5, 5, 1, 11, 12, 7);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(5, 5, 9, 11, 12, 15);

    public CoconutBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.offset((Direction)state.get(FACING)));
        return blockState.isIn(BlockTags.JUNGLE_LOGS);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == state.get(FACING) && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
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
