package supercoder79.ecotones.recipe;

import net.minecraft.util.Identifier;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.items.EcotonesItems;

import java.util.HashMap;
import java.util.Map;

public final class EcotonesSapDistilleryRecipes {
    public static final Map<Identifier, SapDistilleryRecipe> RECIPES = new HashMap<>();

    public static void init() {
        RECIPES.put(Ecotones.id("maple"), new SapDistilleryRecipe(
                EcotonesItems.MAPLE_SAP,
                EcotonesItems.MAPLE_SYRUP,
                0x0000000
        ));

        RECIPES.put(Ecotones.id("turpentine"), new SapDistilleryRecipe(
                EcotonesItems.PINE_SAP,
                EcotonesItems.TURPENTINE,
                0x0000000
        ));
    }
}
