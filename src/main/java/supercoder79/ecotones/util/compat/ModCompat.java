package supercoder79.ecotones.util.compat;

import supercoder79.ecotones.api.ModCompatRunner;
import supercoder79.ecotones.util.CampfireLogHelper;

import static supercoder79.ecotones.Ecotones.*;

public class ModCompat {
    public static void initEarly() {
        // Mod compat handlers that add new blocks that we use
        if (isModLoaded("floralisia")) {
            FloralisiaCompat.init();
            log("Registered Floralisia compat!");
        }

        if (isModLoaded("aurorasdeco")) {
            AurorasDecoCompat.init();
        }
    }

    public static void initLate() {
        // Mod Compat handlers
        if (isModLoaded("traverse")) {
            TraverseCompat.associateGenData();
            ModCompatRunner.register(TraverseCompat::init);
            log("Registered Traverse compat!");
        }

        if (isModLoaded("terrestria")) {
            ModCompatRunner.register(TerrestriaCompat::init);
            log("Registered Terrestria compat!");
        }

        // Love Aurora's Decorations <3
        if (isModLoaded("aurorasdeco")) {
            ModCompatRunner.register(AurorasDecoCompat::setupAurorasDecoStates);
            log("Registered Aurora's Decorations compat!");
        }

        if (isModLoaded("lambdafoxes")) {
            LambdaFoxesCompat.init();
            log("Registered LambdaFoxes compat!");
        }

        if (isModLoaded("yttr")) {
            YttrCompat.associateGenData();
            ModCompatRunner.register(YttrCompat::init);
            log("Registered Yttr compat!");
        }
    }
}
