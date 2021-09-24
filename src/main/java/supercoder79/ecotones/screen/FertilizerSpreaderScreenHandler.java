package supercoder79.ecotones.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.tag.ItemTags;
import supercoder79.ecotones.items.EcotonesItems;

public class FertilizerSpreaderScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;

    public FertilizerSpreaderScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1), new ArrayPropertyDelegate(4));
    }

    public FertilizerSpreaderScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(EcotonesScreenHandlers.FERTILIZER_SPREADER, syncId);

        this.propertyDelegate = propertyDelegate;

        this.addSlot(new FertilizerSlot(inventory, 0, 80, 71 - 7));

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

    public int getPercent() {
        return this.propertyDelegate.get(0);
    }

    public int getFertilizerAmount() {
        return this.propertyDelegate.get(1);
    }

    public int getFarmCount() {
        return this.propertyDelegate.get(2);
    }

    public int getWaterCount() {
        return this.propertyDelegate.get(3);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();

            if (index != 0) {
                if (itemStack.getItem() == EcotonesItems.BASIC_FERTILIZER) {
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

    private static class FertilizerSlot extends Slot {
        public FertilizerSlot(Inventory inventory, int i, int j, int k) {
            super(inventory, i, j, k);
        }

        public boolean canInsert(ItemStack stack) {
            return stack.getItem() == EcotonesItems.BASIC_FERTILIZER;
        }

        public int getMaxItemCount() {
            return 64;
        }
    }
}
