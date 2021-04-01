package supercoder79.ecotones.world.tree.trait;

import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.TreeType;

public class EcotonesTreeTraits {
    public static final TraitContainer<OakTrait> OAK = new TraitContainer<>(Traits.OAK, "Oak", TreeType.OAK_SALT);
    public static final TraitContainer<SmallSpruceTrait> SMALL_SPRUCE = new TraitContainer<>(Traits.SMALL_SPRUCE, "Small Spruce", TreeType.SMALL_SPRUCE_SALT);
    public static final TraitContainer<PoplarTrait> POPLAR = new TraitContainer<>(Traits.POPLAR, "Poplar", TreeType.POPLAR_SALT);

    public static void init() {
        register("oak", OAK);
        register("small_spruce", SMALL_SPRUCE);
        register("poplar", POPLAR);
    }

    private static void register(String name, TraitContainer<? extends Trait> traits) {
        TreeTraitRegistry.register(Ecotones.id(name), traits);
    }
}
