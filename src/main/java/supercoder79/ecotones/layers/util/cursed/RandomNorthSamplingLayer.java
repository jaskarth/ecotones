package supercoder79.ecotones.layers.util.cursed;

import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

/**
 * Samples north of a coordinate. No, I'm not calling it PawnTransformer, what gave you that idea?
 * This is probably very bad for your mental health, so use it sparingly.
 *
 * @author SuperCoder79
 */
public interface RandomNorthSamplingLayer extends ParentedLayer, IdentityCoordinateTransformer {
    int sample(LayerRandomnessSource layerRandomnessSource, int center, int north);
    int sample(LayerRandomnessSource layerRandomnessSource, int center, int north, int northnorth);

    @Override
    default int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        if (context.nextInt(2) == 0) {
            return this.sample(context,
                    parent.sample(transformX(x), transformZ(z)),
                    parent.sample(transformX(x + 1), transformZ(z)));
        }

        return this.sample(context,
                parent.sample(transformX(x), transformZ(z)),
                parent.sample(transformX(x + 1), transformZ(z)),
                parent.sample(transformX(x + 2), transformZ(z)));
    }
}