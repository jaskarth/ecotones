package com.jaskarth.ecotones.world.data;

import net.minecraft.util.Identifier;

public class DefaultDataHolder implements DataHolder {
    public static final DataHolder INSTANCE = new DefaultDataHolder();

    @Override
    public DataFunction get(Identifier id) {
        return DataFunction.NOOP;
    }
}
