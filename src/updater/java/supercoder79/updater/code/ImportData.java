package supercoder79.updater.code;

import java.util.ArrayList;
import java.util.List;

public final class ImportData {
    private final List<String> imports = new ArrayList<>(); // Fully qualified names
    private final List<String> importsSimple = new ArrayList<>(); // Simple names

    public void addImport(String string) {
        this.imports.add(string);
        this.importsSimple.add(string.substring(string.lastIndexOf(".")));
    }
}
