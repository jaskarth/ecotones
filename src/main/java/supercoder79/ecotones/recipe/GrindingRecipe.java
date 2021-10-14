package supercoder79.ecotones.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record GrindingRecipe(ItemStack in, ItemStack mainOutput, @Nullable ItemStack secondaryOutput, double mainChance, double secondaryChance, boolean needsBottle) {
    // single item constructors
    public GrindingRecipe(Item in, Item mainOutput, @Nullable Item secondaryOutput, double mainChance, double secondaryChance, boolean needsBottle) {
        this(new ItemStack(in), new ItemStack(mainOutput), secondaryOutput == null ? null : new ItemStack(secondaryOutput), mainChance, secondaryChance, needsBottle);
    }
}
