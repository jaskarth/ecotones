package supercoder79.updater.update;

import supercoder79.updater.code.ClassData;
import supercoder79.updater.match.Matcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class ClassUpdater {
    public static void update(Update update, ClassData data, File file) {
        if (data == null) {
            return;
        }

        Matcher matcher = update.when();
        List<String> newLines = new ArrayList<>();

        // Add the lines above the class definition
        for (int i = 0; i < data.startIdx(); i++) {
            newLines.add(data.lines().get(i));
        }

        for (int i = data.startIdx(); i < data.lines().size(); i++) {
            String line = data.lines().get(i);

            List<Integer> points = matcher.match(line);

            String curr = line;
            for (int point = 0; point < points.size(); point++) {
                curr = update.update(curr, i, point, data.lines(), data);
            }

            newLines.add(curr);
        }

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
    }
}
