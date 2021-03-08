package supercoder79.ecotones.world.structure;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class EcotonesConfiguredStructures {
    public static final ConfiguredStructureFeature<SingleStateFeatureConfig, ? extends StructureFeature<SingleStateFeatureConfig>> CAMPFIRE_OAK = EcotonesStructures.CAMPFIRE.configure(new SingleStateFeatureConfig(Blocks.OAK_LOG.getDefaultState()));
    public static final ConfiguredStructureFeature<SingleStateFeatureConfig, ? extends StructureFeature<SingleStateFeatureConfig>> CAMPFIRE_BIRCH = EcotonesStructures.CAMPFIRE.configure(new SingleStateFeatureConfig(Blocks.BIRCH_LOG.getDefaultState()));
    public static final ConfiguredStructureFeature<SingleStateFeatureConfig, ? extends StructureFeature<SingleStateFeatureConfig>> CAMPFIRE_SPRUCE = EcotonesStructures.CAMPFIRE.configure(new SingleStateFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState()));
    public static final ConfiguredStructureFeature<SingleStateFeatureConfig, ? extends StructureFeature<SingleStateFeatureConfig>> CAMPFIRE_DARK_OAK = EcotonesStructures.CAMPFIRE.configure(new SingleStateFeatureConfig(Blocks.DARK_OAK_LOG.getDefaultState()));

    public static void init() {
        register("campfire_oak", CAMPFIRE_OAK);
        register("campfire_birch", CAMPFIRE_BIRCH);
        register("campfire_spruce", CAMPFIRE_SPRUCE);
        register("campfire_dark_oak", CAMPFIRE_DARK_OAK);
    }

    private static void register(String name, ConfiguredStructureFeature<?, ?> feature) {
        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new Identifier("ecotones", name), feature);
    }
}
