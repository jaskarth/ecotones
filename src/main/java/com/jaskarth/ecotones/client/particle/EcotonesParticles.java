package com.jaskarth.ecotones.client.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.util.RegistryReport;

public final class EcotonesParticles {
    public static final DefaultParticleType SAND = FabricParticleTypes.simple(false);
    public static final DefaultParticleType MAPLE_LEAF = FabricParticleTypes.simple(false);
    public static final DefaultParticleType SYRUP_POP = FabricParticleTypes.simple(false);
    public static final DefaultParticleType SAP_DRIP = FabricParticleTypes.simple(false);
    public static final DefaultParticleType CHIMNEY_SMOKE = FabricParticleTypes.simple(false);
    public static final DefaultParticleType DIRECTED_SMOKE = FabricParticleTypes.simple(false);

    public static void init() {
        register("sand", SAND);
        register("maple_leaf", MAPLE_LEAF);
        register("syrup_pop", SYRUP_POP);
        register("sap_drip", SAP_DRIP);
        register("chimney_smoke", CHIMNEY_SMOKE);
        register("directed_smoke", DIRECTED_SMOKE);
    }

    private static void register(String string, ParticleType<?> type) {
        Registry.register(Registries.PARTICLE_TYPE, Ecotones.id(string), type);
        RegistryReport.increment("Particle Type");
    }
}
