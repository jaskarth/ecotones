package supercoder79.ecotones.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockStateProviderType.class)
public interface BlockStateProviderTypeAccessor {
    @Invoker(value = "<init>")
    static <P extends BlockStateProvider> BlockStateProviderType<P> createBlockStateProviderType(Codec<P> codec) {
        throw new UnsupportedOperationException();
    }
}
