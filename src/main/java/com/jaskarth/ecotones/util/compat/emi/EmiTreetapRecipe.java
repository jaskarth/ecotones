package com.jaskarth.ecotones.util.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import com.jaskarth.ecotones.Ecotones;

import java.util.List;

public class EmiTreetapRecipe implements EmiRecipe {
    private static final Identifier SHEET = Ecotones.id("textures/gui/emi/treetap_sheet.png");
    private static final EmiTexture BACKING_TEX = new EmiTexture(SHEET, 0, 0, 55, 68, 55, 68, 80, 80);

    private final String id;
    private final Item out;

    public EmiTreetapRecipe(String id, Item out) {
        this.id = id;
        this.out = out;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EcotonesEmi.TREETAP_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return Ecotones.id(id);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of();
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(EmiStack.of(out, 1));
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }

    @Override
    public int getDisplayWidth() {
        return 55;
    }

    @Override
    public int getDisplayHeight() {
        return 68;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BACKING_TEX, 0, 0);
        widgets.addSlot(EmiStack.of(out), 23, 50).recipeContext(this);
    }
}
