package supercoder79.ecotones.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.mixin.BlockStateProviderTypeAccessor;

public final class EcotonesBlockStateProviders {
    public static final BlockStateProviderType<DeferredBlockStateProvider> DEFERRED = BlockStateProviderTypeAccessor.createBlockStateProviderType(DeferredBlockStateProvider.CODEC);

    public static void init() {
        register("deferred", DEFERRED);
    }

    private static void register(String name, BlockStateProviderType<?> provider) {
        Registry.register(Registry.BLOCK_STATE_PROVIDER_TYPE, Ecotones.id(name), provider);
        RegistryReport.increment("Blockstate Provider");
    }
}
