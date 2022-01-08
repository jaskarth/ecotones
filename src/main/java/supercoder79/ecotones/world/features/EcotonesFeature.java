package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public abstract class EcotonesFeature<FC extends FeatureConfig> extends Feature<FC> {
    public EcotonesFeature(Codec<FC> configCodec) {
        super(configCodec);
    }

    public EcotonesConfiguredFeature<FC, ?> configure(FC config) {
        return new EcotonesConfiguredFeature<>(this, config);
    }
}
