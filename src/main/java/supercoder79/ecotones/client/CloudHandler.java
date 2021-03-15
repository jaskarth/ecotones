package supercoder79.ecotones.client;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImageBackedTexture;
import supercoder79.ecotones.client.cloud.ClearSkyCloudsModifier;
import supercoder79.ecotones.client.cloud.CloudModifier;
import supercoder79.ecotones.client.cloud.OvercastCloudsModifier;
import supercoder79.ecotones.util.ListHelper;

import java.util.List;
import java.util.Random;

public final class CloudHandler {
    private static final List<CloudModifier> CLOUD_MODIFIERS = ImmutableList.of(new OvercastCloudsModifier(), new ClearSkyCloudsModifier());

    private static long worldSeed;
    private static long cloudIdx = -1;
    private static long timeIdx = -1;
    private static CloudModifier currentModifier = null;
    private static NativeImageBackedTexture cloudsTexture;
    private static long lastTime = -1;

    public static void init(long seed) {
        worldSeed = seed;
        currentModifier = ListHelper.randomIn(CLOUD_MODIFIERS, new Random(worldSeed));
    }

    public static void update() {
        long time = MinecraftClient.getInstance().world.getTime();
        if (time > lastTime) {
            lastTime = time;

            update(time);

            long update = time / 600;

            if (update > timeIdx) {
                timeIdx = update;
                updateImage(time);
            }
        }

    }

    public static void update(long time) {
        long idx = time / 12000;

        if (idx > cloudIdx) {
            cloudIdx = idx;

            // SuperCoder desperately trying to avoid linear seed dependence, 2021
            currentModifier = ListHelper.randomIn(CLOUD_MODIFIERS, new Random(HashCommon.murmurHash3(worldSeed + (idx * 432424591) + (time * 72314141))));
        }
    }

    public static void updateImage(long time) {
        currentModifier.apply(new Random(worldSeed + time), cloudsTexture.getImage());

        cloudsTexture.upload();
    }

    public static void setTexture(NativeImageBackedTexture texture) {
        cloudsTexture = texture;
    }
}
