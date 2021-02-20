package supercoder79.ecotones.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import supercoder79.ecotones.entity.ai.WanderToWaterGoal;

public class DuckEntity extends ChickenEntity {
    public DuckEntity(EntityType<? extends DuckEntity> entityType, World world) {
        super(entityType, world);;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(4, new WanderToWaterGoal(this));
    }

    @Override
    public ChickenEntity createChild(ServerWorld world, PassiveEntity passiveEntity) {
        return EcotonesEntities.DUCK.create(world);
    }
}
