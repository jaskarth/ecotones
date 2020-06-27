package supercoder79.ecotones.world.features.foliage;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import supercoder79.ecotones.mixin.FoliagePlacerTypeAccessor;

public class EcotonesFoliagePlacers {
    public static FoliagePlacerType<PlusLeavesFoliagePlacer> PLUS_LEAVES;

    public static void init() {
        PLUS_LEAVES = Registry.register(Registry.FOLIAGE_PLACER_TYPE, new Identifier("ecotones", "plus_leaves_foliage_placer"), FoliagePlacerTypeAccessor.createFoliagePlacerType(PlusLeavesFoliagePlacer.CODEC));
    }
}
