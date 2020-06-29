package supercoder79.ecotones.mixin;

import com.google.common.base.MoreObjects;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supercoder79.ecotones.world.generation.EcotonesBiomeSource;
import supercoder79.ecotones.world.generation.EcotonesChunkGenerator;

import java.util.Properties;
import java.util.Random;

@Mixin(GeneratorOptions.class)
public class MixinGeneratorOptions {
    @Inject(method = "fromProperties", at = @At("HEAD"), cancellable = true)
    private static void injectEcotones(Properties properties, CallbackInfoReturnable<GeneratorOptions> cir) {
        // no server.properties file generated
        if (properties.get("level-type") == null) {
            return;
        }

        // check for our world type and return if so
        if (properties.get("level-type").toString().trim().toLowerCase().equals("ecotones")) {
            // get or generate seed
            String seed = (String) MoreObjects.firstNonNull(properties.get("level-seed"), "");
            long l = new Random().nextLong();
            if (!seed.isEmpty()) {
                try {
                    long m = Long.parseLong(seed);
                    if (m != 0L) {
                        l = m;
                    }
                } catch (NumberFormatException var14) {
                    l = seed.hashCode();
                }
            }


            // get other misc data
            SimpleRegistry<DimensionOptions> dimensions = DimensionType.method_28517(l);

            String generate_structures = (String)properties.get("generate-structures");
            boolean generateStructures = generate_structures == null || Boolean.parseBoolean(generate_structures);

            // return our chunk generator
            cir.setReturnValue(new GeneratorOptions(l, generateStructures, false, GeneratorOptions.method_28608(dimensions, new EcotonesChunkGenerator(new EcotonesBiomeSource(l), l))));
        }
    }
}
