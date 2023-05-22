package supercoder79.ecotones.entity.ai.legacy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import supercoder79.ecotones.blocks.BlueberryBushBlock;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.entity.DuckEntity;
import supercoder79.ecotones.items.EcotonesItems;
import supercoder79.ecotones.util.AiLog;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class PickBerriesGoal extends Goal {
    private final DuckEntity mob;
    private BlockPos bushPos;
    private boolean damagingBush;
    private boolean isAtBush = false;
    private int timeAtBush = 0;

    public PickBerriesGoal(DuckEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean shouldContinue() {
        return (!this.mob.getNavigation().isIdle() || this.timeAtBush < 30) && !this.mob.hasPassengers();
    }

    @Override
    public void tick() {
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
                    this.mob.addFood(item.getStack().getCount() * 2.5);
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
    public void start() {
        this.mob.getNavigation().startMovingTo(this.bushPos.getX() + 0.25, this.bushPos.getY(), this.bushPos.getZ() + 0.25, 1.0);
    }

    @Override
    public boolean canStart() {
        if (!this.mob.isOnGround()) {
            return false;
        }

        boolean canStart = this.mob.getRandom().nextDouble() < 0.005;
        if (!canStart) {
            return false;
        }

        BlockPos target = locateBerries(this.mob.getWorld(), 12, 6);
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

    @Nullable
    protected BlockPos locateBerries(BlockView world, int rangeX, int rangeY) {
        BlockPos blockPos = this.mob.getBlockPos();
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        int minimumDist = Integer.MAX_VALUE;
        BlockPos minPos = null;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int dx = x - rangeX; dx <= x + rangeX; ++dx) {
            for(int dy = y - rangeY; dy <= y + rangeY; ++dy) {
                for(int dz = z - rangeX; dz <= z + rangeX; ++dz) {
                    mutable.set(dx, dy, dz);
                    if (isValidBerries(world.getBlockState(mutable))) {
                        int ax = dx - x;
                        int ay = dy - y;
                        int az = dz - z;

                        int dist = ax * ax + ay * ay + az * az;
                        if (dist < minimumDist) {
                            minimumDist = dist;
                            minPos = mutable.toImmutable();
                        }
                    }
                }
            }
        }

        return minPos;
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
}
