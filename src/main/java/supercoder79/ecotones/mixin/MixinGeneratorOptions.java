package supercoder79.ecotones.mixin;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supercoder79.ecotones.world.gen.EcotonesBiomeSource;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.OptionalLong;
import java.util.Properties;
import java.util.Random;

@Mixin(GeneratorOptions.class)
public abstract class MixinGeneratorOptions {
    // FIXME

    @Shadow
    public static OptionalLong parseSeed(String seed) {
        return null;
    }

//    @Shadow
//    public static Registry<DimensionOptions> getRegistryWithReplacedOverworldGenerator(Registry<DimensionType> dimensionTypeRegistry, Registry<DimensionOptions> options, ChunkGenerator overworldGenerator) {
//        return null;
//    }

//    @Inject(method = "fromProperties", at = @At("HEAD"), cancellable = true)
//    private static void injectEcotones(DynamicRegistryManager registryManager, ServerPropertiesHandler.WorldGenProperties properties, CallbackInfoReturnable<GeneratorOptions> cir) {
//        long l = parseSeed(properties.levelSeed()).orElse(new Random().nextLong());
//        Registry<DimensionType> registry = registryManager.get(Registry.DIMENSION_TYPE_KEY);
//        Registry<Biome> registry2 = registryManager.get(Registry.BIOME_KEY);
//        Registry<StructureSet> registry3 = registryManager.get(Registry.STRUCTURE_SET_KEY);
//        Registry<DimensionOptions> registry4 = DimensionType.createDefaultDimensionOptions(registryManager, l);
//        String name = properties.levelType();
//
//        if (name.equals("ecotones")) {
//            GeneratorOptions genop = new GeneratorOptions(
//                    l,
//                    properties.generateStructures(),
//                    false,
//                    getRegistryWithReplacedOverworldGenerator(
//                            registry,
//                            registry4,
//                            new EcotonesChunkGenerator(registry3, new EcotonesBiomeSource(registry2, l), l)
//                    )
//            );
//
//            cir.setReturnValue(genop);
//        }
//    }
}
