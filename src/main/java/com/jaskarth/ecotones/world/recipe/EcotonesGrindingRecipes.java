package com.jaskarth.ecotones.world.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.world.items.EcotonesItems;

import java.util.HashMap;
import java.util.Map;

public final class EcotonesGrindingRecipes {
    public static final Map<Identifier, GrindingRecipe> RECIPES = new HashMap<>();
    private static final Map<Item, Identifier> INPUT2RECIPE = new HashMap<>();

    public static void init() {
        register("pyrite", new GrindingRecipe(
                EcotonesItems.PYRITE_ITEM,
                Items.RAW_IRON,
                EcotonesItems.PHOSPHATE,
                0.33,
                0.25,
                false
        ));

        register("malachite", new GrindingRecipe(
                EcotonesItems.MALACHITE_ITEM,
                Items.RAW_COPPER,
                EcotonesItems.SULFUR,
                0.33,
                0.25,
                false
        ));

        register("blueberry", new GrindingRecipe(
                new ItemStack(EcotonesItems.BLUEBERRIES, 4),
                new ItemStack(EcotonesItems.BLUEBERRY_JAM),
                null,
                1,
                0,
                true
        ));
    }

    private static void register(String id, GrindingRecipe recipe) {
        RECIPES.put(Ecotones.id(id), recipe);
        Item item = recipe.in().getItem();

        if (INPUT2RECIPE.containsKey(item)) {
            throw new IllegalArgumentException("Already have recipe for item of type " + item + " when trying to register " + id + " from " + INPUT2RECIPE.get(item));
        }

        INPUT2RECIPE.put(item, Ecotones.id(id));
    }

    public static Identifier findRecipeByInput(Item item) {
        return INPUT2RECIPE.get(item);
    }
}
