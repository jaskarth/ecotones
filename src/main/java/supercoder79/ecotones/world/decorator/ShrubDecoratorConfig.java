package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.decorator.DecoratorConfig;

public class ShrubDecoratorConfig implements DecoratorConfig {
    public static Codec<ShrubDecoratorConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("target_count").forGetter(config -> config.targetCount))
            .apply(instance, ShrubDecoratorConfig::new));
    public double targetCount;

    public ShrubDecoratorConfig(double targetCount) {
        this.targetCount = targetCount;
    }
}