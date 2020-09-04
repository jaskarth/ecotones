package supercoder79.ecotones.tree.poplar;

import supercoder79.ecotones.tree.PoplarTrait;

public class DefaultPoplarTrait implements PoplarTrait {
    public static final DefaultPoplarTrait INSTANCE = new DefaultPoplarTrait();

    @Override
    public String name() {
        return "Default";
    }
}
