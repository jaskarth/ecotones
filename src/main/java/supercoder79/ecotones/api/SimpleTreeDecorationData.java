package supercoder79.ecotones.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.decorator.DecoratorConfig;

public class SimpleTreeDecorationData implements DecoratorConfig {
    public static Codec<SimpleTreeDecorationData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("count").forGetter(config -> config.count),
            Codec.BOOL.fieldOf("ignore_ground_check").forGetter(config -> config.ignoreGroundCheck))
            .apply(instance, SimpleTreeDecorationData::new));

    public final double count;
    public final boolean ignoreGroundCheck;

    public SimpleTreeDecorationData(double count) {
        this(count, false);
    }

    public SimpleTreeDecorationData(double count, boolean ignoreGroundCheck) {
        this.count = count;
        this.ignoreGroundCheck = ignoreGroundCheck;
    }
}
