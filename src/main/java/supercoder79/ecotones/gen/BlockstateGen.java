package supercoder79.ecotones.gen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class BlockstateGen {
    private static final Path PATH = Paths.get("..", "src/main/resources/assets/ecotones/blockstates");
    private static final String SIMPLE = """
            {
              "variants": {
                "": { "model": "ecotones:block/%%NAME%%" }
              }
            }
            """;

    public static void simple(String name) throws IOException {
        String json = SIMPLE.replace("%%NAME%%", name);
        Files.write(PATH.resolve(name + ".json"), json.getBytes());

        DataGen.DATA.blockstates++;
    }
}
