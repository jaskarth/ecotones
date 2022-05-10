package supercoder79.ecotones.world.structure;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.mixin.StructureFeatureAccessor;

public class EcotonesStructures {
    public static final StructureFeature<SingleStateFeatureConfig> CAMPFIRE = new CampfireStructureFeature(SingleStateFeatureConfig.CODEC);
    public static final StructureFeature<DefaultFeatureConfig> COTTAGE = new CottageStructureFeature(DefaultFeatureConfig.CODEC);
    public static final StructureFeature<DefaultFeatureConfig> OUTPOST = new OutpostStructure(DefaultFeatureConfig.CODEC);

    public static void init() {
        // FIXME: add back default config

        Registry.register(Registry.STRUCTURE_FEATURE, Ecotones.id("campfire"), CAMPFIRE);
        Registry.register(Registry.STRUCTURE_FEATURE, Ecotones.id("cottage"), COTTAGE);
        Registry.register(Registry.STRUCTURE_FEATURE, Ecotones.id("outpost"), OUTPOST);

        StructureFeatureAccessor.getGenerationStepMap().put(CAMPFIRE, GenerationStep.Feature.SURFACE_STRUCTURES);
        StructureFeatureAccessor.getGenerationStepMap().put(COTTAGE, GenerationStep.Feature.SURFACE_STRUCTURES);
        StructureFeatureAccessor.getGenerationStepMap().put(OUTPOST, GenerationStep.Feature.SURFACE_STRUCTURES);

//        FabricStructureBuilder.create(Ecotones.id("campfire"), CAMPFIRE)
//                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
//                .defaultConfig(20, 4, 2492472)
//                .register();
//
//        FabricStructureBuilder.create(Ecotones.id("cottage"), COTTAGE)
//                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
//                .defaultConfig(24, 4, 32183183)
//                .adjustsSurface()
//                .register();
//
//        FabricStructureBuilder.create(Ecotones.id("outpost"), OUTPOST)
//                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
//                .defaultConfig(10, 2, 2492472)
//                .register();
    }
}
