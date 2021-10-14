package supercoder79.ecotones.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.items.EcotonesItems;

import java.util.HashMap;
import java.util.Map;

public final class EcotonesGrindingRecipes {
    public static final Map<Identifier, GrindingRecipe> RECIPES = new HashMap<>();

    public static void init() {
        RECIPES.put(Ecotones.id("pyrite"), new GrindingRecipe(
                EcotonesItems.PYRITE_ITEM,
                Items.RAW_IRON,
                EcotonesItems.SULFUR,
                0.33,
                0.25,
                false
        ));

        RECIPES.put(Ecotones.id("malachite"), new GrindingRecipe(
                EcotonesItems.MALACHITE_ITEM,
                Items.RAW_COPPER,
                EcotonesItems.SULFUR,
                0.33,
                0.25,
                false
        ));

        RECIPES.put(Ecotones.id("blueberry"), new GrindingRecipe(
                new ItemStack(EcotonesItems.BLUEBERRIES, 4),
                new ItemStack(EcotonesItems.BLUEBERRY_JAM),
                null,
                1,
                0,
                true
        ));
    }
}
