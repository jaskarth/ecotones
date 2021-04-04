package supercoder79.ecotones.world.structure;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.util.RegistryReport;

public final class EcotonesConfiguredStructures {
    public static final ConfiguredStructureFeature<SingleStateFeatureConfig, ? extends StructureFeature<SingleStateFeatureConfig>> CAMPFIRE_OAK = EcotonesStructures.CAMPFIRE.configure(new SingleStateFeatureConfig(Blocks.OAK_LOG.getDefaultState()));
    public static final ConfiguredStructureFeature<SingleStateFeatureConfig, ? extends StructureFeature<SingleStateFeatureConfig>> CAMPFIRE_BIRCH = EcotonesStructures.CAMPFIRE.configure(new SingleStateFeatureConfig(Blocks.BIRCH_LOG.getDefaultState()));
    public static final ConfiguredStructureFeature<SingleStateFeatureConfig, ? extends StructureFeature<SingleStateFeatureConfig>> CAMPFIRE_SPRUCE = EcotonesStructures.CAMPFIRE.configure(new SingleStateFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState()));
    public static final ConfiguredStructureFeature<SingleStateFeatureConfig, ? extends StructureFeature<SingleStateFeatureConfig>> CAMPFIRE_DARK_OAK = EcotonesStructures.CAMPFIRE.configure(new SingleStateFeatureConfig(Blocks.DARK_OAK_LOG.getDefaultState()));

    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> COTTAGE = EcotonesStructures.COTTAGE.configure(FeatureConfig.DEFAULT);

    public static void init() {
        register("campfire_oak", CAMPFIRE_OAK);
        register("campfire_birch", CAMPFIRE_BIRCH);
        register("campfire_spruce", CAMPFIRE_SPRUCE);
        register("campfire_dark_oak", CAMPFIRE_DARK_OAK);
        register("cottage", COTTAGE);
    }

    private static void register(String name, ConfiguredStructureFeature<?, ?> feature) {
        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, Ecotones.id(name), feature);
        RegistryReport.increment("Configured Structures");
    }
}
