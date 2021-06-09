package supercoder79.ecotones.updater.mc117;

import supercoder79.updater.code.ClassData;
import supercoder79.updater.match.Matcher;
import supercoder79.updater.match.SimpleMatcher;
import supercoder79.updater.parse.ParseInvoc;
import supercoder79.updater.update.Update;
import supercoder79.updater.update.UpdateResult;

import java.util.List;

public final class UniformIntUpdate extends Update {
    private static final String PATTERN = "UniformIntDistribution.of";
    @Override
    public Matcher when() {
        return new SimpleMatcher(PATTERN);
    }

    @Override
    public UpdateResult update(String line, int lineIdx, int idx, List<String> lines, ClassData data) {
        List<String> list = ParseInvoc.parseInvocation(line, idx + PATTERN.length());

        return new UpdateResult(line, false);
    }
}
