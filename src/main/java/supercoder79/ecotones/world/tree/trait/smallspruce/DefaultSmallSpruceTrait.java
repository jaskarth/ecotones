package supercoder79.ecotones.world.tree.trait.smallspruce;

import supercoder79.ecotones.world.tree.trait.SmallSpruceTrait;

public class DefaultSmallSpruceTrait implements SmallSpruceTrait {
    public static final DefaultSmallSpruceTrait INSTANCE = new DefaultSmallSpruceTrait();

    @Override
    public String name() {
        return "Default";
    }
}
