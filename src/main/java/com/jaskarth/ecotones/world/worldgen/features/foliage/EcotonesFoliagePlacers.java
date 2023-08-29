package com.jaskarth.ecotones.world.worldgen.features.foliage;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.mixin.FoliagePlacerTypeAccessor;
import com.jaskarth.ecotones.util.RegistryReport;

public final class EcotonesFoliagePlacers {
    public static final FoliagePlacerType<PlusLeavesFoliagePlacer> PLUS_LEAVES = FoliagePlacerTypeAccessor.createFoliagePlacerType(PlusLeavesFoliagePlacer.CODEC);
    public static final FoliagePlacerType<SmallPineFoliagePlacer> SMALL_PINE = FoliagePlacerTypeAccessor.createFoliagePlacerType(SmallPineFoliagePlacer.CODEC);

    public static void init() {
        register("plus_leaves", PLUS_LEAVES);
        register("small_pine", SMALL_PINE);
    }

    private static void register(String name, FoliagePlacerType<?> type) {
        Registry.register(Registries.FOLIAGE_PLACER_TYPE, Ecotones.id(name), type);
        RegistryReport.increment("Foliage Placer");
    }
}
