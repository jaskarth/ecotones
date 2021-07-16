package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import supercoder79.ecotones.world.grass.GrassComposer;

public class GrassFeatureConfig implements FeatureConfig {
    public static final Codec<GrassFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("count").forGetter(config -> config.count)
    ).apply(instance, (count) -> new GrassFeatureConfig(null, count)));
    // The null here is because serializing composers is a task for another day

    public final GrassComposer composer;
    public final int count;

    // TODO: needs a better way of doing count
    public GrassFeatureConfig(GrassComposer composer, int count) {
        this.composer = composer;
        this.count = count;
    }
}
