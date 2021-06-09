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
    private static final String OLD_IMPORT = "net.minecraft.world.gen.UniformIntDistribution";
    private static final String CONSTANT_IMPORT = "net.minecraft.util.math.intprovider.ConstantIntProvider";
    private static final String UNIFORM_IMPORT = "net.minecraft.util.math.intprovider.UniformIntProvider";

    @Override
    public Matcher when() {
        return new SimpleMatcher(PATTERN);
    }

    @Override
    public UpdateResult update(String line, int lineIdx, int idx, List<String> lines, ClassData data) {
        // Parse params
        List<String> params = ParseInvoc.parseInvocation(line, idx + PATTERN.length());

        // Start with the base string, before the invocation
        String updated = line.substring(0, idx);

        // Remove the old import
        int importIdx = data.imports().indexOfImport(OLD_IMPORT);

        if (params.size() == 1) {
            // Constant int, simply rename ref
            updated += CONSTANT;
            // Add the end of the line
            updated += line.substring(idx + PATTERN.length());
            // Add import for the constant ref
            data.imports().insertImport(CONSTANT_IMPORT, importIdx);
        } else {
            // Get list of params
            int paramsLength = ParseInvoc.invocationLength(line, idx + PATTERN.length());
            // Add uniform ref
            updated += UNIFORM;
            // Update the (base, spread) to (min, max)
            updated += "(" + params.get(0) + ", " + (Integer.parseInt(params.get(0)) + Integer.parseInt(params.get(1))) + ")";
            // Add the rest of the string, but skipping over the existing params length
            updated += line.substring(idx + PATTERN.length() + paramsLength);
            // Add import for the uniform ref
            data.imports().insertImport(UNIFORM_IMPORT, importIdx);
        }

        return new UpdateResult(updated, true);
    }

    @Override
    public void finalize(ClassData data) {
        // At the end of the processing, remove the old import
        data.imports().removeImport(OLD_IMPORT);
    }
}
