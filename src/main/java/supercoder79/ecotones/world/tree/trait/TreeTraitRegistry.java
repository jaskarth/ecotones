package supercoder79.ecotones.world.tree.trait;

import net.minecraft.util.Identifier;

import java.util.*;

public final class TreeTraitRegistry {
    private static final Map<Identifier, TraitContainer<? extends Trait>> REGISTRY = new HashMap<>();

    public static void register(Identifier id, TraitContainer<? extends Trait> traits) {
        REGISTRY.put(id, traits);
    }

    public static TraitContainer<? extends Trait> get(Identifier id) {
        return REGISTRY.get(id);
    }

    public static Set<Identifier> getKeys() {
        return REGISTRY.keySet();
    }
}
