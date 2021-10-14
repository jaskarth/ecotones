package supercoder79.ecotones.world.structure;

import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.util.RegistryReport;
import supercoder79.ecotones.world.structure.gen.CampfireStructureGenerator;
import supercoder79.ecotones.world.structure.gen.CottageGenerator;

public class EcotonesStructurePieces {
    public static final StructurePieceType CAMPFIRE = CampfireStructureGenerator.Piece::new;
    public static final StructurePieceType COTTAGE_CENTER = CottageGenerator.CenterRoom::new;
    public static final StructurePieceType COTTAGE_PORCH = CottageGenerator.Porch::new;
    public static final StructurePieceType COTTAGE_FARM = CottageGenerator.Farm::new;

    public static void init() {
        register("campfire", CAMPFIRE);
        register("cottage_center", COTTAGE_CENTER);
        register("cottage_porch", COTTAGE_PORCH);
        register("cottage_farm", COTTAGE_FARM);
    }

    private static void register(String name, StructurePieceType piece) {
        Registry.register(Registry.STRUCTURE_PIECE, Ecotones.id(name), piece);
        RegistryReport.increment("Structure Pieces");
    }
}
