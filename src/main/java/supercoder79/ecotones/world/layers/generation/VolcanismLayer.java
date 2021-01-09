package supercoder79.ecotones.world.layers.generation;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.world.biome.special.BlessedSpringsBiome;
import supercoder79.ecotones.world.biome.special.HotSpringsBiome;
import supercoder79.ecotones.world.biome.special.SuperVolcanicBiome;
import supercoder79.ecotones.world.biome.special.VolcanicBiome;
import supercoder79.ecotones.world.layers.seed.SeedLayer;

public enum VolcanismLayer implements IdentitySamplingLayer, SeedLayer {
    INSTANCE;

    public static final Identifier VOLCANIC = new Identifier("ecotones", "volcanic_biome");
    public static final Identifier SUPERVOLCANIC = new Identifier("ecotones", "supervolcanic_biome");
    public static final Identifier HOT_SPRINGS = new Identifier("ecotones", "hot_springs");
    public static final Identifier BLESSED_SPRINGS = new Identifier("ecotones", "blessed_springs");
    private OpenSimplexNoise volcanismNoise;

    @Override
    public int sample(LayerRandomnessSource context, int value) {
        return 0;
    }

    @Override
    public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        int sample = parent.sample(x, z);

        if (context.nextInt(6) == 0) {
            double volcanism = volcanismNoise.sample(x / 5f, z / 5f) * 1.25;
            if (volcanism < -0.8) {
                return context.nextInt(10) == 0 ? Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(SUPERVOLCANIC)) : Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(VOLCANIC));
            }

            if (volcanism > 0.8) {
                return context.nextInt(10) == 0 ? Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(BLESSED_SPRINGS)) : Ecotones.REGISTRY.getRawId(Ecotones.REGISTRY.get(HOT_SPRINGS));
            }
        }

        return sample;
    }


    @Override
    public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent, long seed) {
        volcanismNoise = new OpenSimplexNoise(seed);
        return this.create(context, parent);
    }
}
