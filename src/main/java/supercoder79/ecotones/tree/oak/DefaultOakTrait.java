package supercoder79.ecotones.tree.oak;

import supercoder79.ecotones.tree.OakTrait;

import java.util.Random;

public class DefaultOakTrait implements OakTrait {
    public static final DefaultOakTrait INSTANCE = new DefaultOakTrait();

    @Override
    public String name() {
        return "Default";
    }
}
