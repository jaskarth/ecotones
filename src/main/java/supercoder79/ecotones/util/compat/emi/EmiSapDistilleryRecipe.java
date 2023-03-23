package supercoder79.ecotones.util.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.Ecotones;

import java.util.List;

public class EmiSapDistilleryRecipe implements EmiRecipe {
    private static final Identifier SHEET = Ecotones.id("textures/gui/emi/sap_distillery_sheet.png");
    private static final EmiTexture BACKING_TEX = new EmiTexture(SHEET, 0, 0, 76, 57, 76, 57, 80, 64);

    private final String id;
    private final Item in;
    private final Item out;

    public EmiSapDistilleryRecipe(String id, Item in, Item out) {
        this.id = id;
        this.in = in;
        this.out = out;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EcotonesEmi.SAP_DISTILLERY_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return Ecotones.id(id);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(EmiStack.of(in, 8));
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(EmiStack.of(out, 1));
    }

    @Override
    public int getDisplayWidth() {
        return 76;
    }

    @Override
    public int getDisplayHeight() {
        return 57;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BACKING_TEX, 0, 0);
        widgets.addSlot(EmiStack.of(in, 8), 0, 0).recipeContext(this);
        widgets.addSlot(EmiStack.of(out), 58, 0).recipeContext(this);
    }
}
