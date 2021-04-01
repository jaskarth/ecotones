package supercoder79.ecotones.world.tree.trait.poplar;

public class DefaultPoplarTrait implements PoplarTrait {
    public static final DefaultPoplarTrait INSTANCE = new DefaultPoplarTrait();

    @Override
    public String name() {
        return "Default";
    }
}
