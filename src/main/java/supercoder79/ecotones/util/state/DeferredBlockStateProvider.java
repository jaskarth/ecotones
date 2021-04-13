package supercoder79.ecotones.util.state;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;

import java.util.Random;
import java.util.function.Supplier;

public class DeferredBlockStateProvider extends BlockStateProvider {
    // Theoretically speaking, this shouldn't work. However, at evaluation time (world creation) mod blocks will already
    // have been fully loaded, causing this incredible hack to function against all odds
    public static final Codec<DeferredBlockStateProvider> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.fieldOf("state").forGetter(provider -> provider.supplier.get())
    ).apply(instance, DeferredBlockStateProvider::new));

    private final Supplier<BlockState> supplier;
    private BlockState cache = null;

    // Don't use this one. It won't work
    private DeferredBlockStateProvider(BlockState state) {
        this(() -> state);
    }

    public DeferredBlockStateProvider(Supplier<BlockState> supplier) {
        this.supplier = supplier;
    }

    @Override
    protected BlockStateProviderType<?> getType() {
        return EcotonesBlockStateProviders.DEFERRED;
    }

    @Override
    public BlockState getBlockState(Random random, BlockPos pos) {
        if (this.cache == null) {
            this.cache = this.supplier.get();
        }

        return this.cache;
    }
}
