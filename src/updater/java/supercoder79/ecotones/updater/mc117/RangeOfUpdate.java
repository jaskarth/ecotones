package supercoder79.ecotones.updater.mc117;

import supercoder79.updater.code.ClassData;
import supercoder79.updater.match.Matcher;
import supercoder79.updater.match.SimpleMatcher;
import supercoder79.updater.parse.ParseInvoc;
import supercoder79.updater.update.Update;
import supercoder79.updater.update.UpdateResult;

import java.util.List;

public final class RangeOfUpdate extends Update {
    private static final String PATTERN = ".rangeOf";
    private static final String UPDATED = ".uniformRange";

    @Override
    public Matcher when() {
        return new SimpleMatcher(PATTERN);
    }

    @Override
    public UpdateResult update(String line, int lineIdx, int idx, List<String> lines, ClassData data) {
        // Parse params
        List<String> params = ParseInvoc.parseInvocation(line, idx + PATTERN.length());
        int paramsLength = ParseInvoc.invocationLength(line, idx + PATTERN.length());

        // Start with the base string, before the invocation
        String updated = line.substring(0, idx);

        // Add new ref
        updated += UPDATED + "(YOffset.fixed(" + params.get(0) + "))";

        updated += line.substring(idx + PATTERN.length() + paramsLength);

        return new UpdateResult(updated, true);
    }

    @Override
    public void finalize(ClassData data) {
        data.imports().addImport("net.minecraft.world.gen.YOffset");
    }
}
