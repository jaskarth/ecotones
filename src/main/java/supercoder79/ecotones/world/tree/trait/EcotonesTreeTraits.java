package supercoder79.ecotones.world.tree.trait;

import com.google.common.collect.ImmutableList;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.TreeType;
import supercoder79.ecotones.world.tree.trait.aspen.*;
import supercoder79.ecotones.world.tree.trait.oak.*;
import supercoder79.ecotones.world.tree.trait.poplar.*;
import supercoder79.ecotones.world.tree.trait.smallspruce.*;

public final class EcotonesTreeTraits {
    public static final TraitContainer<OakTrait> OAK = new TraitContainer<>(ImmutableList.of(new DefaultOakTrait(), new ThinOakTrait(), new WarpedOakTrait(), new SmallerOakTrait()), "Oak", TreeType.OAK_SALT);
    public static final TraitContainer<SmallSpruceTrait> SMALL_SPRUCE = new TraitContainer<>(ImmutableList.of(new DefaultSmallSpruceTrait(), new ShortSmallSpruceTrait(), new WideSmallSpruceTrait(), new ThinSmallSpruceTrait()), "Small Spruce", TreeType.SMALL_SPRUCE_SALT);
    public static final TraitContainer<PoplarTrait> POPLAR = new TraitContainer<>(ImmutableList.of(new DefaultPoplarTrait(), new ShortPoplarTrait(), new WidePoplarTrait(), new TaperedPoplarTrait()), "Poplar", TreeType.POPLAR_SALT);
    public static final TraitContainer<AspenTrait> ASPEN = new TraitContainer<>(ImmutableList.of(new DefaultAspenTrait(), new StraightAspenTrait(), new ShorterAspenTrait(), new ThinAspenTrait()), "Aspen", 446261);

    public static void init() {
        register("oak", OAK);
        register("small_spruce", SMALL_SPRUCE);
        register("poplar", POPLAR);
        register("aspen", ASPEN);
    }

    private static void register(String name, TraitContainer<? extends Trait> traits) {
        TreeTraitRegistry.register(Ecotones.id(name), traits);
    }
}
