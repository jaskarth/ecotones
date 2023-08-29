package com.jaskarth.ecotones.world.worldgen.tree.trait.poplar;

public class DefaultPoplarTrait implements PoplarTrait {
    public static final DefaultPoplarTrait INSTANCE = new DefaultPoplarTrait();

    @Override
    public String name() {
        return "Default";
    }
}
