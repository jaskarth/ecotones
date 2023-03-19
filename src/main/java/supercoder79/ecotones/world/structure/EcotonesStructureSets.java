package supercoder79.ecotones.world.structure;

import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.Structures;
import supercoder79.ecotones.Ecotones;

import java.util.List;

public final class EcotonesStructureSets {
    private static final RegistryEntry<StructureSet> CAMPFIRES = register(
            of("campfires"),
            new StructureSet(
                    List.of(
                            StructureSet.createEntry(EcotonesStructures.CAMPFIRE_OAK),
                            StructureSet.createEntry(EcotonesStructures.CAMPFIRE_BIRCH),
                            StructureSet.createEntry(EcotonesStructures.CAMPFIRE_SPRUCE),
                            StructureSet.createEntry(EcotonesStructures.CAMPFIRE_DARK_OAK)
                    ),
                    new RandomSpreadStructurePlacement(20, 4, SpreadType.LINEAR, 2492472)
            )
    );

    private static final RegistryEntry<StructureSet> COTTAGES = register(
            of("cottages"), EcotonesStructures.COTTAGE, new RandomSpreadStructurePlacement(24, 4, SpreadType.LINEAR, 14357617)
    );

    private static final RegistryEntry<StructureSet> OUTPOSTS = register(
            of("outposts"), EcotonesStructures.OUTPOST, new RandomSpreadStructurePlacement(10, 2, SpreadType.LINEAR, 14357617)
    );

    public static void init() {

    }

    private static RegistryKey<StructureSet> of(String id) {
        return RegistryKey.of(Registry.STRUCTURE_SET_KEY, Ecotones.id(id));
    }

    private static RegistryEntry<StructureSet> register(RegistryKey<StructureSet> key, StructureSet structureSet) {
        return BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_SET, key, structureSet);
    }

    private static RegistryEntry<StructureSet> register(RegistryKey<StructureSet> key, RegistryEntry<Structure> structure, StructurePlacement placement) {
        return register(key, new StructureSet(structure, placement));
    }
}
