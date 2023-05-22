package supercoder79.ecotones.entity.ai.action;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.blocks.NestBlock;
import supercoder79.ecotones.entity.DuckEntity;
import supercoder79.ecotones.entity.ai.AiHelper;
import supercoder79.ecotones.entity.ai.system.Action;
import supercoder79.ecotones.entity.ai.system.AiState;
import supercoder79.ecotones.util.AiLog;

public class RoostAtNestAction extends Action {
    private final DuckEntity mob;
    private BlockPos nestPos;
    protected int roostTicks;
    protected int halfTick;
    protected boolean checkedEgg = false;

    public RoostAtNestAction(DuckEntity mob) {
        this.mob = mob;
    }
    @Override
    public boolean shouldStart(AiState state) {
        if (!this.mob.isOnGround()) {
            return false;
        }

        // TODO: always roost at night

        boolean canStart = this.mob.getRandom().nextDouble() < 0.0025;
        if (!canStart) {
            return false;
        }

        AiLog.log(this.mob, "Attempting find nest");
        BlockPos nestPos = AiHelper.locateBlock(this.mob.getBlockPos(), this.mob.getWorld(), 16, 6, s -> s.isOf(EcotonesBlocks.NEST));
        if (nestPos == null) {
            return false;
        }

        this.nestPos = nestPos;
        this.roostTicks = 600 + this.mob.getRandom().nextInt(400);
        this.halfTick = this.roostTicks / 2;
        this.checkedEgg = false;
        AiLog.log(this.mob, "Found nest at " + nestPos + ". Moving there and staying for " + this.roostTicks + " ticks");

        return true;
    }

    @Override
    public void start(AiState state) {
        this.mob.getNavigation().startMovingTo(this.nestPos.getX() + 0.25, this.nestPos.getY(), this.nestPos.getZ() + 0.25, 1.0);

        state.getStomach().setMetabolismRate(0.002);
    }

    @Override
    public void stop(AiState state) {
        AiLog.log(this.mob, "Finished roost");
        this.mob.getNavigation().stop();
        this.mob.setRoosting(false);
    }

    @Override
    public void tick(AiState state) {
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
            state.getStomach().setMetabolismRate(0.002);

            if (this.roostTicks < this.halfTick && this.mob.shouldLayEgg() && !this.checkedEgg) {
                BlockState bstate = this.mob.getWorld().getBlockState(this.nestPos);

                if (bstate.isOf(EcotonesBlocks.NEST) && bstate.get(NestBlock.EGGS) < 3 && state.getStomach().exhaust(5.0)) {
                    int newEggCount = bstate.get(NestBlock.EGGS) + 1;
                    this.mob.getWorld().setBlockState(this.nestPos, bstate.with(NestBlock.EGGS, newEggCount));

                    this.mob.resetEggTimer();
                    AiLog.log(this.mob, "Laying egg at " + this.nestPos + " which now has " + newEggCount + " eggs in it");
                }

                this.checkedEgg = true;
            }
        }
    }

    @Override
    public boolean shouldContinue(AiState state) {
        return (!this.mob.getNavigation().isIdle() || this.roostTicks > 0) && !this.mob.hasPassengers();
    }

    @Override
    public Identifier key() {
        return Ecotones.id("roost_at_nest");
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public NbtCompound serialize(NbtCompound nbt) {
        if (this.nestPos != null) {
            nbt.put("NestPos", NbtHelper.fromBlockPos(this.nestPos));
        }
        nbt.putInt("RoostTicks", this.roostTicks);
        nbt.putInt("HalfTick", this.halfTick);
        nbt.putBoolean("CheckedEgg", this.checkedEgg);

        return nbt;
    }

    @Override
    public void deserialize(NbtCompound nbt) {
        if (nbt.contains("NestPos")) {
            this.nestPos = NbtHelper.toBlockPos(nbt.getCompound("NestPos"));
        }

        this.roostTicks = nbt.getInt("RoostTicks");
        this.halfTick = nbt.getInt("HalfTick");
        this.checkedEgg = nbt.getBoolean("CheckedEgg");
    }

    @Override
    public Control control() {
        return Control.MOVE;
    }
}
