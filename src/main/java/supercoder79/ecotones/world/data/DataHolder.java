package supercoder79.ecotones.world.data;

import net.minecraft.util.Identifier;

public interface DataHolder {
    DataFunction get(Identifier id);

    default double get(Identifier id, double x, double z) {
        return get(id).get(x, z);
    }
}
