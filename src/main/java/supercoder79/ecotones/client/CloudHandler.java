package supercoder79.ecotones.client;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import supercoder79.ecotones.client.cloud.ClearSkyCloudsModifier;
import supercoder79.ecotones.client.cloud.CloudModifier;
import supercoder79.ecotones.client.cloud.OvercastCloudsModifier;
import supercoder79.ecotones.util.ListHelper;

import java.util.List;
import java.util.Random;

public final class CloudHandler {
    // TODO: cloud tex is not preserved on reload

    private static final List<CloudModifier> CLOUD_MODIFIERS = ImmutableList.of(new OvercastCloudsModifier(), new ClearSkyCloudsModifier());

    private static long worldSeed;
    private static long cloudIdx = -1;
    private static long timeIdx = -1;
    private static CloudModifier currentModifier = null;
    private static NativeImageBackedTexture cloudsTexture;
    private static long lastTime = -1;
    private static int cloudTexCount = 0;
    private static long cloudTexTimeIdx = -1;

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
                updateCloudTexCount();
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

        updateCloudTexCount();
    }

    public static int getCloudTexCount() {
        long time = MinecraftClient.getInstance().world.getTime();
        long idx = time / 80;

        if (idx > cloudTexTimeIdx) {
            cloudTexTimeIdx = idx;

            updateCloudTexCount();
        }

        return cloudTexCount;
    }

    private static void updateCloudTexCount() {
        if (cloudsTexture == null || cloudsTexture.getImage() == null) {
            cloudTexCount = 0;

            return;
        }

        int nonEmptyPixels = 0;

        for (int x = 0; x < 256; x++) {
            for (int z = 0; z < 256; z++) {
                // Returns fully opaque (0xFFFFFFFF) pixels as -1.
                if (cloudsTexture.getImage().getPixelColor(x, z) < 0) {
                    nonEmptyPixels++;
                }
            }
        }

        cloudTexCount = nonEmptyPixels;
        System.out.println(cloudTexCount);
    }
}
