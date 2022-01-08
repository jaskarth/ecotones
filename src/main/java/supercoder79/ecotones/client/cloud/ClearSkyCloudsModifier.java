package supercoder79.ecotones.client.cloud;

import net.minecraft.client.texture.NativeImage;

import java.util.Random;

public class ClearSkyCloudsModifier implements CloudModifier {

    @Override
    public void apply(Random random, NativeImage texture) {
        int count = random.nextInt(4000) + 4000;

        for (int i = 0; i < count; i++) {
            texture.setColor(random.nextInt(256), random.nextInt(256), 0);
        }

        count = random.nextInt(500) + 500;

        for (int i = 0; i < count; i++) {
            texture.setColor(random.nextInt(256), random.nextInt(256), COLOR);
        }
    }
}
