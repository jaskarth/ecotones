package supercoder79.ecotones.features;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import supercoder79.ecotones.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.features.tree.BananaTreeFeature;
import supercoder79.ecotones.features.tree.PalmTreeFeature;
import supercoder79.ecotones.features.tree.SmallAcaciaTreeFeature;
import supercoder79.ecotones.features.tree.SmallSpruceTreeFeature;

public class EcotonesFeatures {
    public static DesertifySoilFeature DESERTIFY_SOIL;
    public static PlaceCactiFeature CACTI;
    public static ShrubFeature SHRUB;
    public static PalmTreeFeature JUNGLE_PALM_TREE;
    public static SugarCaneFeature SUGARCANE;
    public static SmallAcaciaTreeFeature SMALL_ACACIA;
    public static BananaTreeFeature BANANA_TREE;
    public static SmallSpruceTreeFeature SMALL_SPRUCE;
    public static BigShrubFeature BIG_SHRUB;
    public static PoplarTreeFeature POPLAR_TREE;

    public static void init() {
        DESERTIFY_SOIL = Registry.register(Registry.FEATURE, new Identifier("ecotones", "desertify"), new DesertifySoilFeature(DefaultFeatureConfig::deserialize));
        CACTI = Registry.register(Registry.FEATURE, new Identifier("ecotones", "cacti"), new PlaceCactiFeature(DefaultFeatureConfig::deserialize));
        SHRUB = Registry.register(Registry.FEATURE, new Identifier("ecotones", "shrub"), new ShrubFeature(SimpleTreeFeatureConfig::deserialize));
        JUNGLE_PALM_TREE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "palm_tree"),
                new PalmTreeFeature(TreeFeatureConfig::deserialize, Blocks.JUNGLE_WOOD.getDefaultState())
        );
        SUGARCANE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "sugarcane"), new SugarCaneFeature(DefaultFeatureConfig::deserialize));
        SMALL_ACACIA = Registry.register(Registry.FEATURE, new Identifier("ecotones", "small_acacia"), new SmallAcaciaTreeFeature());
        BANANA_TREE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "banana_tree"), new BananaTreeFeature(TreeFeatureConfig::deserialize));
        SMALL_SPRUCE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "small_spruce"), new SmallSpruceTreeFeature());
        BIG_SHRUB = Registry.register(Registry.FEATURE, new Identifier("ecotones", "big_shrub"), new BigShrubFeature(SimpleTreeFeatureConfig::deserialize));
        POPLAR_TREE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "poplar_tree"), new PoplarTreeFeature(SimpleTreeFeatureConfig::deserialize));
    }
}
