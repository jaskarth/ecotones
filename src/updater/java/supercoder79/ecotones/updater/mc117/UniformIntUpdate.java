package supercoder79.ecotones.updater.mc117;

import supercoder79.updater.code.ClassData;
import supercoder79.updater.match.Matcher;
import supercoder79.updater.match.SimpleMatcher;
import supercoder79.updater.update.Update;
import supercoder79.updater.update.UpdateResult;

import java.util.List;

public final class UniformIntUpdate extends Update {
    @Override
    public Matcher when() {
        return new SimpleMatcher("UniformIntDistribution.of");
    }

    @Override
    public UpdateResult update(String line, int lineIdx, int idx, List<String> lines, ClassData data) {
        System.out.println(line);

        return new UpdateResult(line, true);
    }
}
