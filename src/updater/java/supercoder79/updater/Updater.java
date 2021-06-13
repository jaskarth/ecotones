package supercoder79.updater;

import com.sun.security.jgss.GSSUtil;
import supercoder79.updater.code.ClassData;
import supercoder79.updater.code.ClassProcessor;
import supercoder79.updater.update.ClassUpdater;
import supercoder79.updater.update.Update;
import supercoder79.updater.util.Search;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Updater {
    private final List<File> files = new ArrayList<>();
    private final List<Update> updates = new ArrayList<>();

    public void loadPath(Path path) {
        File file = new File(path.toString());
        List<File> files = new ArrayList<>();
        // Find all files in path
        Search.search(files, file);
        // Add all java files to
        this.files.addAll(files.stream().filter(f -> f.getAbsolutePath().endsWith(".java")).collect(Collectors.toList()));
    }

    public void register(Update... updates) {
        this.updates.addAll(Arrays.stream(updates).collect(Collectors.toList()));
    }

    public void run() {
        System.out.println("Starting updater...");
        System.out.println("Found " + this.files.size() + " classes to update with " + this.updates.size() + " updates.");

        for (Update update : this.updates) {
            System.out.println("Starting update " + update.getClass().getSimpleName() + "...");

            int i = 0;
            for (File file : this.files) {
                ClassData data = ClassProcessor.process(file);
                boolean updated = ClassUpdater.update(update, data, file);
                if (updated) {
                    i++;
                }
            }

            System.out.println("Done. Updated " + i + " files.");
        }
    }
}
