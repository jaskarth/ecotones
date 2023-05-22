package supercoder79.ecotones.entity.ai.action;

import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.entity.ai.AiHelper;
import supercoder79.ecotones.entity.ai.system.Action;
import supercoder79.ecotones.entity.ai.system.AiState;
import supercoder79.ecotones.util.AiLog;

import javax.annotation.Nullable;

public class WanderToWaterAction extends Action {
    private final PathAwareEntity mob;
    protected double targetX;
    protected double targetY;
    protected double targetZ;
    protected int chillInWaterTicks;

    public WanderToWaterAction(PathAwareEntity mob) {
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

        BlockPos target = AiHelper.locateBlock(this.mob.getBlockPos(), this.mob.getWorld(), 12, 6, s -> s.getFluidState().isIn(FluidTags.WATER));
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

    @Override
    public void start(AiState state) {
        this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, 1.0);
    }

    @Override
    public boolean shouldContinue(AiState state) {
        return (!this.mob.getNavigation().isIdle() || this.chillInWaterTicks > 0) && !this.mob.hasPassengers();
    }

    @Override
    public void stop(AiState state) {
        AiLog.log(this.mob, "Finished wandering and staying in water");
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick(AiState state) {
        this.chillInWaterTicks--;

        if (chillInWaterTicks < 0) {
            this.chillInWaterTicks = 0;
        }
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

    @Override
    public Identifier key() {
        return Ecotones.id("wander_to_water");
    }

    @Override
    public int priority() {
        return 4;
    }

    @Override
    public NbtCompound serialize(NbtCompound nbt) {
        nbt.putDouble("TargetX", this.targetX);
        nbt.putDouble("TargetY", this.targetY);
        nbt.putDouble("TargetZ", this.targetZ);
        nbt.putInt("CIWT", this.chillInWaterTicks);

        return nbt;
    }

    @Override
    public void deserialize(NbtCompound nbt) {
        this.targetX = nbt.getDouble("TargetX");
        this.targetY = nbt.getDouble("TargetY");
        this.targetZ = nbt.getDouble("TargetZ");
        this.chillInWaterTicks = nbt.getInt("CIWT");
    }

    @Override
    public Control control() {
        return Control.MOVE;
    }
}
