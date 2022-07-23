package supercoder79.ecotones.screen;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.items.EcotonesItems;

public class GrindstoneScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;

    public GrindstoneScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5), new ArrayPropertyDelegate(5));
    }

    public GrindstoneScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(EcotonesScreenHandlers.GRINDSTONE, syncId);
        this.propertyDelegate = propertyDelegate;

        this.addSlot(new FuelSlot(inventory, 0, 152, 74 - 7));
        this.addSlot(new Slot(inventory, 1, 80, 5 - 7));
        this.addSlot(new JarSlot(inventory, 2, 116, 30 - 7));
        this.addSlot(new OutSlot(inventory, 3, 80, 74 - 7));
        this.addSlot(new OutSlot(inventory, 4, 44, 74 - 7));

        for(int k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18 + 8));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142 + 8));
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
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return null;
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
