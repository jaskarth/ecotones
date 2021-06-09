package supercoder79.updater.parse;

import java.util.ArrayList;
import java.util.List;

public final class ParseInvoc {
    public static List<String> parseInvocation(String line, int start) {
        List<String> list = new ArrayList<>();
        String curr = "";
        int depth = 0;
        boolean found = false;

        for (int i = start; i < line.toCharArray().length; i++) {
            char c = line.toCharArray()[i];
            if (c == '(') {
                found = true;
                depth++;
                continue;
            }

            if (c == ')') {
                depth--;
                if (found && depth == 0) {
                    list.add(curr);
                    break;
                }
            }

            if (c == ',') {
                list.add(curr);
                curr = "";
                continue;
            }

            curr += c;
        }

        List<String> ret = new ArrayList<>();
        for (String s : list) {
            if (s.startsWith(" ")) {
                ret.add(s.substring(1));
            } else {
                ret.add(s);
            }
        }

        return ret;
    }

    // Includes parentheses!
    public static int invocationLength(String line, int start) {
        int depth = 0;
        boolean found = false;
        int len = 0;

        for (int i = start; i < line.toCharArray().length; i++) {
            len++;
            char c = line.toCharArray()[i];
            if (c == '(') {
                found = true;
                depth++;
                continue;
            }

            if (c == ')') {
                depth--;
                if (found && depth == 0) {
                    break;
                }
            }
        }

        return len;
    }
}
