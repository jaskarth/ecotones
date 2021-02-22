package supercoder79.ecotones.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import supercoder79.ecotones.entity.ai.RoostAtNestGoal;
import supercoder79.ecotones.entity.ai.WanderToWaterGoal;

public class DuckEntity extends ChickenEntity {
    private static final TrackedData<Boolean> ROOSTING = DataTracker.registerData(DuckEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


    public DuckEntity(EntityType<? extends DuckEntity> entityType, World world) {
        super(entityType, world);;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ROOSTING, false);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(4, new WanderToWaterGoal(this));
        this.goalSelector.add(3, new RoostAtNestGoal(this));
    }

    @Override
    public ChickenEntity createChild(ServerWorld world, PassiveEntity passiveEntity) {
        return EcotonesEntities.DUCK.create(world);
    }

    // TODO: roosting gets stuck when reloading world

    public boolean isRoosting() {
        return this.dataTracker.get(ROOSTING);
    }

    public void setRoosting(boolean roosting) {
        this.dataTracker.set(ROOSTING, roosting);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        setRoosting(tag.getBoolean("roosting"));
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putBoolean("roosting", isRoosting());
    }
}
