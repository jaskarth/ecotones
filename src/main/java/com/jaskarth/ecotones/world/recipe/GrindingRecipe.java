package com.jaskarth.ecotones.world.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

// TODO: convert this into a normal recipe [after 1.20.2]
public record GrindingRecipe(ItemStack in, ItemStack mainOutput, @Nullable ItemStack secondaryOutput, double mainChance, double secondaryChance, boolean needsBottle) {
    // single item constructors
    public GrindingRecipe(Item in, Item mainOutput, @Nullable Item secondaryOutput, double mainChance, double secondaryChance, boolean needsBottle) {
        this(new ItemStack(in), new ItemStack(mainOutput), secondaryOutput == null ? null : new ItemStack(secondaryOutput), mainChance, secondaryChance, needsBottle);
    }
}
