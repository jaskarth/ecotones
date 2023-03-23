package supercoder79.ecotones.util.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.items.EcotonesItems;
import supercoder79.ecotones.recipe.GrindingRecipe;

import java.util.ArrayList;
import java.util.List;

public class EmiGrindstoneRecipe implements EmiRecipe {
    private static final Identifier SHEET = Ecotones.id("textures/gui/emi/grindstone_sheet.png");
    private static final EmiTexture BACKING_TEX = new EmiTexture(SHEET, 0, 0, 111, 69);
    private static final EmiTexture SECONDARY_TEX = new EmiTexture(SHEET, 161, 0, 19, 43);
    private static final EmiTexture JAR_TEX = new EmiTexture(SHEET, 111, 0, 27, 19);
    private static final EmiTexture LABEL_TEX = new EmiTexture(SHEET, 138, 0, 23, 12);
    private final Identifier id;
    private final GrindingRecipe backing;

    public EmiGrindstoneRecipe(Identifier id, GrindingRecipe backing) {
        this.id = id;
        this.backing = backing;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EcotonesEmi.GRINDSTONE_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        List<EmiIngredient> list = new ArrayList<>();
        list.add(EmiStack.of(backing.in().copy()));
        if (backing.needsBottle()) {
            list.add(EmiStack.of(EcotonesItems.JAR));
        }

        return list;
    }

    @Override
    public List<EmiStack> getOutputs() {
        List<EmiStack> list = new ArrayList<>();
        list.add(EmiStack.of(backing.mainOutput().copy()).setChance((float) backing.mainChance()));
        if (backing.secondaryOutput() != null) {
            list.add(EmiStack.of(backing.secondaryOutput().copy()).setChance((float) backing.secondaryChance()));
        }

        return list;
    }

    @Override
    public boolean supportsRecipeTree() {
        return true;
    }

    @Override
    public int getDisplayWidth() {
        return 134;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BACKING_TEX, 0, 0);
        widgets.addSlot(EmiStack.of(backing.in()),  57, 0).recipeContext(this);
        widgets.addSlot(EmiStack.of(backing.mainOutput()).setChance((float) backing.mainChance()),  57, 51).recipeContext(this);
        if (backing.secondaryOutput() != null) {
            widgets.addTexture(SECONDARY_TEX, 21, 26);
            widgets.addSlot(EmiStack.of(backing.secondaryOutput()).setChance((float) backing.secondaryChance()),  21, 51).recipeContext(this);
        }

        if (backing.needsBottle()) {
            widgets.addTexture(JAR_TEX, 84, 21);
            widgets.addSlot(EmiStack.of(EcotonesItems.JAR),  93, 22);
        }

        if (backing.mainChance() != 1) {
            widgets.addTexture(LABEL_TEX, 76, 44);
            widgets.addText(Text.literal(((int)(backing.mainChance() * 100)) + "%"), 76 + 2, 44 + 2, 0x404040, false);
        }

        if (backing.secondaryChance() != 0) {
            widgets.addTexture(LABEL_TEX, 1, 24);
            widgets.addText(Text.literal(((int)(backing.secondaryChance() * 100)) + "%"), 1 + 2, 24 + 2, 0x404040, false);
        }
    }
}
