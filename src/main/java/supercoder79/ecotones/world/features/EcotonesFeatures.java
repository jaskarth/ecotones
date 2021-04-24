package supercoder79.ecotones.world.features;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.TreeGenerationConfig;
import supercoder79.ecotones.util.RegistryReport;
import supercoder79.ecotones.world.features.config.*;
import supercoder79.ecotones.world.features.entity.BeehiveFeature;
import supercoder79.ecotones.world.features.entity.DuckNestFeature;
import supercoder79.ecotones.world.features.rock.*;
import supercoder79.ecotones.world.features.tree.*;

public final class EcotonesFeatures {
    public static final Feature<DefaultFeatureConfig> DESERTIFY_SOIL = new DesertifySoilFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> CACTI = new PlaceCactiFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<SimpleTreeFeatureConfig> SHRUB = new ShrubFeature(SimpleTreeFeatureConfig.CODEC);
    public static final Feature<TreeFeatureConfig> JUNGLE_PALM_TREE = new PalmTreeFeature(TreeFeatureConfig.CODEC, Blocks.JUNGLE_WOOD.getDefaultState());
    public static final Feature<DefaultFeatureConfig> SUGARCANE = new SugarCaneFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<TreeGenerationConfig> SMALL_ACACIA = new SmallAcaciaTreeFeature(TreeGenerationConfig.CODEC);
    public static final Feature<TreeFeatureConfig> BANANA_TREE = new BananaTreeFeature(TreeFeatureConfig.CODEC);
    public static final Feature<SimpleTreeFeatureConfig> SMALL_SPRUCE = new SmallSpruceTreeFeature();
    public static final Feature<SimpleTreeFeatureConfig> BIG_SHRUB = new BigShrubFeature(SimpleTreeFeatureConfig.CODEC);
    public static final Feature<SimpleTreeFeatureConfig> POPLAR_TREE = new PoplarTreeFeature(SimpleTreeFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> DRAINAGE = new DrainageDecorationFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<TreeGenerationConfig> BRANCHING_OAK = new BranchingOakTreeFeature(TreeGenerationConfig.CODEC);
    public static final Feature<TreeGenerationConfig> IMPROVED_BIRCH = new ImprovedBirchTreeFeature(TreeGenerationConfig.CODEC);
    public static final Feature<TreeGenerationConfig> BRANCHING_ACACIA = new BranchingAcaciaTreeFeature(TreeGenerationConfig.CODEC);
    public static final Feature<SimpleTreeFeatureConfig> WIDE_SHRUB = new WideShrubFeature(SimpleTreeFeatureConfig.CODEC);
    public static final Feature<SimpleTreeFeatureConfig> ASPEN_TREE = new AspenTreeFeature(SimpleTreeFeatureConfig.CODEC);
    public static final Feature<WaterFeatureConfig> PLACE_WATER = new PlaceWaterFeature(WaterFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> FARMLAND = new FarmlandPatchFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> BEEHIVES = new BeehiveFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<TreeGenerationConfig> MANGROVE_TREE = new MangroveTreeFeature(TreeGenerationConfig.CODEC);
    public static final Feature<TreeGenerationConfig> BRANCHING_DARK_OAK = new BranchingDarkOakTreeFeature(TreeGenerationConfig.CODEC);
    public static final Feature<RockFeatureConfig> ROCK = new RockFeature(RockFeatureConfig.CODEC);
    public static final Feature<SimpleTreeFeatureConfig> DEAD_TREE = new DeadTreeFeature(SimpleTreeFeatureConfig.CODEC);
    @Deprecated
    public static final Feature<DefaultFeatureConfig> PODZOL = new PodzolPatchFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<TreeGenerationConfig> MAPLE_TREE = new MapleTreeFeature(TreeGenerationConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> BLUEBERRY_BUSH = new BlueberryBushFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<SimpleTreeFeatureConfig> BARREN_PINE = new BarrenPineTreeFeature(SimpleTreeFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> DUCK_NEST = new DuckNestFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> PUMPKIN_FARM = new PumpkinFarmFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> ROSEMARY = new RosemaryFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<OakTreeFeatureConfig> STRAIGHT_OAK = new StraightOakTreeFeature(OakTreeFeatureConfig.CODEC);
    public static final Feature<SimpleTreeFeatureConfig> BARREN_TREE = new BarrenTreeFeature(SimpleTreeFeatureConfig.CODEC);
    public static final Feature<SimpleTreeFeatureConfig> FAN_TREE = new FanTreeFeature(SimpleTreeFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> SMALL_ROCK = new SmallRockFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<PatchFeatureConfig> GROUND_PATCH = new PatchFeature(PatchFeatureConfig.CODEC);
    public static final Feature<SimpleTreeFeatureConfig> TALL_BARREN_TREE = new TallBarrenTreeFeature(SimpleTreeFeatureConfig.CODEC);
    public static final Feature<RockSpireFeatureConfig> ROCK_SPIRE = new RockSpireFeature(RockSpireFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> GRANITE_SPRING = new GraniteSpringFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> WIDE_GRANITE_SPRING = new WideGraniteSpringFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> GRANITE_LAVA_SPRING = new GraniteLavaSpringFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<SimpleTreeFeatureConfig> LARCH_TREE = new LarchTreeFeature(SimpleTreeFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> PEBBLES = new PebblesFeature(DefaultFeatureConfig.CODEC);
    public static final Feature<RockFeatureConfig> ROCK_IN_WATER = new RockInWaterFeature(RockFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> GEYSER_PATCH = new GeyserPatchFeature(DefaultFeatureConfig.CODEC);

    public static void init() {
        register("desertify", DESERTIFY_SOIL);
        register("cacti", CACTI);
        register("shrub", SHRUB);
        register("palm_tree", JUNGLE_PALM_TREE);
        register("sugarcane", SUGARCANE);
        register("small_acacia", SMALL_ACACIA);
        register("banana_tree", BANANA_TREE);
        register("small_spruce", SMALL_SPRUCE);
        register("big_shrub", BIG_SHRUB);
        register("poplar_tree", POPLAR_TREE);
        register("drainage", DRAINAGE);
        register("branching_oak", BRANCHING_OAK);
        register("improved_birch", IMPROVED_BIRCH);
        register("branching_acacia", BRANCHING_ACACIA);
        register("wide_shrub", WIDE_SHRUB);
        register("aspen_tree", ASPEN_TREE);
        register("place_water", PLACE_WATER);
        register("farmland", FARMLAND);
        register("beehives", BEEHIVES);
        register("mangrove_tree", MANGROVE_TREE);
        register("branching_dark_oak", BRANCHING_DARK_OAK);
        register("rock", ROCK);
        register("dead_tree", DEAD_TREE);
        register("podzol", PODZOL);
        register("maple_tree", MAPLE_TREE);
        register("blueberry_bush", BLUEBERRY_BUSH);
        register("barren_pine", BARREN_PINE);
        register("duck_nest", DUCK_NEST);
        register("pumpkin_farm", PUMPKIN_FARM);
        register("rosemary", ROSEMARY);
        register("straight_oak", STRAIGHT_OAK);
        register("barren_tree", BARREN_TREE);
        register("fan_tree", FAN_TREE);
        register("small_rock", SMALL_ROCK);
        register("ground_patch", GROUND_PATCH);
        register("tall_barren_tree", TALL_BARREN_TREE);
        register("rock_spire", ROCK_SPIRE);
        register("granite_spring", GRANITE_SPRING);
        register("wide_granite_spring", WIDE_GRANITE_SPRING);
        register("granite_lava_spring", GRANITE_LAVA_SPRING);
        register("larch_tree", LARCH_TREE);
        register("pebbles", PEBBLES);
        register("rock_in_water", ROCK_IN_WATER);
        register("geyser_patch", GEYSER_PATCH);
    }

    private static void register(String name, Feature<?> feature) {
        Registry.register(Registry.FEATURE, Ecotones.id(name), feature);
        RegistryReport.increment("Feature");
    }
}
