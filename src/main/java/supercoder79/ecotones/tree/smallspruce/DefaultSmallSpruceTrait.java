package supercoder79.ecotones.tree.smallspruce;

import supercoder79.ecotones.tree.SmallSpruceTrait;

public class DefaultSmallSpruceTrait implements SmallSpruceTrait {
    public static final DefaultSmallSpruceTrait INSTANCE = new DefaultSmallSpruceTrait();

    @Override
    public String name() {
        return "Default";
    }
}
