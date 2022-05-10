package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public final class ChanceDecoratorConfig {
    public static final Codec<ChanceDecoratorConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("chance").forGetter(config -> config.chance)
    ).apply(instance, ChanceDecoratorConfig::new));

    public final int chance;
    public ChanceDecoratorConfig(int chance) {
        this.chance = chance;
    }
}
