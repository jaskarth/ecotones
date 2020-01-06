package supercoder79.ecotones.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.EntityContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class EcotonesGrassBlock extends Block implements Fertilizable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    protected EcotonesGrassBlock(Block.Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        return SHAPE;
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        TallPlantBlock tallPlantBlock = (TallPlantBlock) (this == Blocks.FERN ? Blocks.LARGE_FERN : Blocks.TALL_GRASS);
        if (tallPlantBlock.getDefaultState().canPlaceAt(world, pos) && world.isAir(pos.up())) {
            tallPlantBlock.placeAt(world, pos, 2);
        }

    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.down());
        return (downState == Blocks.GRASS_BLOCK.getDefaultState() || downState == Blocks.DIRT.getDefaultState() || downState == Blocks.COARSE_DIRT.getDefaultState());
    }

    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XYZ;
    }
}
