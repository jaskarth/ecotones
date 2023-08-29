package com.jaskarth.ecotones.util.state;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.mixin.BlockStateProviderTypeAccessor;
import com.jaskarth.ecotones.util.RegistryReport;

public final class EcotonesBlockStateProviders {
    public static final BlockStateProviderType<DeferredBlockStateProvider> DEFERRED = BlockStateProviderTypeAccessor.createBlockStateProviderType(DeferredBlockStateProvider.CODEC);
    public static final BlockStateProviderType<SmallCactusBlockStateProvider> SMALL_CACTUS = BlockStateProviderTypeAccessor.createBlockStateProviderType(SmallCactusBlockStateProvider.CODEC);
    public static final BlockStateProviderType<DaffodilProvider> DAFFODIL = BlockStateProviderTypeAccessor.createBlockStateProviderType(DaffodilProvider.CODEC);

    public static void init() {
        register("deferred", DEFERRED);
        register("small_cactus", SMALL_CACTUS);
        register("daffodil", DAFFODIL);
    }

    private static void register(String name, BlockStateProviderType<?> provider) {
        Registry.register(Registries.BLOCK_STATE_PROVIDER_TYPE, Ecotones.id(name), provider);
        RegistryReport.increment("Blockstate Provider");
    }
}
