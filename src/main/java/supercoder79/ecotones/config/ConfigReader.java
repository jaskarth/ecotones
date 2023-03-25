package supercoder79.ecotones.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ConfigReader {
    private static final Gson GSON = new GsonBuilder().create();
    public static ConfigSpec read() {
        ConfigSpec value = new ConfigSpec();
        Path path = Paths.get(".", "config", "ecotones.json");

        if (!path.toFile().exists()) {
            ConfigWriter.write(value);
            return value;
        }

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            lines.removeIf(line -> line.stripLeading().startsWith("//"));

            return GSON.fromJson(String.join("\n", lines), ConfigSpec.class);
        } catch (Exception e) {
            throw new RuntimeException("Exception reading config file: ", e);
        }
    }
}
