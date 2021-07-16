package supercoder79.ecotones.world.features.rock;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.world.features.config.RockFeatureConfig;

public class RockInWaterFeature extends RockFeature {
    public RockInWaterFeature(Codec<RockFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<RockFeatureConfig> context) {
        if (context.getOrigin().getY() <= context.getGenerator().getSeaLevel()) {
            return super.generate(context);
        } else {
            return false;
        }
    }
}
