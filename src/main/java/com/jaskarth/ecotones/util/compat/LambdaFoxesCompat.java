package com.jaskarth.ecotones.util.compat;

public final class LambdaFoxesCompat {
    private static boolean isLambdaFoxesEnabled = false;

    public static void init() {
        isLambdaFoxesEnabled = true;
    }

    public static boolean isEnabled() {
        return isLambdaFoxesEnabled;
    }
}
