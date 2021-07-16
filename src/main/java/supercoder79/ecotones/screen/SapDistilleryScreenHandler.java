package supercoder79.ecotones.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.tag.ItemTags;
import supercoder79.ecotones.items.EcotonesItems;

public class SapDistilleryScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;

    public SapDistilleryScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(4));
    }

    public SapDistilleryScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(EcotonesScreenHandlers.SAP_DISTILLERY, syncId);
        this.propertyDelegate = propertyDelegate;

        this.addSlot(new SapSlot(inventory, 0, 26, 37 - 7));
        this.addSlot(new LogSlot(inventory, 1, 80, 79 - 7));

        int k;
        for(k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18 + 8));
            }
        }

        for(k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142 + 8));
        }

        this.addProperties(propertyDelegate);
    }

    public int getBurnTime() {
        return this.propertyDelegate.get(0);
    }

    public int getHeat() {
        return this.propertyDelegate.get(1);
    }

    public int getSapAmount() {
        return this.propertyDelegate.get(2);
    }

    public int getSyrupAmount() {
        return this.propertyDelegate.get(3);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();

            if (index != 0 && index != 1) {
                if (itemStack.isIn(ItemTags.LOGS_THAT_BURN)) {
                    if (this.insertItem(itemStack2, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemStack.getItem() == EcotonesItems.MAPLE_SAP) {
                    if (this.insertItem(itemStack2, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                if (!this.insertItem(itemStack2, 3, 38, true)) {
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

    static class SapSlot extends Slot {
        public SapSlot(Inventory inventory, int i, int j, int k) {
            super(inventory, i, j, k);
        }

        public boolean canInsert(ItemStack stack) {
            return stack.getItem() == EcotonesItems.MAPLE_SAP;
        }

        public int getMaxItemCount() {
            return 64;
        }
    }

    static class LogSlot extends Slot {
        public LogSlot(Inventory inventory, int i, int j, int k) {
            super(inventory, i, j, k);
        }

        public boolean canInsert(ItemStack stack) {
            return stack.isIn(ItemTags.LOGS_THAT_BURN);
        }

        public int getMaxItemCount() {
            return 64;
        }
    }
}
