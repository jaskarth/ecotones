package supercoder79.updater.update;

import supercoder79.updater.code.ClassData;
import supercoder79.updater.match.Matcher;

import java.util.List;

public abstract class Update {
    public abstract Matcher when();

    public abstract UpdateResult update(String line, int lineIdx, int idx, List<String> lines, ClassData data);

    public abstract void finalize(ClassData data);
}
