package com.jaskarth.ecotones.screen;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import com.jaskarth.ecotones.world.items.EcotonesItems;
import com.jaskarth.ecotones.recipe.EcotonesGrindingRecipes;

public class GrindstoneScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;

    public GrindstoneScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5), new ArrayPropertyDelegate(5));
    }

    public GrindstoneScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(EcotonesScreenHandlers.GRINDSTONE, syncId);
        this.propertyDelegate = propertyDelegate;

        this.addSlot(new FuelSlot(inventory, 0, 152, 74 - 7 - 1));
        this.addSlot(new Slot(inventory, 1, 80, 5 - 7 - 1));
        this.addSlot(new JarSlot(inventory, 2, 116, 30 - 7 - 1));
        this.addSlot(new OutSlot(inventory, 3, 80, 74 - 7 - 1));
        this.addSlot(new OutSlot(inventory, 4, 44, 74 - 7 - 1));

        for(int k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18 + 8 - 1));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142 + 8 - 1));
        }

        this.addProperties(propertyDelegate);
    }

    public int getBurnTime() {
        return this.propertyDelegate.get(0);
    }

    public int getBurnTimeMax() {
        return this.propertyDelegate.get(1);
    }

    public int getProgress() {
        return this.propertyDelegate.get(2);
    }

    public int getMainChance() {
        return this.propertyDelegate.get(3);
    }

    public int getAltChance() {
        return this.propertyDelegate.get(4);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();

            int start = 5;
            if (index == 3 || index == 4) {
                if (!this.insertItem(itemStack2, start, 36 + start, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index != 0 && index != 1 && index != 2) {
                if (AbstractFurnaceBlockEntity.canUseAsFuel(itemStack)) {
                    if (this.insertItem(itemStack2, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (EcotonesGrindingRecipes.findRecipeByInput(itemStack.getItem()) != null) {
                    if (this.insertItem(itemStack2, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemStack2.getItem() == EcotonesItems.JAR) {
                    if (this.insertItem(itemStack2, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                if (!this.insertItem(itemStack2, start, start + 36, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    private static final class FuelSlot extends Slot {
        public FuelSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return AbstractFurnaceBlockEntity.canUseAsFuel(stack);
        }
    }

    private static final class JarSlot extends Slot {
        public JarSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.isOf(EcotonesItems.JAR);
        }
    }

    private static final class OutSlot extends Slot {
        public OutSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }
    }
}
