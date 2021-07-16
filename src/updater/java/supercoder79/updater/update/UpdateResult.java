package supercoder79.updater.update;

import supercoder79.updater.util.LineResult;

import java.util.Collections;
import java.util.List;

public record UpdateResult(String result, boolean recurse, boolean updated, List<LineResult> addedLinesAfter) {
    public UpdateResult(String result, boolean recurse) {
        this(result, recurse, true, Collections.emptyList());
    }
}
