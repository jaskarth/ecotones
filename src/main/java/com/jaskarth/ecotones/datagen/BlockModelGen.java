package com.jaskarth.ecotones.datagen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class BlockModelGen {
    private static final Path PATH = Paths.get("..", "src/main/resources/assets/ecotones/models/block");

    private static final String CUBE_ALL = """
            {
              "parent": "block/cube_all",
              "textures": {
                "all": "ecotones:block/%%NAME%%"
              }
            }
            """;

    private static final String CROSS = """
            {
            	"parent": "block/cross",
            	"textures": {
            		"cross": "ecotones:block/%%NAME%%"
            	}
            }
            """;

    private static final String LEAVES = """
            {
              "parent": "minecraft:block/leaves",
              "textures": {
                "all": "minecraft:block/%%NAME%%"
              }
            }
            """;

    public static void cubeAll(String name) throws IOException {
        String json = CUBE_ALL.replace("%%NAME%%", name);
        Files.write(PATH.resolve(name + ".json"), json.getBytes());

        DataGen.DATA.blockModels++;
    }

    public static void cross(String name) throws IOException {
        String json = CROSS.replace("%%NAME%%", name);
        Files.write(PATH.resolve(name + ".json"), json.getBytes());

        DataGen.DATA.blockModels++;
    }

    public static void leaves(String name) throws IOException {
        String json = LEAVES.replace("%%NAME%%", name);
        Files.write(PATH.resolve(name + ".json"), json.getBytes());

        DataGen.DATA.blockModels++;
    }
}
