package supercoder79.updater.match;

import java.util.ArrayList;
import java.util.List;

public final class SimpleMatcher extends Matcher {
    private final String pattern;
    private final int length;

    public SimpleMatcher(String pattern) {
        this.pattern = pattern;
        this.length = pattern.length();
    }

    @Override
    public List<Integer> match(String line) {
        List<Integer> points = new ArrayList<>();

        for (int i = 0; i <= line.length() - this.length; i++) {
            if (line.substring(i, i + this.length).equals(this.pattern)) {
                points.add(i);
            }
        }

        return points;
    }
}
