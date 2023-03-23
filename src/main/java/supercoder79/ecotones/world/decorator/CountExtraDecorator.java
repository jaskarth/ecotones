package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.placementmodifier.AbstractCountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

import java.util.stream.Stream;

public class CountExtraDecorator extends AbstractCountPlacementModifier {
    public static final Codec<CountExtraDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CountExtraDecoratorConfig.CODEC.fieldOf("config").forGetter(c -> c.config)
    ).apply(instance, CountExtraDecorator::new));

    private final CountExtraDecoratorConfig config;
    public CountExtraDecorator(CountExtraDecoratorConfig config) {
        this.config = config;
    }

    @Override
    protected int getCount(Random random, BlockPos pos) {
        return config.extraChance < random.nextDouble() ? config.count : config.count + config.extraCount;
    }

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.COUNT_EXTRA;
    }
}
