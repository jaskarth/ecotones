package supercoder79.ecotones.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.blocks.NestBlock;
import supercoder79.ecotones.entity.DuckEntity;
import supercoder79.ecotones.util.AiLog;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class RoostAtNestGoal extends Goal {
    private final DuckEntity mob;
    private BlockPos nestPos;
    protected int roostTicks;
    protected int halfTick;
    protected boolean checkedEgg = false;

    public RoostAtNestGoal(DuckEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public void stop() {
        AiLog.log(this.mob, "Finished roost");
        this.mob.getNavigation().stop();
        this.mob.setRoosting(false);
        super.stop();
    }

    @Override
    public void tick() {
        this.roostTicks--;

        if (roostTicks < 0) {
            this.roostTicks = 0;
        }

        if (!this.mob.isRoosting()) {
            if (this.mob.getBlockPos().getSquaredDistance(this.nestPos) <= 1) {
                this.mob.setRoosting(true);
            }
        } else {
            // Digest 2 food across the 1000 ticks
            if (this.mob.exhaustFood(0.002)) {
                this.mob.addEnergyPoints(0.002);
            }

            if (this.roostTicks < this.halfTick && this.mob.shouldLayEgg() && !this.checkedEgg) {
                BlockState state = this.mob.world.getBlockState(this.nestPos);

                if (state.isOf(EcotonesBlocks.NEST) && state.get(NestBlock.EGGS) < 3) {
                    int newEggCount = state.get(NestBlock.EGGS) + 1;
                    this.mob.world.setBlockState(this.nestPos, state.with(NestBlock.EGGS, newEggCount));
                    this.mob.useEnergyPoints(5.0);
                    this.mob.resetEggTimer();
                    AiLog.log(this.mob, "Laying egg at " + this.nestPos + " which now has " + newEggCount + " eggs in it");
                }

                this.checkedEgg = true;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return (!this.mob.getNavigation().isIdle() || this.roostTicks > 0) && !this.mob.hasPassengers();
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingTo(this.nestPos.getX() + 0.25, this.nestPos.getY(), this.nestPos.getZ() + 0.25, 1.0);
    }

    @Override
    public boolean canStart() {
        if (!this.mob.isOnGround()) {
            return false;
        }

        // TODO: always roost at night

        boolean canStart = this.mob.getRandom().nextDouble() < 0.0025;
        if (!canStart) {
            return false;
        }

        AiLog.log(this.mob, "Attempting find nest");
        BlockPos nestPos = locateNest(this.mob.world, 16, 6);
        if (nestPos == null) {
            return false;
        }

        this.nestPos = nestPos;
        this.roostTicks = 600 + this.mob.getRandom().nextInt(400);
        this.halfTick = this.roostTicks / 2;
        this.checkedEgg = false;
        AiLog.log(this.mob, "Found nest at " + nestPos.toString() + ". Moving there and staying for " + this.roostTicks + " ticks");

        return true;
    }

    @Nullable
    protected BlockPos locateNest(BlockView world, int rangeX, int rangeY) {
        BlockPos blockPos = mob.getBlockPos();
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
                    if (world.getBlockState(mutable).isOf(EcotonesBlocks.NEST)) {
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
}
