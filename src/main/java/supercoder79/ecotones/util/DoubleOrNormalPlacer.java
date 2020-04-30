package supercoder79.ecotones.util;

import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.placer.BlockPlacer;
import net.minecraft.world.gen.placer.BlockPlacerType;

import java.util.Random;

public class DoubleOrNormalPlacer extends BlockPlacer {
    public DoubleOrNormalPlacer() {
        super(BlockPlacerType.SIMPLE_BLOCK_PLACER);
    }

    @Override
    public void method_23403(IWorld world, BlockPos pos, BlockState state, Random random) {
        if (state.getBlock() instanceof TallPlantBlock) {
            ((TallPlantBlock)state.getBlock()).placeAt(world, pos, 2);
        } else {
            world.setBlockState(pos, state, 2);
        }
    }

    @Override
    public <T> T serialize(DynamicOps<T> ops) {
        return null;
    }
}
