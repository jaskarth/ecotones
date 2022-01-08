package supercoder79.ecotones.world.layers.generation;

import net.minecraft.util.math.MathHelper;
import supercoder79.ecotones.world.layers.system.layer.type.IdentitySamplingLayer;
import supercoder79.ecotones.world.layers.system.layer.type.InitLayer;
import supercoder79.ecotones.world.layers.system.layer.util.LayerFactory;
import supercoder79.ecotones.world.layers.system.layer.util.LayerRandomnessSource;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampleContext;
import supercoder79.ecotones.world.layers.system.layer.util.LayerSampler;
import supercoder79.ecotones.api.Climate;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.world.layers.seed.SeedInitLayer;

import java.util.Random;

public enum PickFromClimateLayer implements IdentitySamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int value) {
        return Climate.VALUES[value].choose(context);
    }
}
