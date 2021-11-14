package supercoder79.ecotones.entity.ai.legacy;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import supercoder79.ecotones.util.AiLog;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class WanderToWaterGoal extends Goal {
    private final PathAwareEntity mob;
    protected double targetX;
    protected double targetY;
    protected double targetZ;
    protected int chillInWaterTicks;

    public WanderToWaterGoal(PathAwareEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public void stop() {
        AiLog.log(this.mob, "Finished wandering and staying in water");
        this.mob.getNavigation().stop();
        super.stop();
    }

    @Override
    public boolean shouldContinue() {
        return (!this.mob.getNavigation().isIdle() || this.chillInWaterTicks > 0) && !this.mob.hasPassengers();
    }

    @Override
    public void tick() {
        // Yes, this starts counting down before the duck reaches water. It seems to produce good results so i'm keeping it.
        this.chillInWaterTicks--;

        if (chillInWaterTicks < 0) {
            this.chillInWaterTicks = 0;
        }
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, 1.0);
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

        BlockPos target = locateWater(this.mob.world, 12, 6);
        if (target == null) {
            return false;
        }

        this.targetX = target.getX();
        this.targetY = target.getY();
        this.targetZ = target.getZ();
        this.chillInWaterTicks = this.mob.getRandom().nextInt(200) + 200;
        AiLog.log(this.mob, "Found water at [" + this.targetX + ", " + this.targetY + ", " + this.targetZ + "] and staying there for " + this.chillInWaterTicks + " ticks");

        return true;
    }

    @Nullable
    protected BlockPos locateWater(BlockView world, int rangeX, int rangeY) {
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
                    if (world.getFluidState(mutable).isIn(FluidTags.WATER)) {
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
