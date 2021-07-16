package supercoder79.ecotones.world.data;

import net.minecraft.util.Identifier;
import supercoder79.ecotones.Ecotones;

public class EcotonesData {
    public static final Identifier SOIL_QUALITY = Ecotones.id("soil_quality");
    public static final Identifier SOIL_DRAINAGE = Ecotones.id("soil_drainage");
    public static final Identifier SOIL_ROCKINESS = Ecotones.id("soil_rockiness");
    public static final Identifier SOIL_PH = Ecotones.id("soil_ph");

    public static final Identifier GRASS_NOISE = Ecotones.id("grass_noise");

    public static void init() {
        // TODO: some sort of default impl thing
    }
}
