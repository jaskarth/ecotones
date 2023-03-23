package supercoder79.ecotones.gen;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.structure.Structure;
import supercoder79.ecotones.api.BiomeRegistries;

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
            Files.write(path.resolve("has_" + k.getKey().orElseThrow().getValue().getPath() + ".json"), json.getBytes());
            DataGen.DATA.tags++;
        }
    }

    public static void block(TagKey<Block> key, Block... values) throws IOException {
        String json = TAG
                .replace("%%VALUES%%", Arrays.stream(values)
                        .map(b -> Registry.BLOCK.getId(b).toString())
                        .map(s -> "\"" + s + "\"")
                        .collect(Collectors.joining(",\n    "))

                );

        Path path = PATH
                .resolve(key.id().getNamespace())
                .resolve("tags")
                .resolve("blocks");

        path = path.resolve(key.id().getPath() + ".json");

        path.getParent().toFile().mkdirs();
        Files.write(path, json.getBytes());

        DataGen.DATA.tags++;
    }
}
