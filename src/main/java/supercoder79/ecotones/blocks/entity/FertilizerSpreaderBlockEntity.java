package supercoder79.ecotones.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.blocks.FertilizerSpreaderBlock;
import supercoder79.ecotones.items.EcotonesItems;
import supercoder79.ecotones.screen.FertilizerSpreaderScreenHandler;

import java.util.HashSet;
import java.util.Set;

public class FertilizerSpreaderBlockEntity extends LockableContainerBlockEntity {
    private static final Direction[] HORIZONTAL = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    protected final PropertyDelegate propertyDelegate;

    // serialized
    private DefaultedList<ItemStack> inventory;
    // 0 .. 100
    private int percentDissolved;
    // 0 .. 20000 (20 units)
    private int dissolvedAmount;
    // 0 .. 20000 (20 units)
    private int fertilizerAmount;

    // transient
    private boolean needsValidation = true;
    private boolean valid = false;
    // 0: Invalid
    // 1: Working
    // 2: Needs water
    // 3: Waiting
    // 4: needs plants
    private int status = 0;
    private final long timeOffset = (long) (Math.random() * 300);

    // calculated on demand
    // synced but not serialized
    private Set<BlockPos> water = new HashSet<>();
    private Set<BlockPos> farmland = new HashSet<>();



    public FertilizerSpreaderBlockEntity(BlockPos pos, BlockState state) {
        super(EcotonesBlockEntities.FERTILIZER_SPREADER, pos, state);

        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> FertilizerSpreaderBlockEntity.this.percentDissolved;
                    case 1 -> FertilizerSpreaderBlockEntity.this.fertilizerAmount;
                    case 2 -> FertilizerSpreaderBlockEntity.this.farmland.size();
                    case 3 -> FertilizerSpreaderBlockEntity.this.water.size();
                    case 4 -> FertilizerSpreaderBlockEntity.this.status;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FertilizerSpreaderBlockEntity.this.percentDissolved = value;
                    case 1 -> FertilizerSpreaderBlockEntity.this.fertilizerAmount = value;
                }

            }

            public int size() {
                return 5;
            }
        };
    }

    public static void tick(World world, BlockPos pos, BlockState state, FertilizerSpreaderBlockEntity blockEntity) {
        if (blockEntity.needsValidation) {
            // validate and calculate environment

            blockEntity.validate(world, pos, state);
            blockEntity.sync();

            blockEntity.needsValidation = false;
        }

        long time = world.getTime() + blockEntity.timeOffset;

        if (blockEntity.valid) {
            if (blockEntity.fertilizerAmount >= 8 && blockEntity.dissolvedAmount < 20000) {
                blockEntity.fertilizerAmount -= 8;

                // \frac{600}{x+60}-\frac{x}{50}
                // Makes it harder to dissolve more into the surrounding water
                blockEntity.dissolvedAmount += (int) ((600.0 / (blockEntity.percentDissolved + 60)) - (blockEntity.percentDissolved / 50.0));
            }

            blockEntity.percentDissolved = (int) ((blockEntity.dissolvedAmount / 20000.0) * 100.0);

            if (time % 20 == 0) {
                if (blockEntity.getStack(0).getCount() > 0 && blockEntity.fertilizerAmount + 1000 <= 20000) {
                    blockEntity.getStack(0).decrement(1);

                    blockEntity.fertilizerAmount += 1000;
                }
            }

            if (time % 160 == 0) {
                if (blockEntity.percentDissolved > 0) {
                    blockEntity.fertilize(world);
                }
            }

            if (blockEntity.dissolvedAmount >= 120) {
                if (blockEntity.world.random.nextInt(4) == 0) {
                    blockEntity.dissolvedAmount -= 1;
                }
            }

            blockEntity.markDirty();
        }

        blockEntity.percentDissolved = (int) ((blockEntity.dissolvedAmount / 20000.0) * 100.0);

        // Recheck position every 5 seconds
        if (time % 100 == 0) {
            blockEntity.needsValidation = true;
        }
    }

    private void validate(World world, BlockPos pos, BlockState state) {
        if (state.get(FertilizerSpreaderBlock.WATERLOGGED)) {
            Set<BlockPos> water = new HashSet<>();
            Set<BlockPos> farmland = new HashSet<>();

            dfs(world, pos, water, farmland, 0);

            this.water = water;
            this.farmland = farmland;

            this.status = 1;

            if (this.farmland.isEmpty()) {
                this.status = 4;
            }

            if (this.dissolvedAmount == 0 && this.fertilizerAmount == 0) {
                this.status = 3;
            }

            // Debugging!
//            for (BlockPos p : this.farmland) {
//                world.setBlockState(p, Blocks.MAGENTA_TERRACOTTA.getDefaultState());
//            }

            this.valid = true;
        } else {
            // No water
            this.status = 2;
            this.valid = false;
        }
    }

    private void fertilize(World world) {
        // \frac{500}{x+1}-\frac{x}{20}+3
        // Fertilization chance curve, makes higher values less effective as more is added
        int chance = (int)((500.0 / (this.percentDissolved + 1)) + (this.percentDissolved / 20.0) + 3);
        // TODO: make it so that more dissolved can let more blocks be fertilized per tick?

        Random random = world.random;

        // FIXME: biased to start!
        for (BlockPos pos : this.farmland) {
            if (world.getBlockState(pos).isOf(Blocks.FARMLAND)) {
                if (random.nextInt(chance) == 0) {
                    BlockState state = world.getBlockState(pos.up());

                    if (state.getBlock() instanceof CropBlock crop) {
                        crop.grow((ServerWorld) world, random, pos.up(), state);

                        // Green particles
                        world.syncWorldEvent(WorldEvents.PLANT_FERTILIZED, pos.up(), 20);
                    }

                    this.dissolvedAmount -= 120;

                    break;
                }
            } else {
                // Invalid! Recheck
                this.needsValidation = true;
            }
        }
    }

    private void dfs(World world, BlockPos pos, Set<BlockPos> water, Set<BlockPos> farmland, int depth) {
        for (Direction dir : HORIZONTAL) {
            BlockPos local = pos.offset(dir);
            BlockState state = world.getBlockState(local);

            boolean search = false;
            if (state.isOf(Blocks.WATER)) {
                water.add(local);

                search = true;
            } else if (state.isOf(Blocks.FARMLAND)) {
                // Don't propagate farmland
                farmland.add(local);
            } else if (state.isOf(EcotonesBlocks.FERTILIZER_SPREADER)) {
                // TODO: reduce effectiveness
            }

            if (depth > 5) {
                continue;
            }

            if (search) {
                dfs(world, local, water, farmland, depth + 1);
            }
        }
    }

    public Set<BlockPos> getWater() {
        return water;
    }

    public Set<BlockPos> getFarmland() {
        return farmland;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        Inventories.readNbt(nbt, this.inventory);

        this.percentDissolved = nbt.getInt("percent_dissolved");
        this.dissolvedAmount = nbt.getInt("dissolved_amount");
        this.fertilizerAmount = nbt.getInt("fertilizer_amount");

        fromClientTag(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        Inventories.writeNbt(nbt, this.inventory);

        nbt.putInt("percent_dissolved", this.percentDissolved);
        nbt.putInt("dissolved_amount", this.dissolvedAmount);
        nbt.putInt("fertilizer_amount", this.fertilizerAmount);

//        return nbt;
    }

    public void fromClientTag(NbtCompound tag) {
        // Debug

        NbtList water = tag.getList("water", NbtElement.COMPOUND_TYPE);
        NbtList farmland = tag.getList("farmland", NbtElement.COMPOUND_TYPE);

        Set<BlockPos> wList = new HashSet<>();
        for (NbtElement el : water) {
            wList.add(NbtHelper.toBlockPos((NbtCompound) el));
        }

        Set<BlockPos> fList = new HashSet<>();
        for (NbtElement el : farmland) {
            fList.add(NbtHelper.toBlockPos((NbtCompound) el));
        }

        this.water = wList;
        this.farmland = fList;
    }

    public NbtCompound toClientTag(NbtCompound tag) {
        // Debug
        NbtList water = new NbtList();
        NbtList farmland = new NbtList();

        for (BlockPos pos : this.water) {
            water.add(NbtHelper.fromBlockPos(pos));
        }

        for (BlockPos pos : this.farmland) {
            farmland.add(NbtHelper.fromBlockPos(pos));
        }

        tag.put("water", water);
        tag.put("farmland", farmland);

        return tag;
    }

    private void sync() {
        ((ServerWorld)this.world).getChunkManager().markForUpdate(this.pos);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return toClientTag(new NbtCompound());
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    protected Text getContainerName() {
        return Text.literal("Fertilizer Spreader");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FertilizerSpreaderScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public int size() {
        return 1;
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
        return stack.getItem() == EcotonesItems.BASIC_FERTILIZER;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }
}
