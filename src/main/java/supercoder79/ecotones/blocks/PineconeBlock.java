package supercoder79.ecotones.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldView;

public class PineconeBlock extends Block {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 7.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public PineconeBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        Vec3d vec3d = state.getOffsetPos(view, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }

    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState upState = world.getBlockState(pos.up());
        return upState.getBlock() == Blocks.SPRUCE_LEAVES.getDefaultState().getBlock();
    }
}
