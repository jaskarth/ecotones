package supercoder79.ecotones.blocks.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.blocks.ExcursionFunnelBlock;
import supercoder79.ecotones.blocks.GrindstoneBlock;
import supercoder79.ecotones.util.RegistryReport;

public final class EcotonesBlockEntities {
    public static final BlockEntityType<SapDistilleryBlockEntity> SAP_DISTILLERY = FabricBlockEntityTypeBuilder.create(SapDistilleryBlockEntity::new, EcotonesBlocks.SAP_DISTILLERY).build();
    public static final BlockEntityType<TreetapBlockEntity> TREETAP = FabricBlockEntityTypeBuilder.create(TreetapBlockEntity::new, EcotonesBlocks.TREETAP).build();
    public static final BlockEntityType<FertilizerSpreaderBlockEntity> FERTILIZER_SPREADER = FabricBlockEntityTypeBuilder.create(FertilizerSpreaderBlockEntity::new, EcotonesBlocks.FERTILIZER_SPREADER).build();
    public static final BlockEntityType<ExcursionFunnelBlockEntity> EXCURSION_FUNNEL = FabricBlockEntityTypeBuilder.create(ExcursionFunnelBlockEntity::new, EcotonesBlocks.EXCURSION_FUNNEL).build();
    public static final BlockEntityType<GrindstoneBlockEntity> GRINDSTONE = FabricBlockEntityTypeBuilder.create(GrindstoneBlockEntity::new, EcotonesBlocks.GRINDSTONE).build();

    public static void init() {
        register("sap_distillery", SAP_DISTILLERY);
        register("treetap", TREETAP);
        register("fertilizer_spreader", FERTILIZER_SPREADER);
        register("steady_geyser", EXCURSION_FUNNEL);
        register("grindstone", GRINDSTONE);
    }

    private static void register(String path, BlockEntityType<?> be) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Ecotones.id(path), be);
        RegistryReport.increment("Block Entity");
    }
}
