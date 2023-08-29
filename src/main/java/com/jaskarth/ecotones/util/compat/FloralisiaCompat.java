package com.jaskarth.ecotones.util.compat;

import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public final class FloralisiaCompat {
    private static boolean floralisiaEnabled = false;

    public static void init() {
        floralisiaEnabled = true;
    }

    private static Identifier id(String name) {
        return new Identifier("floralisia", name);
    }

    public static boolean isEnabled() {
        return floralisiaEnabled;
    }

    public static Supplier<BlockState> anastasia() {
        return () -> Registries.BLOCK.get(id("anastasia")).getDefaultState();
    }

    public static Supplier<BlockState> cymbidium() {
        return () -> Registries.BLOCK.get(id("cymbidium")).getDefaultState();
    }

    public static Supplier<BlockState> gladiolus() {
        return () -> Registries.BLOCK.get(id("gladiolus")).getDefaultState();
    }
}
