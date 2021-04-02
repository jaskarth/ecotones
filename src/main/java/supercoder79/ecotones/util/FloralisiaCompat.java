package supercoder79.ecotones.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public final class FloralisiaCompat {
    private static boolean floralisiaEnabled = false;

    public static void init() {
        floralisiaEnabled = true;
    }

    private static Identifier id(String name) {
        return new Identifier("floralisia", name);
    }

    public static boolean isFloralisiaEnabled() {
        return floralisiaEnabled;
    }

    public static Supplier<BlockState> anastasia() {
        return () -> Registry.BLOCK.get(id("anastasia")).getDefaultState();
    }

    public static Supplier<BlockState> cymbidium() {
        return () -> Registry.BLOCK.get(id("cymbidium")).getDefaultState();
    }

    public static Supplier<BlockState> gladiolus() {
        return () -> Registry.BLOCK.get(id("gladiolus")).getDefaultState();
    }
}
