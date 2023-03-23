package supercoder79.ecotones.gen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ItemModelGen {
    private static final Path PATH = Paths.get("..", "src/main/resources/assets/ecotones/models/item");

    private static final String FLAT = """
            {
              "parent": "item/generated",
              "textures": {
                "layer0": "ecotones:item/%%NAME%%"
              }
            }
            """;

    private static final String FROM_BLOCK = """
            {
              "parent": "ecotones:block/%%NAME%%"
            }
            """;

    public static void flat(String name) throws IOException {
        String json = FLAT.replace("%%NAME%%", name);
        Files.write(PATH.resolve(name + ".json"), json.getBytes());

        DataGen.DATA.itemModels++;
    }

    public static void block(String name) throws IOException {
        String json = FROM_BLOCK.replace("%%NAME%%", name);
        Files.write(PATH.resolve(name + ".json"), json.getBytes());

        DataGen.DATA.itemModels++;
    }
}
