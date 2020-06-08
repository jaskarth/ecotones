package supercoder79.ecotones.util;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.placer.BlockPlacer;
import net.minecraft.world.gen.placer.BlockPlacerType;

import java.util.Random;

public class DoubleOrNormalPlacer extends BlockPlacer {
    public static final DoubleOrNormalPlacer INSTANCE = new DoubleOrNormalPlacer();
    public static Codec<DoubleOrNormalPlacer> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public void method_23403(WorldAccess world, BlockPos pos, BlockState state, Random random) {
        if (state.getBlock() instanceof TallPlantBlock) {
            ((TallPlantBlock)state.getBlock()).placeAt(world, pos, 2);
        } else {
            world.setBlockState(pos, state, 2);
        }
    }

    @Override
    protected BlockPlacerType<?> method_28673() {
        return EcotonesBlockPlacers.DOUBLE_OR_NORMAL;
    }
}
