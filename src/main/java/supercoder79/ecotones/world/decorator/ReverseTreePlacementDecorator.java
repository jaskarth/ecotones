package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.decorator.PlacementModifierType;
import supercoder79.ecotones.api.SimpleTreeDecorationData;

public class ReverseTreePlacementDecorator extends SimpleTreePlacementDecorator {
    public static final Codec<ReverseTreePlacementDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SimpleTreeDecorationData.CODEC.fieldOf("config").forGetter(c -> c.config)
    ).apply(instance, ReverseTreePlacementDecorator::new));

    public ReverseTreePlacementDecorator(SimpleTreeDecorationData config) {
        super(config);
    }

    // Make quality modeling go in reverse
    @Override
    protected double qualityToDensity(double q) {
        return super.qualityToDensity(1 - q);
    }

    @Override
    public PlacementModifierType<?> getType() {
        return EcotonesDecorators.REVERSE_QUALITY_TREE_DECORATOR;
    }
}
