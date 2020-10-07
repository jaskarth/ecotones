package supercoder79.ecotones.world.decorator;

import com.mojang.serialization.Codec;
import supercoder79.ecotones.api.SimpleTreeDecorationData;

public class ReverseTreePlacementDecorator extends SimpleTreePlacementDecorator {
    public ReverseTreePlacementDecorator(Codec<SimpleTreeDecorationData> codec) {
        super(codec);
    }

    // Make quality modeling go in reverse
    @Override
    protected double qualityToDensity(double q) {
        return super.qualityToDensity(1 - q);
    }
}
