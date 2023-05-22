package supercoder79.ecotones.gen;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.util.register.EarlyRegistrationState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class TagGen {
    private static final Path PATH = Paths.get("..", "src/main/resources/data/");

    private static final String TAG = """
            {
              "replace": false,
              "values": [
                %%VALUES%%
              ]
            }
            """;

    public static void automakeStructureHas() throws IOException {
        for (RegistryEntry<Structure> k : BiomeRegistries.STRUCTURE_TO_BIOME_LIST.keySet()) {

            String json = TAG
                    .replace("%%VALUES%%", BiomeRegistries.STRUCTURE_TO_BIOME_LIST.get(k).stream()
                            .map(b -> BiomeRegistries.keyOrNull(b).getValue().toString())
                            .map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(",\n    "))

                    );

            Path path = PATH
                    .resolve("ecotones")
                    .resolve("tags")
                    .resolve("worldgen")
                    .resolve("biome");

            path.toFile().mkdirs();
            Files.write(path.resolve("has_" + k.getKey()
                    .orElse(RegistryKey.of(RegistryKeys.STRUCTURE, EarlyRegistrationState.STRUCTURES.inverse().get(k.value()))).getValue().getPath() + ".json"), json.getBytes());
            DataGen.DATA.tags++;
        }

        for (TagKey<Biome> k : BiomeRegistries.TAG_TO_BIOMES.keySet()) {
            String json = TAG
                    .replace("%%VALUES%%", BiomeRegistries.TAG_TO_BIOMES.get(k).stream()
                            .map(b -> BiomeRegistries.keyOrNull(b).getValue().toString())
                            .map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(",\n    "))

                    );

            Path path = PATH
                    .resolve(k.id().getNamespace())
                    .resolve("tags")
                    .resolve("worldgen")
                    .resolve("biome")
                    .resolve(k.id().getPath() + ".json");

            path.getParent().toFile().mkdirs();
            Files.write(path, json.getBytes());
            DataGen.DATA.tags++;
        }
    }

    public static void block(TagKey<Block> key, Block... values) throws IOException {
        String json = TAG
                .replace("%%VALUES%%", Arrays.stream(values)
                        .map(b -> Registries.BLOCK.getId(b).toString())
                        .map(s -> "\"" + s + "\"")
                        .collect(Collectors.joining(",\n    "))

                );

        Path path = PATH
                .resolve(key.id().getNamespace())
                .resolve("tags")
                .resolve("blocks")
                .resolve(key.id().getPath() + ".json");

        path.getParent().toFile().mkdirs();
        Files.write(path, json.getBytes());

        DataGen.DATA.tags++;
    }
}
