package supercoder79.ecotones.gen;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;

public final class DataGen {
    private static final boolean RUN = true;
    public static void run() {
        if (RUN && FabricLoader.getInstance().isDevelopmentEnvironment()) {
            try {
                LangFileGen.init();

                runDataGen();

                LangFileGen.commit();
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        }
    }

    private static void runDataGen() throws IOException {
        crossBlock("flame_lily", "Flame Lily");
    }

    private static void crossBlock(String name, String localizedName) throws IOException {
        BlockstateGen.simple(name);
        BlockModelGen.cross(name);
        ItemModelGen.flat(name);

        LangFileGen.addBlock(name, localizedName);
        LangFileGen.addItemblock(name, localizedName);
    }
}
