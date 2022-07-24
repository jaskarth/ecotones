package supercoder79.ecotones.gen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class RecipeGen {
    private static final Path PATH = Paths.get("..", "src/main/resources/data/ecotones/recipes");

    private static final String SHAPELESS = """
            {
              "type": "minecraft:crafting_shapeless",
              "ingredients": [
                %%INGREDIENTS%%
              ],
              "result": {
                "item": "%%OUTPUT%%",
                "count": %%COUNT%%
              }
            }
            """;

    private static final String SHAPELESS_INGREDIENT = """
            {
                  "item": "%%ITEM%%"
                }
            """;

    private static final String SHAPED = """
            {
              "type": "minecraft:crafting_shaped",
              "pattern": [
                %%PATTERN%%
              ],
              "key": {
                %%INGREDIENTS%%
              },
              "result": {
                "item": "%%OUTPUT%%",
                "count": %%COUNT%%
              }
            }
            """;

    private static final String SHAPED_INGREDIENT = """
            "%%CHAR%%": {
                  "item": "%%ITEM%%"
                }
            """;

    public static void shapeless(String name, String output, int amount, String... ingredients) throws IOException {
        String json = SHAPELESS
                .replace("%%COUNT%%", String.valueOf(amount))
                .replace("%%OUTPUT%%", output)
                .replace("%%INGREDIENTS%%", IntStream.of(ingredients.length)
                        .mapToObj(i -> SHAPELESS_INGREDIENT
                                .replace("%%ITEM%%", ingredients[i - 1])
                        )
                        .collect(Collectors.joining(",\n"))

                );

        Files.write(PATH.resolve(name + ".json"), json.getBytes());

        DataGen.DATA.recipes++;
    }

    public static void shaped(String name, String output, int amount, String lineA, String... ingredients) throws IOException {
        shaped(name, output, amount, lineA, "", "", ingredients);
    }

    public static void shaped(String name, String output, int amount, String lineA, String lineB, String... ingredients) throws IOException {
        shaped(name, output, amount, lineA, lineB, "", ingredients);
    }

    public static void shaped(String name, String output, int amount, String lineA, String lineB, String lineC, String... ingredients) throws IOException {
        if (ingredients.length % 2 == 1) {
            throw new IllegalStateException("Ingredients must be [char, ingredient]");
        }

        String json = SHAPED
                .replace("%%COUNT%%", String.valueOf(amount))
                .replace("%%OUTPUT%%", output)
                .replace("%%PATTERN%%", Stream.of(lineA, lineB, lineC)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.joining(",\n"))
                )
                .replace("%%INGREDIENTS%%", IntStream.of(ingredients.length / 2)
                        .mapToObj(i -> SHAPED_INGREDIENT
                                .replace("%%ITEM%%", ingredients[i * 2 - 1])
                                .replace("%%CHAR%%", ingredients[i * 2 - 2])
                        )
                        .collect(Collectors.joining(",\n"))

                );

        Files.write(PATH.resolve(name + ".json"), json.getBytes());

        DataGen.DATA.recipes++;
    }
}
