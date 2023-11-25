package com.jaskarth.ecotones.world.blocks.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.util.RegistryReport;

public final class EcotonesBlockEntities {
    public static final BlockEntityType<SapDistilleryBlockEntity> SAP_DISTILLERY = FabricBlockEntityTypeBuilder.create(SapDistilleryBlockEntity::new, EcotonesBlocks.SAP_DISTILLERY).build();
    public static final BlockEntityType<TreetapBlockEntity> TREETAP = FabricBlockEntityTypeBuilder.create(TreetapBlockEntity::new, EcotonesBlocks.TREETAP).build();
    public static final BlockEntityType<FertilizerSpreaderBlockEntity> FERTILIZER_SPREADER = FabricBlockEntityTypeBuilder.create(FertilizerSpreaderBlockEntity::new, EcotonesBlocks.FERTILIZER_SPREADER).build();
    public static final BlockEntityType<GrindstoneBlockEntity> GRINDSTONE = FabricBlockEntityTypeBuilder.create(GrindstoneBlockEntity::new, EcotonesBlocks.GRINDSTONE).build();
    public static final BlockEntityType<CooktopBlockEntity> COOKTOP = FabricBlockEntityTypeBuilder.create(CooktopBlockEntity::new, EcotonesBlocks.BRICK_COOKTOP).build();

    public static void init() {
        register("sap_distillery", SAP_DISTILLERY);
        register("treetap", TREETAP);
        register("fertilizer_spreader", FERTILIZER_SPREADER);
        register("grindstone", GRINDSTONE);
        register("cooktop", COOKTOP);
    }

    private static void register(String path, BlockEntityType<?> be) {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, Ecotones.id(path), be);
        RegistryReport.increment("Block Entity");
    }
}
