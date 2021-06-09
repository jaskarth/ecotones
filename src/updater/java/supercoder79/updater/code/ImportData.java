package supercoder79.updater.code;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ImportData implements Iterable<String> {
    private final List<String> imports = new ArrayList<>(); // Fully qualified names
    private final List<String> importsSimple = new ArrayList<>(); // Simple names

    public void addImport(String string) {
        if (this.imports.contains(string)) {
            return;
        }

        this.imports.add(string);
        this.importsSimple.add(string.substring(string.lastIndexOf(".")));
    }

    public int removeImport(String string) {
        int idx = this.imports.indexOf(string);

        this.imports.remove(string);
        this.importsSimple.remove(string.substring(string.lastIndexOf(".")));

        return idx;
    }

    public int indexOfImport(String string) {
        return this.imports.indexOf(string);
    }

    public void insertImport(String string, int idx) {
        if (this.imports.contains(string)) {
            return;
        }

        this.imports.add(idx, string);
        this.importsSimple.add(idx, string.substring(string.lastIndexOf(".")));
    }

    public String getQualified(int idx) {
        return this.imports.get(idx);
    }

    public int size() {
        return this.imports.size();
    }

    @Override
    public Iterator<String> iterator() {
        return this.imports.iterator();
    }
}
