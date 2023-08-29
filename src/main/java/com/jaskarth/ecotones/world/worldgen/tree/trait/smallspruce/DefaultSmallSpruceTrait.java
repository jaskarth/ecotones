package com.jaskarth.ecotones.world.worldgen.tree.trait.smallspruce;

public class DefaultSmallSpruceTrait implements SmallSpruceTrait {
    public static final DefaultSmallSpruceTrait INSTANCE = new DefaultSmallSpruceTrait();

    @Override
    public String name() {
        return "Default";
    }
}
