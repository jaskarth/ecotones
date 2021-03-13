package supercoder79.ecotones.world.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;

public class OakTreeFeatureConfig implements FeatureConfig {
    public static final Codec<OakTreeFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("height").forGetter(config -> config.height),
            Codec.INT.fieldOf("random_height").forGetter(config -> config.randomHeight)
    ).apply(instance, OakTreeFeatureConfig::new));

    private int height;
    private int randomHeight;

    public OakTreeFeatureConfig(int height, int randomHeight) {
        this.height = height;
        this.randomHeight = randomHeight;
    }

    public OakTreeFeatureConfig() {
        this.height = 9;
        this.randomHeight = 4;
    }

    public OakTreeFeatureConfig setHeight(int height) {
        this.height = height;

        return this;
    }

    public OakTreeFeatureConfig setRandomHeight(int randomHeight) {
        this.randomHeight = randomHeight;

        return this;
    }

    public int getHeight() {
        return height;
    }

    public int getRandomHeight() {
        return randomHeight;
    }
}
