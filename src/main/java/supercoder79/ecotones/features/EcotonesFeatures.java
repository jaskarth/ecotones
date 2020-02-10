package supercoder79.ecotones.features;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import supercoder79.ecotones.features.config.ShrubConfig;

public class EcotonesFeatures {
    public static DesertifySoilFeature DESERTIFY_SOIL;
    public static PlaceCactiFeature CACTI;
    public static ShrubFeature SHRUB;
    public static PalmTreeFeature JUNGLE_PALM_TREE;
    public static SugarCaneFeature SUGARCANE;

    public static void init() {
        DESERTIFY_SOIL = Registry.register(Registry.FEATURE, new Identifier("ecotones", "desertify"), new DesertifySoilFeature(DefaultFeatureConfig::deserialize));
        CACTI = Registry.register(Registry.FEATURE, new Identifier("ecotones", "cacti"), new PlaceCactiFeature(DefaultFeatureConfig::deserialize));
        SHRUB = Registry.register(Registry.FEATURE, new Identifier("ecotones", "shrub"), new ShrubFeature(ShrubConfig::deserialize));
        JUNGLE_PALM_TREE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "palm_tree"),
                new PalmTreeFeature(BranchedTreeFeatureConfig::deserialize, Blocks.JUNGLE_WOOD.getDefaultState())
        );
        SUGARCANE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "sugarcane"), new SugarCaneFeature(DefaultFeatureConfig::deserialize));
    }
}
