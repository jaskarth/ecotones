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
    private static final String CONSTANT = "ConstantIntProvider.create";
    private static final String UNIFORM = "UniformIntProvider.create";

    @Override
    public Matcher when() {
        return new SimpleMatcher(PATTERN);
    }

    @Override
    public UpdateResult update(String line, int lineIdx, int idx, List<String> lines, ClassData data) {
        // Parse params
        List<String> params = ParseInvoc.parseInvocation(line, idx + PATTERN.length());

        String updated = line.substring(0, idx);
        if (params.size() == 0) {
            // Constant int, simply rename ref
            updated += CONSTANT;
            // Add the end of the line
            updated += line.substring(idx + PATTERN.length() + (CONSTANT.length() - PATTERN.length()));
        } else {
            // Get list of params
            int paramsLength = ParseInvoc.invocationLength(line, idx + PATTERN.length());
            // Capture the total list of params
            String paramsString = line.substring(idx + PATTERN.length() + paramsLength);
            // Add uniform ref
            updated += UNIFORM;
            // Update the (base, spread) to (min, max)
            updated += "(" + params.get(0) + ", " + (Integer.parseInt(params.get(0)) + Integer.parseInt(params.get(1))) + ")";
            // Add the rest of the string, but replace the old parameters
            updated += line.substring(idx + PATTERN.length()).replace(paramsString, "");
        }

        return new UpdateResult(updated, true);
    }
}
