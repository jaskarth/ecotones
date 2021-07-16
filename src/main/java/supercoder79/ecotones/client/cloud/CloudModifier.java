package supercoder79.ecotones.client.cloud;

import net.minecraft.client.texture.NativeImage;

import java.util.Random;

public interface CloudModifier {
    int COLOR = 255 << 24 | 255 << 16 | 255 << 8 | 255;

    void apply(Random random, NativeImage texture);
}
