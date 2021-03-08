package supercoder79.ecotones.world.structure;

import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import supercoder79.ecotones.world.structure.gen.CampfireStructureGenerator;

public class EcotonesStructurePieces {
    public static final StructurePieceType CAMPFIRE = CampfireStructureGenerator.Piece::new;

    public static void init() {
        register("campfire", CAMPFIRE);
    }

    private static void register(String name, StructurePieceType piece) {
        Registry.register(Registry.STRUCTURE_PIECE, new Identifier("ecotones", name), piece);
    }
}
