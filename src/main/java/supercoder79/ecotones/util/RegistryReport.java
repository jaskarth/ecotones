package supercoder79.ecotones.util;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RegistryReport {
    private static final Map<String, Integer> COUNTS = new HashMap<>();

    public static void increment(String string) {
        int count = COUNTS.getOrDefault(string, 0);
        COUNTS.put(string, count + 1);
    }

    public static void report(int ecotonesBiomes) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            try {
                Path logPath = Paths.get(".", "logs", "ecotones", "registry.log").toAbsolutePath();
                File logFile = new File(logPath.toString());
                logFile.mkdirs();

                if (logFile.exists()) {
                    logFile.delete();
                }

                Files.createFile(logPath);
                FileWriter writer = new FileWriter(logFile);

                // We hardcode biomes for now
                writer.write("Biome: " + ecotonesBiomes + "\n");
                for (Map.Entry<String, Integer> entry : COUNTS.entrySet()) {
                    try {
                        writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
