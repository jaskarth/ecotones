package com.jaskarth.ecotones.world.blocks;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;
import java.util.List;

public class CyanRoseBlock extends EcotonesPlantBlock {
    protected CyanRoseBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("tooltip.amelia_rose_1").formatted(Formatting.AQUA, Formatting.ITALIC));
        tooltip.add(Text.translatable("tooltip.amelia_rose_2").formatted(Formatting.AQUA, Formatting.ITALIC));
    }
}
