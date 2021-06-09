package supercoder79.updater.update;

import supercoder79.updater.code.ClassData;
import supercoder79.updater.match.Matcher;

import java.util.List;

public final class ClassUpdater {
    public static void update(ClassData data, Update update) {
        if (data == null) {
            return;
        }

        Matcher matcher = update.when();

        for (int i = data.startIdx(); i < data.lines().size(); i++) {
            String line = data.lines().get(i);

            List<Integer> points = matcher.match(line);

            for (int point = 0; point < points.size(); point++) {
                update.update(line, i, point, data.lines(), data);
            }
        }
    }
}
