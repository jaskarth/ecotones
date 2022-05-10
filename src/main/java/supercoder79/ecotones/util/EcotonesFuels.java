package supercoder79.ecotones.util;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import supercoder79.ecotones.items.EcotonesItems;

public final class EcotonesFuels {
    public static void init() {
        FuelRegistry.INSTANCE.add(EcotonesItems.PEAT_ITEM, 400);
        FuelRegistry.INSTANCE.add(EcotonesItems.SULFUR, 200);
        FuelRegistry.INSTANCE.add(EcotonesItems.PHOSPHATE, 200);
    }
}
