package supercoder79.updater.util;

import java.io.File;
import java.util.List;

public final class Search {
    public static void search(List<File> files, File currentFile) {
        if (currentFile.isDirectory()) {
            for (File file : currentFile.listFiles()) {
                if (!file.isDirectory()) {
                    files.add(file);
                } else {
                    search(files, file);
                }
            }
        }
    }
}
