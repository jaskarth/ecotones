package supercoder79.ecotones.client.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EcotonesParticles {
    public static DefaultParticleType SAND;

    public static void init() {
        SAND = Registry.register(Registry.PARTICLE_TYPE, new Identifier("ecotones", "sand"), FabricParticleTypes.simple(false));
    }

}
