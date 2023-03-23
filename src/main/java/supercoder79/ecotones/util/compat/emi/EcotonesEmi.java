package supercoder79.ecotones.util.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.util.Identifier;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.items.EcotonesItems;
import supercoder79.ecotones.recipe.EcotonesGrindingRecipes;
import supercoder79.ecotones.recipe.GrindingRecipe;

import java.util.Map;

public class EcotonesEmi implements EmiPlugin {
    public static final EmiRecipeCategory GRINDSTONE_CATEGORY = new EmiRecipeCategory(Ecotones.id( "grindstone"), EmiStack.of(EcotonesBlocks.GRINDSTONE));
    public static final EmiRecipeCategory SAP_DISTILLERY_CATEGORY = new EmiRecipeCategory(Ecotones.id( "sap_distillery"), EmiStack.of(EcotonesBlocks.SAP_DISTILLERY));
    public static final EmiRecipeCategory TREETAP_CATEGORY = new EmiRecipeCategory(Ecotones.id( "treetap"), EmiStack.of(EcotonesBlocks.TREETAP));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(GRINDSTONE_CATEGORY);
        registry.addCategory(SAP_DISTILLERY_CATEGORY);
        registry.addCategory(TREETAP_CATEGORY);

        registry.addWorkstation(GRINDSTONE_CATEGORY, EmiStack.of(EcotonesBlocks.GRINDSTONE));
        registry.addWorkstation(SAP_DISTILLERY_CATEGORY, EmiStack.of(EcotonesBlocks.SAP_DISTILLERY));
        registry.addWorkstation(TREETAP_CATEGORY, EmiStack.of(EcotonesBlocks.TREETAP));

        for (Map.Entry<Identifier, GrindingRecipe> e : EcotonesGrindingRecipes.RECIPES.entrySet()) {
            registry.addRecipe(new EmiGrindstoneRecipe(e.getKey(), e.getValue()));
        }

        registry.addRecipe(new EmiSapDistilleryRecipe("maple_syrup", EcotonesItems.MAPLE_SAP, EcotonesItems.MAPLE_SYRUP));
        registry.addRecipe(new EmiTreetapRecipe("maple_sap", EcotonesItems.MAPLE_SAP));
    }
}
