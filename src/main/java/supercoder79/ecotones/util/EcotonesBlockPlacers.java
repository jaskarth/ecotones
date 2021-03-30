package supercoder79.ecotones.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.placer.BlockPlacerType;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.mixin.BlockPlacerTypeAccessor;

public class EcotonesBlockPlacers {
    public static final BlockPlacerType<DoubleOrNormalPlacer> DOUBLE_OR_NORMAL = BlockPlacerTypeAccessor.createBlockPlacerType(DoubleOrNormalPlacer.CODEC);

    public static void init() {
        register("double_or_normal_placer", DOUBLE_OR_NORMAL);
    }

    private static void register(String name, BlockPlacerType<?> type) {
        Registry.register(Registry.BLOCK_PLACER_TYPE, Ecotones.id(name), type);
    }
}
