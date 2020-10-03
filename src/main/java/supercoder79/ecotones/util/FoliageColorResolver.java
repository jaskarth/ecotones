package supercoder79.ecotones.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface FoliageColorResolver {
    @Environment(EnvType.CLIENT)
    int getFoliageColorAt(double x, double z);
}
