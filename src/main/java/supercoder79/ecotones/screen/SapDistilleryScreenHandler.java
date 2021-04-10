package supercoder79.ecotones.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class SapDistilleryScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;

    public SapDistilleryScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(4));
    }

    public SapDistilleryScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(EcotonesScreenHandlers.SAP_DISTILLERY, syncId);
        this.propertyDelegate = propertyDelegate;

        this.addSlot(new Slot(inventory, 0, 26, 37 - 7));
        this.addSlot(new Slot(inventory, 1, 80, 79 - 7));

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

    // TODO: implement shift clicking

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
