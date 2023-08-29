package com.jaskarth.ecotones.api;

import java.util.ArrayList;
import java.util.List;

public final class ModCompatRunner {
    private static boolean initialized = false;
    private static boolean initializedEarly = false;
    private static final List<Runnable> COMPAT_ACTIONS = new ArrayList<>();
    private static final List<Runnable> COMPAT_ACTIONS_EARLY = new ArrayList<>();

    public static void register(Runnable compat) {
        COMPAT_ACTIONS.add(compat);
    }

    public static void registerEarly(Runnable compat) {
        COMPAT_ACTIONS_EARLY.add(compat);
    }

    public static void run() {
        if (!initialized) {
            initialized = true;

            for (Runnable compat : COMPAT_ACTIONS) {
                compat.run();
            }
        }
    }

    public static void runEarly() {
        if (!initializedEarly) {
            initializedEarly = true;

            for (Runnable compat : COMPAT_ACTIONS_EARLY) {
                compat.run();
            }
        }
    }
}