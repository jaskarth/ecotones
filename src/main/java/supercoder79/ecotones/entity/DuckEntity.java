package supercoder79.ecotones.entity;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.entity.ai.PickBerriesGoal;
import supercoder79.ecotones.entity.ai.RoostAtNestGoal;
import supercoder79.ecotones.entity.ai.WanderToWaterGoal;

public class DuckEntity extends ChickenEntity {
    private static final TrackedData<Boolean> ROOSTING = DataTracker.registerData(DuckEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private double food;
    private double energyPoints;
    private int eggLayingTicks;

    public DuckEntity(EntityType<? extends DuckEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ROOSTING, false);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        this.food = 25;
        resetEggTimer();

        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Override
    protected void mobTick() {
        super.mobTick();

        if (this.eggLayingTicks > 0) {
            this.eggLayingTicks--;
        }
    }

    public boolean shouldLayEgg() {
        return this.eggLayingTicks == 0 && this.energyPoints > 8.5;
    }

    public void resetEggTimer() {
        this.eggLayingTicks = this.random.nextInt(6000) + 6000;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(4, new WanderToWaterGoal(this));
        this.goalSelector.add(3, new RoostAtNestGoal(this));
        this.goalSelector.add(3, new PickBerriesGoal(this));
    }

    public void addFood(double food) {
        this.food += food;

        if (this.food > 50) {
            this.food = 50;
        }
    }

    public boolean exhaustFood(double food) {
        if (this.food > food) {
            this.food -= food;
            return true;
        } else {
            return false;
        }
    }

    public void addEnergyPoints(double energyPoints) {
        this.energyPoints += energyPoints;

        if (this.energyPoints > 50) {
            this.energyPoints = 50;
        }

    }

    public boolean useEnergyPoints(double energyPoints) {
        if (this.energyPoints > energyPoints) {
            this.energyPoints -= energyPoints;
            return true;
        } else {
            return false;
        }
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
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        setRoosting(tag.getBoolean("roosting"));
        this.food = tag.getDouble("food");
        this.energyPoints = tag.getDouble("energy_points");
        this.eggLayingTicks = tag.getInt("egg_laying_ticks");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putBoolean("roosting", isRoosting());
        tag.putDouble("food", this.food);
        tag.putDouble("energy_points", this.energyPoints);
        tag.putInt("egg_laying_ticks", this.eggLayingTicks);
    }
}
