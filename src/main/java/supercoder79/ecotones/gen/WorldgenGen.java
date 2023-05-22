package supercoder79.ecotones.gen;

import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryOps;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.Identifier;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import supercoder79.ecotones.util.register.EarlyRegistrationState;
import supercoder79.ecotones.world.biome.EarlyBiomeRegistry;
import supercoder79.ecotones.world.structure.StructureSetHelpers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorldgenGen {
    private static final Path PATH = Paths.get("..", "src/main/resources/data/ecotones/worldgen");
    public static void genBiomes() {
        PATH.resolve("biome").toFile().mkdirs();
        for (Identifier id : EarlyBiomeRegistry.ids()) {
            Path jsonPath = PATH.resolve("biome").resolve(id.getPath() + ".json");
            DynamicOps<JsonElement> dynamicOps = RegistryOps.of(JsonOps.INSTANCE, EarlyRegistrationState.globalBuiltins);
            Biome.CODEC.encodeStart(dynamicOps, EarlyBiomeRegistry.get(id)).resultOrPartial(System.err::println).ifPresent((json) -> {
                try {
                    Files.writeString(jsonPath, json.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void genStructures() {
        PATH.resolve("structure").toFile().mkdirs();
        for (Identifier id : EarlyRegistrationState.STRUCTURES.keySet()) {
            Path jsonPath = PATH.resolve("structure").resolve(id.getPath() + ".json");
            DynamicOps<JsonElement> dynamicOps = RegistryOps.of(JsonOps.INSTANCE, EarlyRegistrationState.globalBuiltins);
            Structure.STRUCTURE_CODEC.encodeStart(dynamicOps, EarlyRegistrationState.STRUCTURES.get(id)).resultOrPartial(System.err::println).ifPresent((json) -> {
                try {
                    Files.writeString(jsonPath, json.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void genStructureSets() {
        PATH.resolve("structure_set").toFile().mkdirs();
        for (Identifier id : EarlyRegistrationState.STRUCTURE_SETS.keySet()) {
            Path jsonPath = PATH.resolve("structure_set").resolve(id.getPath() + ".json");
            DynamicOps<JsonElement> dynamicOps = RegistryOps.of(JsonOps.INSTANCE, EarlyRegistrationState.globalBuiltins);
            StructureSetHelpers.CODEC.encodeStart(dynamicOps, EarlyRegistrationState.STRUCTURE_SETS.get(id)).resultOrPartial(System.err::println).ifPresent((json) -> {
                try {
                    Files.writeString(jsonPath, json.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
