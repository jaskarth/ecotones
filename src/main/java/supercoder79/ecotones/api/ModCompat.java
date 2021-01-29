package supercoder79.ecotones.api;

import java.util.ArrayList;
import java.util.List;

public class ModCompat {
    private static boolean initialized = false;
    private static final List<Runnable> COMPAT_ACTIONS = new ArrayList<>();

    public static void register(Runnable compat) {
        COMPAT_ACTIONS.add(compat);
    }

    public static void run() {
        if (!initialized) {
            initialized = true;

            for (Runnable compat : COMPAT_ACTIONS) {
                compat.run();
            }
        }
    }
}