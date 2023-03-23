package supercoder79.ecotones.world.data;

import net.minecraft.util.Identifier;
import supercoder79.ecotones.Ecotones;

import java.util.HashMap;
import java.util.Map;

public class EcotonesData {
    public static final Identifier SOIL_QUALITY = Ecotones.id("soil_quality");
    public static final Identifier SOIL_DRAINAGE = Ecotones.id("soil_drainage");
    public static final Identifier SOIL_ROCKINESS = Ecotones.id("soil_rockiness");
    public static final Identifier SOIL_PH = Ecotones.id("soil_ph");

    public static final Identifier GRASS_NOISE = Ecotones.id("grass_noise");

    public static final Identifier FLOWER_MOSAIC = Ecotones.id("flower_mosaic");
    public static final Identifier FLOWER_MOSAIC_ALT = Ecotones.id("flower_mosaic_alt");

    public static void init() {
        // TODO: some sort of default impl thing
    }
}
