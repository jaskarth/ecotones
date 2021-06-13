package supercoder79.ecotones.updater.mc117;

import supercoder79.updater.code.ClassData;
import supercoder79.updater.match.Matcher;
import supercoder79.updater.match.SimpleMatcher;
import supercoder79.updater.update.Update;
import supercoder79.updater.update.UpdateResult;
import supercoder79.updater.util.LineResult;

import java.util.ArrayList;
import java.util.List;

public final class AddAmethystGeodesUpdate extends Update {
    @Override
    public Matcher when() {
        return new SimpleMatcher("DefaultBiomeFeatures.addDefaultOres(this.getGenerationSettings());");
    }

    @Override
    public UpdateResult update(String line, int lineIdx, int idx, List<String> lines, ClassData data) {
        List<LineResult> updatedLines = new ArrayList<>();

        updatedLines.add(new LineResult("DefaultBiomeFeatures.addAmethystGeodes(this.getGenerationSettings());"));

        return new UpdateResult(line, false, true, updatedLines);
    }

    @Override
    public void finalize(ClassData data) {

    }
}
