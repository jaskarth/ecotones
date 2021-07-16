package supercoder79.updater.code;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ClassProcessor {
    public static ClassData process(File file) {
        List<String> lines = splitIntoLines(file);

        if (lines == null) {
            System.out.println("Couldn't process " + file.getName());
            return null;
        }

        ImportData imports = new ImportData();

        ClassDec foundDec = null;

        int startIdx = -1;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (processImport(line, imports)) {
                continue;
            }

            ClassDec dec = processClassDec(line);
            if (dec != null && foundDec == null) {
                foundDec = dec;
                startIdx = i;
                continue;
            }
        }

        if (foundDec == null) {
            return null;
        }

        return new ClassData(foundDec.name, foundDec.superclass, imports, lines, startIdx);
    }

    private static List<String> splitIntoLines(File file) {
        try {
            return Files.lines(file.toPath()).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ClassDec processClassDec(String line) {
        if (line.startsWith("public class ")) {
            String[] elements = line.split(" ");
            return new ClassDec(elements[2], elements.length > 3 && elements[3].equals("extends") ? elements[4] : null);
        } else if (line.startsWith("public final class ")) {
            String[] elements = line.split(" ");
            return new ClassDec(elements[3], elements.length > 4 && elements[4].equals("extends") ? elements[5] : null);
        } else if (line.startsWith("public abstract class ")) {
            String[] elements = line.split(" ");
            return new ClassDec(elements[3], elements.length > 4 && elements[4].equals("extends") ? elements[5] : null);
        }

        return null;
    }

    private record ClassDec(String name, String superclass) {}

    private static boolean processImport(String line, ImportData imports) {
        if (line.startsWith("import ")) {
            if (line.startsWith("import static ")) {
                return false; // TODO: we don't handle these
            }

            imports.addImport(line.replace("import ", "").replace(";", ""));

            return true;
        }


        return false;
    }
}
