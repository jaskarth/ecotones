package supercoder79.ecotones.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.items.EcotonesItems;
import supercoder79.ecotones.recipe.EcotonesGrindingRecipes;
import supercoder79.ecotones.recipe.GrindingRecipe;
import supercoder79.ecotones.screen.GrindstoneScreenHandler;

public class GrindstoneBlockEntity extends LockableContainerBlockEntity {
    private DefaultedList<ItemStack> inventory;
    protected final PropertyDelegate propertyDelegate;
    // 0 .. burnTimeMax
    private int burnTime;

    // --
    private int burnTimeMax;

    // 0 .. 200
    private int progress;
    private int mainOutPercent;
    private int altOutPercent;

    @Nullable
    private Identifier recipeId;

    public GrindstoneBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EcotonesBlockEntities.GRINDSTONE, blockPos, blockState);


        // 0 -> fuel slot
        // 1 -> input slot
        // 2 -> jar input slot
        // 3 -> output slot
        // 4 -> extra output slot
        this.inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);

        this.propertyDelegate = new PropertyDelegate() {

            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> burnTime;
                    case 1 -> burnTimeMax;
                    case 2 -> progress;
                    case 3 -> mainOutPercent;
                    case 4 -> altOutPercent;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> burnTime = value;
                    case 1 -> burnTimeMax = value;
                    case 2 -> progress = value;
                    case 3 -> mainOutPercent = value;
                    case 4 -> altOutPercent = value;
                }
            }

            @Override
            public int size() {
                return 5;
            }
        };
    }


    public static void tick(World world, BlockPos pos, BlockState state, GrindstoneBlockEntity blockEntity) {
        ItemStack fuel = blockEntity.inventory.get(0);
        ItemStack input = blockEntity.inventory.get(1);
        ItemStack jarInput = blockEntity.inventory.get(2);
        ItemStack mainOutput = blockEntity.inventory.get(3);
        ItemStack altOutput = blockEntity.inventory.get(4);

        if (blockEntity.recipeId == null) {
            blockEntity.progress = 0;
            if (!input.isEmpty()) {
                blockEntity.recipeId = EcotonesGrindingRecipes.findRecipeByInput(input.getItem());
            }
        }

        if (blockEntity.recipeId != null) {
            GrindingRecipe recipe = EcotonesGrindingRecipes.RECIPES.get(blockEntity.recipeId);

            if (input.getItem() != recipe.in().getItem() || input.getCount() < recipe.in().getCount()) {
                blockEntity.recipeId = null;
            }

            if (recipe.needsBottle() && jarInput.isEmpty()) {
                blockEntity.recipeId = null;
            }

            if ((!mainOutput.isEmpty() && !mainOutput.isOf(recipe.mainOutput().getItem())) || mainOutput.getCount() + recipe.mainOutput().getCount() > recipe.mainOutput().getMaxCount()) {
                blockEntity.recipeId = null;
            }

            if (recipe.secondaryOutput() != null) {
                if ((!altOutput.isEmpty() && !altOutput.isOf(recipe.secondaryOutput().getItem())) || altOutput.getCount() + recipe.secondaryOutput().getCount() > recipe.secondaryOutput().getMaxCount()) {
                    blockEntity.recipeId = null;
                }
            }

            if (blockEntity.recipeId != null) {
                if (blockEntity.burnTime == 0) {
                    if (!fuel.isEmpty()) {
                        blockEntity.burnTimeMax = blockEntity.burnTime = AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(fuel.getItem(), 0);

                        fuel.decrement(1);

                        if (fuel.isEmpty()) {
                            blockEntity.inventory.set(0, ItemStack.EMPTY);
                        }
                    }
                }
            }
        }

        blockEntity.mainOutPercent = -1;
        blockEntity.altOutPercent = -1;

        if (blockEntity.burnTime > 0) {
            blockEntity.burnTime--;

            if (blockEntity.recipeId != null) {
                blockEntity.progress++;
                GrindingRecipe recipe = EcotonesGrindingRecipes.RECIPES.get(blockEntity.recipeId);

                blockEntity.mainOutPercent = (int) Math.round(recipe.mainChance() * 100);
                blockEntity.altOutPercent = (int) Math.round(recipe.secondaryChance() * 100);
            }
        } else {
            blockEntity.progress = 0;
        }

        if (blockEntity.recipeId != null && blockEntity.progress >= 200) {
            GrindingRecipe recipe = EcotonesGrindingRecipes.RECIPES.get(blockEntity.recipeId);

            if (recipe.needsBottle()) {
                jarInput.decrement(1);

                if (jarInput.isEmpty()) {
                    blockEntity.inventory.set(2, ItemStack.EMPTY);
                }
            }

            input.decrement(recipe.in().getCount());

            if (input.isEmpty()) {
                blockEntity.inventory.set(1, ItemStack.EMPTY);
            }

            if (blockEntity.world.random.nextDouble() < recipe.mainChance()) {
                if (mainOutput.isEmpty()) {
                    blockEntity.inventory.set(3, recipe.mainOutput().copy());
                } else {
                    mainOutput.increment(recipe.mainOutput().getCount());
                }
            }

            if (recipe.secondaryOutput() != null && blockEntity.world.random.nextDouble() < recipe.secondaryChance()) {
                if (altOutput.isEmpty()) {
                    blockEntity.inventory.set(4, recipe.secondaryOutput().copy());
                } else {
                    altOutput.increment(recipe.secondaryOutput().getCount());
                }
            }

            // Reset
            blockEntity.progress = 0;
            blockEntity.recipeId = null;
        }

        blockEntity.markDirty();
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);

        Inventories.readNbt(tag, this.inventory);
        this.burnTime = tag.getInt("BurnTime");
        this.burnTimeMax = tag.getInt("BurnTimeMax");
        this.mainOutPercent = tag.getShort("MainOutPercent");
        this.altOutPercent = tag.getShort("AltOutPercent");
        this.progress = tag.getShort("Progress");

        String id = tag.getString("RecipeId");
        this.recipeId = id.equals("nil") ? null : new Identifier(id);
    }

    @Override
    protected void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);

        Inventories.writeNbt(tag, this.inventory);
        tag.putInt("BurnTime", this.burnTime);
        tag.putInt("BurnTimeMax", this.burnTimeMax);
        tag.putShort("MainOutPercent", (short) this.mainOutPercent);
        tag.putShort("AltOutPercent", (short) this.altOutPercent);
        tag.putShort("Progress", (short) this.progress);

        tag.putString("RecipeId", recipeId == null ? "nil" : recipeId.toString());
    }

    @Override
    protected Text getContainerName() {
        return Text.literal("Grindstone");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new GrindstoneScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.inventory) {
            if (!stack.isEmpty()) {
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
        if (slot == 0) {
            return AbstractFurnaceBlockEntity.canUseAsFuel(stack);
        } else if (slot == 1) {
            return true;
        } else if (slot == 2) {
            return stack.getItem() == EcotonesItems.JAR;
        }

        // output slots
        return false;
    }

    @Override
    public void clear() {

    }
}
