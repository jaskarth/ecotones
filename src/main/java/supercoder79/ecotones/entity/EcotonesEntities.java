package supercoder79.ecotones.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EcotonesEntities {
    public static final EntityType<DuckEntity> DUCK = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DuckEntity::new)
            .dimensions(EntityDimensions.fixed(0.4F, 0.7F))
            .build();

    public static void init() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier("ecotones", "duck"), DUCK);

        initAttributes();
    }

    private static void initAttributes() {
        FabricDefaultAttributeRegistry.register(DUCK, DuckEntity.createChickenAttributes());
    }
}
