package com.jaskarth.ecotones.world.worldgen.tree.trait.aspen;

public class DefaultAspenTrait implements AspenTrait {
    public static final AspenTrait INSTANCE = new DefaultAspenTrait();

    @Override
    public String name() {
        return "Default";
    }
}
