package supercoder79.ecotones.util.compat;

import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public final class AurorasDecoCompat {
    private static boolean aurorasDecoEnabled = false;

    public static void init() {
        aurorasDecoEnabled = true;
    }

    public static boolean isAurorasDecoEnabled() {
        return aurorasDecoEnabled;
    }

    public static Supplier<BlockState> lavender() {
        return () -> Registry.BLOCK.get(id("lavender")).getDefaultState();
    }

    private static Identifier id(String name) {
        return new Identifier("aurorasdeco", name);
    }
}
