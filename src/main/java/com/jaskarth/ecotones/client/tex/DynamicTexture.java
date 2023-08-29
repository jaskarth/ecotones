package com.jaskarth.ecotones.client.tex;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureTickListener;

public final class DynamicTexture extends NativeImageBackedTexture implements TextureTickListener {
    private final Runnable tick;

    public DynamicTexture(NativeImage image, Runnable tick) {
        super(image);
        this.tick = tick;
    }

    @Override
    public void tick() {
        tick.run();
    }
}
