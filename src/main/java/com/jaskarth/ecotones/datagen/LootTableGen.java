package com.jaskarth.ecotones.datagen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class LootTableGen {
    private static final Path PATH = Paths.get("..", "src/main/resources/data/ecotones/loot_tables/blocks");

    private static final String DROPS_SELF = """
            {
              "type": "minecraft:block",
              "pools": [
                {
                  "rolls": %%ROLLS%%,
                  "entries": [
                    {
                      "type": "minecraft:item",
                      "name": "ecotones:%%NAME%%"
                    }
                  ],
                  "conditions": [
                    {
                      "condition": "minecraft:survives_explosion"
                    }
                  ]
                }
              ]
            }
            """;

    private static final String MINMAX = """
            {
                    "min": %%MIN%%,
                    "max": %%MAX%%
                  },
            """;


    public static void dropsSelf(String name) throws IOException {
        String json = DROPS_SELF.replace("%%NAME%%", name).replace("%%ROLLS%%", "1");
        Files.write(PATH.resolve(name + ".json"), json.getBytes());

        DataGen.DATA.loottables++;
    }

    public static void dropsItem(String name, String drop) throws IOException {
        String json = DROPS_SELF.replace("%%NAME%%", drop).replace("%%ROLLS%%", "1");
        Files.write(PATH.resolve(name + ".json"), json.getBytes());

        DataGen.DATA.loottables++;
    }

    public static void dropsItem(String name, String drop, int min, int max) throws IOException {
        String json = DROPS_SELF
                .replace("%%NAME%%", drop)
                .replace("%%ROLLS%%", MINMAX
                        .replace("%%MIN%%", String.valueOf(min))
                        .replace("%%MAX%%", String.valueOf(max))
                );
        Files.write(PATH.resolve(name + ".json"), json.getBytes());

        DataGen.DATA.loottables++;
    }
}
