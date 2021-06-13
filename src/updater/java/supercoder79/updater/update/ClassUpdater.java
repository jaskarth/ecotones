package supercoder79.updater.update;

import supercoder79.updater.code.ClassData;
import supercoder79.updater.match.Matcher;
import supercoder79.updater.util.LineResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class ClassUpdater {
    public static boolean update(Update update, ClassData data, File file) {
        if (data == null) {
            return false;
        }

        Matcher matcher = update.when();
        List<String> updatedLines = new ArrayList<>();

        boolean appliedUpdate = false;
        for (int i = data.startIdx(); i < data.lines().size(); i++) {
            String curr = data.lines().get(i);

            int indent = 0;
            // Calculate indent
            for (char c : curr.toCharArray()) {
                if (c == ' ') {
                    indent++;
                } else {
                    break;
                }
            }

            while (true) {
                List<Integer> points = matcher.match(curr);

                boolean updated = false;
                for (Integer point : points) {
                    UpdateResult res = update.update(curr, i, point, data.lines(), data);
                    if (res.updated()) {
                        appliedUpdate = true;
                    }
                    
                    if (res.recurse()) {
                        curr = res.result();
                        updated = true;
                        appliedUpdate = true;
                        break;
                    }

                    for (LineResult lineResult : res.addedLinesAfter()) {
                        // Calculate indent if we need to auto indent, otherwise just add line
                        data.lines().add(i + lineResult.offset() + 1, lineResult.autoIndent() ? " ".repeat(indent) + lineResult.content() : lineResult.content());
                    }
                }

                if (!updated) {
                    break;
                }
            }

            updatedLines.add(curr);
        }

        // Finalize if we've updated this class at all
        if (appliedUpdate) {
            update.finalize(data);
        } else {
            // Don't write classes if we haven't updated
            return false;
        }

        List<String> newLines = new ArrayList<>();
        int importsStart = -1;
        int importCount = 0;
        // Add the lines above the class definition
        for (int i = 0; i < data.startIdx(); i++) {
            // Skip the imports, we add them later
            String line = data.lines().get(i);
            if (line.startsWith("import ")) {
                if (importsStart == -1) {
                    importsStart = i;
                }

                importCount++;

                continue;
            }

            newLines.add(line);
        }

        // Process imports
        boolean foundJava = false;
        int javaStart = -1;
        if (importsStart > -1) {
            // Should match import count of data.imports()
            for (int i = 0; i < data.imports().size(); i++) {
                String imp = "import " + data.imports().getQualified(i) + ";";

                if (!data.imports().getQualified(i).startsWith("java")) {
                    newLines.add(importsStart + i, imp);
                } else {
                    if (javaStart == -1) {
                        javaStart = i;
                    }
                    foundJava = true;
                }
            }

            if (foundJava) {
                newLines.add(importsStart + javaStart, "");

                int j = 1;
                for (int i = 0; i < data.imports().size(); i++) {
                    String imp = "import " + data.imports().getQualified(i) + ";";

                    if (data.imports().getQualified(i).startsWith("java")) {
                        newLines.add(importsStart + javaStart + j, imp);
                        j++;
                    }
                }

                newLines.remove(newLines.size() - 1);
            }
        }

        // Off by 1 error- TODO figure out why this happens
        newLines.addAll(updatedLines);

        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(file));

            for (String line : newLines) {
                writer.println(line);
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error writing file " + file.getAbsolutePath());
        }

        return true;
    }
}
