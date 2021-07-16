package supercoder79.ecotones.util.state;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.blocks.SmallCactusBlock;

import java.util.Random;

public class SmallCactusBlockStateProvider extends BlockStateProvider {
    public static final SmallCactusBlockStateProvider INSTANCE = new SmallCactusBlockStateProvider();
    public static final Codec<SmallCactusBlockStateProvider> CODEC = Codec.unit(INSTANCE);

    @Override
    protected BlockStateProviderType<?> getType() {
        return EcotonesBlockStateProviders.SMALL_CACTUS;
    }

    @Override
    public BlockState getBlockState(Random random, BlockPos pos) {
        return EcotonesBlocks.SMALL_CACTUS.getDefaultState().with(SmallCactusBlock.FRUITING, random.nextBoolean());
    }
}
