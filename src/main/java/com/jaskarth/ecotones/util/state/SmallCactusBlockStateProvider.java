package com.jaskarth.ecotones.util.state;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.blocks.SmallCactusBlock;

public class SmallCactusBlockStateProvider extends BlockStateProvider {
    public static final SmallCactusBlockStateProvider INSTANCE = new SmallCactusBlockStateProvider();
    public static final Codec<SmallCactusBlockStateProvider> CODEC = Codec.unit(INSTANCE);

    @Override
    protected BlockStateProviderType<?> getType() {
        return EcotonesBlockStateProviders.SMALL_CACTUS;
    }

    @Override
    public BlockState get(Random random, BlockPos pos) {
        return EcotonesBlocks.SMALL_CACTUS.getDefaultState().with(SmallCactusBlock.FRUITING, random.nextBoolean());
    }
}
