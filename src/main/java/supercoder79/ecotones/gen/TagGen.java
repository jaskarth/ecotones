package supercoder79.ecotones.gen;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

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

        path.toFile().mkdirs();
        Files.write(path.resolve(key.id().getPath() + ".json"), json.getBytes());

        DataGen.DATA.tags++;
    }
}
