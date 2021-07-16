package supercoder79.ecotones.client.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.util.RegistryReport;

public final class EcotonesParticles {
    public static final DefaultParticleType SAND = FabricParticleTypes.simple(false);
    public static final DefaultParticleType MAPLE_LEAF = FabricParticleTypes.simple(false);
    public static final DefaultParticleType SYRUP_POP = FabricParticleTypes.simple(false);
    public static final DefaultParticleType SAP_DRIP = FabricParticleTypes.simple(false);

    public static void init() {
        register("sand", SAND);
        register("maple_leaf", MAPLE_LEAF);
        register("syrup_pop", SYRUP_POP);
        register("sap_drip", SAP_DRIP);
    }

    private static void register(String string, ParticleType<?> type) {
        Registry.register(Registry.PARTICLE_TYPE, Ecotones.id(string), type);
        RegistryReport.increment("Particle Type");
    }
}
