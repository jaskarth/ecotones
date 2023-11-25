package com.jaskarth.ecotones.client.tex;

import net.minecraft.client.texture.TextureManager;

public class EcotonesDynamicTextures {
    public static boolean init = false;

    public static void init(TextureManager manager) {
        if (init) {
            return;
        }

        init = true;

        SyrupTextureGenerator.register(manager);
        CooktopTextureGenerator.register(manager);
    }
}
