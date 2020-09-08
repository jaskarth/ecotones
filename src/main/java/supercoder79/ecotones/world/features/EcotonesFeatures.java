package supercoder79.ecotones.world.features;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.ForestRockFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;
import supercoder79.ecotones.world.features.tree.*;

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
    public static DrainageDecorationFeature DRAINAGE;
    public static BranchingOakTreeFeature BRANCHING_OAK;
    public static ImprovedBirchTreeFeature IMPROVED_BIRCH;
    public static BranchingAcaciaTreeFeature BRANCHING_ACACIA;
    public static WideShrubFeature WIDE_SHRUB;
    public static AspenTreeFeature ASPEN_TREE;
    public static PlaceWaterFeature PLACE_WATER;
    public static FarmlandPatchFeature FARMLAND;
    public static BeehiveFeature BEEHIVES;
    public static MangroveTreeFeature MANGROVE_TREE;
    public static BranchingDarkOakTreeFeature BRANCHING_DARK_OAK;
    public static RockFeature ROCK;

    public static void init() {
        DESERTIFY_SOIL = Registry.register(Registry.FEATURE, new Identifier("ecotones", "desertify"), new DesertifySoilFeature(DefaultFeatureConfig.CODEC));
        CACTI = Registry.register(Registry.FEATURE, new Identifier("ecotones", "cacti"), new PlaceCactiFeature(DefaultFeatureConfig.CODEC));
        SHRUB = Registry.register(Registry.FEATURE, new Identifier("ecotones", "shrub"), new ShrubFeature(SimpleTreeFeatureConfig.CODEC));
        JUNGLE_PALM_TREE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "palm_tree"), new PalmTreeFeature(TreeFeatureConfig.CODEC, Blocks.JUNGLE_WOOD.getDefaultState()));
        SUGARCANE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "sugarcane"), new SugarCaneFeature(DefaultFeatureConfig.CODEC));
        SMALL_ACACIA = Registry.register(Registry.FEATURE, new Identifier("ecotones", "small_acacia"), new SmallAcaciaTreeFeature(TreeGenerationConfig.CODEC));
        BANANA_TREE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "banana_tree"), new BananaTreeFeature(TreeFeatureConfig.CODEC));
        SMALL_SPRUCE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "small_spruce"), new SmallSpruceTreeFeature());
        BIG_SHRUB = Registry.register(Registry.FEATURE, new Identifier("ecotones", "big_shrub"), new BigShrubFeature(SimpleTreeFeatureConfig.CODEC));
        POPLAR_TREE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "poplar_tree"), new PoplarTreeFeature(SimpleTreeFeatureConfig.CODEC));
        DRAINAGE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "drainage"), new DrainageDecorationFeature(DefaultFeatureConfig.CODEC));
        BRANCHING_OAK = Registry.register(Registry.FEATURE, new Identifier("ecotones", "branching_oak"), new BranchingOakTreeFeature(TreeGenerationConfig.CODEC));
        IMPROVED_BIRCH = Registry.register(Registry.FEATURE, new Identifier("ecotones", "improved_birch"), new ImprovedBirchTreeFeature(TreeGenerationConfig.CODEC));
        BRANCHING_ACACIA = Registry.register(Registry.FEATURE, new Identifier("ecotones", "branching_acacia"), new BranchingAcaciaTreeFeature(TreeGenerationConfig.CODEC));
        WIDE_SHRUB = Registry.register(Registry.FEATURE, new Identifier("ecotones", "wide_shrub"), new WideShrubFeature(SimpleTreeFeatureConfig.CODEC));
        ASPEN_TREE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "aspen_tree"), new AspenTreeFeature(SimpleTreeFeatureConfig.CODEC));
        PLACE_WATER = Registry.register(Registry.FEATURE, new Identifier("ecotones", "place_water"), new PlaceWaterFeature(DefaultFeatureConfig.CODEC));
        FARMLAND = Registry.register(Registry.FEATURE, new Identifier("ecotones", "farmland"), new FarmlandPatchFeature(DefaultFeatureConfig.CODEC));
        BEEHIVES = Registry.register(Registry.FEATURE, new Identifier("ecotones", "beehives"), new BeehiveFeature(DefaultFeatureConfig.CODEC));
        MANGROVE_TREE = Registry.register(Registry.FEATURE, new Identifier("ecotones", "mangrove_tree"), new MangroveTreeFeature(TreeGenerationConfig.CODEC));
        BRANCHING_DARK_OAK = Registry.register(Registry.FEATURE, new Identifier("ecotones", "branching_dark_oak"), new BranchingDarkOakTreeFeature(TreeGenerationConfig.CODEC));
        ROCK = Registry.register(Registry.FEATURE, new Identifier("ecotones", "rock"), new RockFeature(ForestRockFeatureConfig.CODEC));
    }
}
