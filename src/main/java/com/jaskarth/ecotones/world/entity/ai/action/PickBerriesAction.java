package com.jaskarth.ecotones.world.entity.ai.action;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.world.blocks.BlueberryBushBlock;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.entity.DuckEntity;
import com.jaskarth.ecotones.world.entity.ai.AiHelper;
import com.jaskarth.ecotones.world.entity.ai.system.Action;
import com.jaskarth.ecotones.world.entity.ai.system.AiState;
import com.jaskarth.ecotones.world.items.EcotonesItems;
import com.jaskarth.ecotones.util.AiLog;
import com.jaskarth.ecotones.world.worldgen.gen.EcotonesChunkGenerator;

import java.util.List;

public class PickBerriesAction extends Action {
    private final DuckEntity mob;
    private BlockPos bushPos;
    private boolean damagingBush;
    private boolean isAtBush = false;
    private int timeAtBush = 0;

    public PickBerriesAction(DuckEntity mob) {
        this.mob = mob;
    }

    @Override
    public boolean shouldStart(AiState state) {
        if (!this.mob.isOnGround()) {
            return false;
        }

        boolean canStart = this.mob.getRandom().nextDouble() < 0.005;
        if (!canStart) {
            return false;
        }

        BlockPos target = AiHelper.locateBlock(this.mob.getBlockPos(), this.mob.getWorld(), 12, 6, PickBerriesAction::isValidBerries);
        if (target == null) {
            return false;
        }

        this.bushPos = target;
        this.damagingBush = this.mob.getWorld().getBlockState(target).isOf(Blocks.SWEET_BERRY_BUSH);
        this.isAtBush = false;
        AiLog.log(this.mob, "Found berry bush at " + target + ". Moving there" + (this.damagingBush ? " and making sure to avoid it" : ""));

        return true;
    }

    private static int getBlueberryBerryCount(World world, BlockPos pos) {
        ChunkGenerator generator = ((ServerChunkManager) world.getChunkManager()).getChunkGenerator();
        if (generator instanceof EcotonesChunkGenerator) {
            double soilQuality = ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX(), pos.getZ());
            double soilPh = ((EcotonesChunkGenerator)generator).getSoilPhAt(pos.getX(), pos.getZ());
            int baseCount = BlueberryBushBlock.baseCount(soilQuality, soilPh);
            int randomCount = BlueberryBushBlock.randomCount(soilQuality, soilPh);

            return baseCount + world.random.nextInt(randomCount);
        } else {
            return 1 + world.random.nextInt(3);
        }
    }

    @Override
    public void start(AiState state) {
        this.mob.getNavigation().startMovingTo(this.bushPos.getX() + 0.25, this.bushPos.getY(), this.bushPos.getZ() + 0.25, 1.0);
    }

    @Override
    public void tick(AiState state) {
        if (!this.isAtBush) {
            if (this.mob.getPos().squaredDistanceTo(this.bushPos.getX(), this.bushPos.getY(), this.bushPos.getZ()) <= 2.5) {
                World world = this.mob.getWorld();
                if (this.damagingBush) {
                    int count = 1 + this.mob.getRandom().nextInt(2);
                    Block.dropStack(world, this.bushPos, new ItemStack(Items.SWEET_BERRIES, count));
                    world.setBlockState(this.bushPos, Blocks.SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE, 1), 2);
                    AiLog.log(this.mob, "Found " + count + " sweet berries.");
                } else {
                    int count = getBlueberryBerryCount(world, this.bushPos);
                    Block.dropStack(world, this.bushPos, new ItemStack(EcotonesItems.BLUEBERRIES, count));
                    world.setBlockState(this.bushPos, EcotonesBlocks.BLUEBERRY_BUSH.getDefaultState().with(BlueberryBushBlock.AGE, 2), 2);
                    AiLog.log(this.mob, "Found " + count + " blueberries.");
                }

                List<ItemEntity> items = world.getEntitiesByClass(ItemEntity.class, this.mob.getBoundingBox().expand(4.0), e -> isItemValidForPickup(e.getStack().getItem()));

                for (ItemEntity item : items) {
                    item.setDespawnImmediately();
                    state.getStomach().eat(item.getStack().getCount() * 2.5);
                }

                this.isAtBush = true;
            }
        } else {
            this.timeAtBush++;
            if (timeAtBush >= 30) {
                AiLog.log(this.mob, "Finished eating and moving on.");
            }
        }
    }

    @Override
    public boolean shouldContinue(AiState state) {
        return (!this.mob.getNavigation().isIdle() || this.timeAtBush < 30) && !this.mob.hasPassengers();
    }

    private static boolean isItemValidForPickup(Item item) {
        return item == Items.SWEET_BERRIES || item == EcotonesItems.BLUEBERRIES;
    }

    private static boolean isValidBerries(BlockState state) {
        if (state.isOf(Blocks.SWEET_BERRY_BUSH)) {
            if (state.get(SweetBerryBushBlock.AGE) >= 2) {
                return true;
            }
        } else if (state.isOf(EcotonesBlocks.BLUEBERRY_BUSH)) {
            if (state.get(BlueberryBushBlock.AGE) == 4) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Identifier key() {
        return Ecotones.id("pick_berries");
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public NbtCompound serialize(NbtCompound nbt) {
        if (this.bushPos != null) {
            nbt.put("BushPos", NbtHelper.fromBlockPos(this.bushPos));
        }
        nbt.putBoolean("Damaging", this.damagingBush);
        nbt.putInt("TimeAtBush", this.timeAtBush);
        nbt.putBoolean("IsAtBush", this.isAtBush);

        return nbt;
    }

    @Override
    public void deserialize(NbtCompound nbt) {
        if (nbt.contains("BushPos")) {
            this.bushPos = NbtHelper.toBlockPos(nbt.getCompound("BushPos"));
        }
        this.damagingBush = nbt.getBoolean("Damaging");
        this.timeAtBush = nbt.getInt("TimeAtBush");
        this.isAtBush = nbt.getBoolean("IsAtBush");
    }

    @Override
    public Control control() {
        return Control.MOVE;
    }
}
