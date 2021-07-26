package supercoder79.ecotones.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Split into 2 classes to reduce the jar size with DevOnly
public final class AiLog {
    private static FileWriter writer;
    private static boolean isDev;

    public static void init() {
        isDev = FabricLoader.getInstance().isDevelopmentEnvironment();
        if (!isDev()) {
            return;
        }

        Path logPath = Paths.get(".", "logs", "ecotones", "ai.log").toAbsolutePath();
        File logFile = new File(logPath.toString());
        logFile.mkdirs();

        if (logFile.exists()) {
            logFile.delete();
        }

        try {
            Files.createFile(logPath);
            writer = new FileWriter(logFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(Entity entity, String msg) {
        if (!isDev()) {
            return;
        }

        log("[" + entity.getUuidAsString() + " @ " + entity.getBlockPos().toString() + "] " + msg);
    }

    public static void log(String msg) {
        if (!isDev()) {
            return;
        }

        try {
            writer.write(msg + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        if (!isDev()) {
            return;
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isDev() {
        return isDev;
    }
}
