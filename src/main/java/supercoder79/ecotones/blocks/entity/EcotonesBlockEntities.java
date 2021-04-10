package supercoder79.ecotones.blocks.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.util.RegistryReport;

public final class EcotonesBlockEntities {
    public static final BlockEntityType<SapDistilleryBlockEntity> SAP_DISTILLERY = FabricBlockEntityTypeBuilder.create(SapDistilleryBlockEntity::new, EcotonesBlocks.SAP_DISTILLERY).build();

    public static void init() {
        register("sap_distillery", SAP_DISTILLERY);
    }

    private static void register(String path, BlockEntityType<?> be) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Ecotones.id(path), be);
        RegistryReport.increment("Block Entity");
    }
}
