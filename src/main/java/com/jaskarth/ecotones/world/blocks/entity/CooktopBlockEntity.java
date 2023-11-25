package com.jaskarth.ecotones.world.blocks.entity;

import com.jaskarth.ecotones.client.particle.EcotonesParticles;
import com.jaskarth.ecotones.world.blocks.CooktopBlock;
import com.jaskarth.ecotones.world.blocks.entity.types.SmokeEmitter;
import com.jaskarth.ecotones.world.blocks.entity.types.HeatProducer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CooktopBlockEntity extends LockableContainerBlockEntity implements SmokeEmitter, HeatProducer {
    // [0, 2400]
    private int burnTime;
    // [0, 1000]
    private int heat;
    private DefaultedList<ItemStack> inventory;

    public CooktopBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EcotonesBlockEntities.COOKTOP, blockPos, blockState);

        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    public static void tick(World world, BlockPos pos, BlockState state, CooktopBlockEntity be) {
        ServerWorld level = (ServerWorld) world;

        // TODO: spawn on the client instead!
        if (world.getTime() % 10 == 0 || world.random.nextInt(3) == 0) {
            level.spawnParticles(EcotonesParticles.DIRECTED_SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0.2, 0.07, 0, 1);
        }

        if (be.getStack(0).getCount() > 0 && be.burnTime == 0) {
            be.burnTime = 2400;
        }

        if (be.burnTime > 0) {
            world.setBlockState(pos, state.with(CooktopBlock.LIT, true));
            be.burnTime--;

            if (be.heat < 1000) {
                be.heat++;
            }
        } else {
            world.setBlockState(pos, state.with(CooktopBlock.LIT, false));

            if (be.heat > 0) {
                be.heat--;
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
//        nbt.putInt("Dir", this.getCachedState());
        this.burnTime = nbt.getInt("BurnTime");
        this.heat = nbt.getInt("Heat");
        super.writeNbt(nbt);
    }

    @Override
    protected Text getContainerName() {
        return Text.literal("Wooden Cooktop");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemStack : this.inventory) {
            if (!itemStack.isEmpty()) {
                return false;
            }
        }

        return true;
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
        return stack.isIn(ItemTags.LOGS_THAT_BURN);
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public Direction smokeFacing() {
        return null;
    }

    @Override
    public boolean smokeEnabled() {
        return burnTime > 0;
    }

    @Override
    public double currentHeat() {
        return heat / 1000.0;
    }
}
