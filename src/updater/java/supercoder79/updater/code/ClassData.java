package supercoder79.updater.code;

import java.util.List;

public record ClassData(String name, String superclass, ImportData imports, List<String> lines, int startIdx) {
}
