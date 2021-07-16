package supercoder79.ecotones.advancement;

import supercoder79.ecotones.mixin.CriteriaAccessor;

public final class EcotonesCriteria {
    public static final EnterEcotonesWorldCriterion ENTER_ECOTONES_WORLD = new EnterEcotonesWorldCriterion();

    public static void init() {
        CriteriaAccessor.callRegister(ENTER_ECOTONES_WORLD);
    }
}
