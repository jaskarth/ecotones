package supercoder79.ecotones.blocks.entity;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.tag.ItemTags;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import supercoder79.ecotones.items.EcotonesItems;
import supercoder79.ecotones.screen.SapDistilleryScreenHandler;

public class SapDistilleryBlockEntity extends LockableContainerBlockEntity implements BlockEntityClientSerializable {
    private DefaultedList<ItemStack> inventory;
    protected final PropertyDelegate propertyDelegate;
    // 0 .. 600
    private int burnTime;
    // 0 .. 3000
    private int heatAmount;
    // 0 .. 40,000
    private int sapAmount;
    // 0 .. 5,000
    private int syrupAmount;

    public SapDistilleryBlockEntity(BlockPos pos, BlockState state) {
        super(EcotonesBlockEntities.SAP_DISTILLERY, pos, state);
        this.inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch(index) {
                    case 0:
                        return SapDistilleryBlockEntity.this.burnTime;
                    case 1:
                        return SapDistilleryBlockEntity.this.heatAmount;
                    case 2:
                        return SapDistilleryBlockEntity.this.sapAmount;
                    case 3:
                        return SapDistilleryBlockEntity.this.syrupAmount;
                    default:
                        return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0:
                        SapDistilleryBlockEntity.this.burnTime = value;
                        break;
                    case 1:
                        SapDistilleryBlockEntity.this.heatAmount = value;
                        break;
                    case 2:
                        SapDistilleryBlockEntity.this.sapAmount = value;
                        break;
                    case 3:
                        SapDistilleryBlockEntity.this.syrupAmount = value;
                        break;
                }

            }

            public int size() {
                return 4;
            }
        };
    }

    public static void tick(World world, BlockPos pos, BlockState state, SapDistilleryBlockEntity blockEntity) {
        if (blockEntity.getStack(1).getCount() > 0 && blockEntity.burnTime == 0) {
            blockEntity.getStack(1).decrement(1);

            blockEntity.burnTime = 600;
        }

        if (blockEntity.burnTime > 0) {
            blockEntity.burnTime--;

            if (blockEntity.heatAmount < 3000) {
                blockEntity.heatAmount++;
            }
        } else {
            if (blockEntity.heatAmount > 0) {
                blockEntity.heatAmount--;
            }
        }

        // Try to increment sap amount every tick (illusion of consuming sap slowly)
        if (world.getTime() % 20 == 0) {
            if (blockEntity.getStack(0).getCount() > 0 && blockEntity.sapAmount + 1000 <= 40000) {
                blockEntity.getStack(0).decrement(1);

                blockEntity.sapAmount += 1000;
            }
        }

        if (blockEntity.heatAmount > 1500 && blockEntity.sapAmount >= 8 && blockEntity.syrupAmount < 5000) {
            if (world.getTime() % 2 == 0) {
                blockEntity.sapAmount -= 8;
                blockEntity.syrupAmount++;
                blockEntity.sync();
            }
        }

        blockEntity.markDirty();
    }

    public boolean canFillBottle() {
        return this.syrupAmount >= 1000;
    }

    public void reduceForBottle() {
        if (canFillBottle()) {
            this.syrupAmount -= 1000;
            markDirty();

            if (!this.world.isClient()) {
                sync();
            }
        }
    }

    public int getSyrupAmount() {
        return syrupAmount;
    }

    @Override
    protected Text getContainerName() {
        return new LiteralText("Sap Distillery");
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.fromTag(tag, this.inventory);
        this.burnTime = tag.getShort("burn_time");
        this.heatAmount = tag.getShort("heat_amt");
        this.sapAmount = tag.getInt("sap_amt");
        this.syrupAmount = tag.getShort("syrup_amt");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag, this.inventory);
        tag.putShort("burn_time", (short) this.burnTime);
        tag.putShort("heat_amt", (short) this.heatAmount);
        tag.putInt("sap_amt", this.sapAmount);
        tag.putShort("syrup_amt", (short) this.syrupAmount);

        return tag;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new SapDistilleryScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.get(0).isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return slot >= 0 && slot < this.inventory.size() ? this.inventory.get(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot >= 0 && slot < this.inventory.size()) {
            this.inventory.set(slot, stack);
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 0) {
            return stack.getItem() == EcotonesItems.MAPLE_SAP;
        } else {
            return stack.isIn(ItemTags.LOGS_THAT_BURN);
        }
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        this.syrupAmount = tag.getShort("syrup");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        tag.putShort("syrup", (short) this.syrupAmount);
        return tag;
    }
}
