package supercoder79.ecotones.world.tree.trait.oak;

import supercoder79.ecotones.world.tree.trait.OakTrait;

public class DefaultOakTrait implements OakTrait {
    public static final DefaultOakTrait INSTANCE = new DefaultOakTrait();

    @Override
    public String name() {
        return "Default";
    }
}
