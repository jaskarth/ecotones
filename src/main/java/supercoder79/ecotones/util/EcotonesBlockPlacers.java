package supercoder79.ecotones.util;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.placer.BlockPlacerType;
import supercoder79.ecotones.mixin.BlockPlacerTypeAccessor;

public class EcotonesBlockPlacers {
    public static BlockPlacerType<DoubleOrNormalPlacer> DOUBLE_OR_NORMAL;

    public static void init() {
        DOUBLE_OR_NORMAL = Registry.register(Registry.BLOCK_PLACER_TYPE, new Identifier("ecotones", "double_or_normal_placer"), BlockPlacerTypeAccessor.createBlockPlacerType(DoubleOrNormalPlacer.CODEC));
    }
}
