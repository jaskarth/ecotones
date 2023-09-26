package com.jaskarth.ecotones.util.compat;

import com.jaskarth.ecotones.api.ModCompatRunner;
import com.jaskarth.ecotones.util.CampfireLogHelper;

import static com.jaskarth.ecotones.Ecotones.*;

public class ModCompat {
    public static void initEarly() {
        // Mod compat handlers that add new blocks that we use
        if (isModLoaded("aurorasdeco")) {
            AurorasDecoCompat.init();
        }
    }

    public static void initLate() {
        // Mod Compat handlers
        if (isModLoaded("traverse")) {
            ModCompatRunner.registerEarly(TraverseCompat::associateGenData);
            ModCompatRunner.register(TraverseCompat::init);
            log("Registered Traverse compat!");
        }

        if (isModLoaded("terrestria")) {
            ModCompatRunner.registerEarly(TerrestriaCompat::associateGenData);
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
            ModCompatRunner.registerEarly(YttrCompat::associateGenData);
            ModCompatRunner.register(YttrCompat::setupYttrStates);
            ModCompatRunner.register(YttrCompat::init);
            log("Registered Yttr compat!");
        }
    }
}
