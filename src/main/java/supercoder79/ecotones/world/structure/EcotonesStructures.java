package supercoder79.ecotones.world.structure;

import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import supercoder79.ecotones.Ecotones;

public class EcotonesStructures {
    public static final StructureFeature<SingleStateFeatureConfig> CAMPFIRE = new CampfireStructureFeature(SingleStateFeatureConfig.CODEC);
    public static final StructureFeature<DefaultFeatureConfig> COTTAGE = new CottageStructureFeature(DefaultFeatureConfig.CODEC);

    public static void init() {
        FabricStructureBuilder.create(Ecotones.id("campfire"), CAMPFIRE)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(20, 4, 2492472)
                .register();

        FabricStructureBuilder.create(Ecotones.id("cottage"), COTTAGE)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(24, 4, 32183183)
                .adjustsSurface()
                .register();
    }
}
