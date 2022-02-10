package supercoder79.ecotones.items;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.util.RegistryReport;

public final class EcotonesItems {
    // Block Items
    public static final Item PEAT = new BlockItem(EcotonesBlocks.PEAT_BLOCK, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item SHORT_GRASS = new BlockItem(EcotonesBlocks.SHORT_GRASS, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item REEDS = new BlockItem(EcotonesBlocks.REEDS, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item WILDFLOWERS = new BlockItem(EcotonesBlocks.WILDFLOWERS, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item SMALL_SHRUB = new BlockItem(EcotonesBlocks.SMALL_SHRUB, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item GEYSER = new BlockItem(EcotonesBlocks.GEYSER, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item HAZEL_LEAVES = new BlockItem(EcotonesBlocks.HAZEL_LEAVES, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item HAZEL_SAPLING = new BlockItem(EcotonesBlocks.HAZEL_SAPLING, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item SANDY_GRASS = new BlockItem(EcotonesBlocks.SANDY_GRASS, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item SURFACE_ROCK = new BlockItem(EcotonesBlocks.SURFACE_ROCK, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item CLOVER = new BlockItem(EcotonesBlocks.CLOVER, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item PINECONE = new BlockItem(EcotonesBlocks.PINECONE, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item DRIED_DIRT = new BlockItem(EcotonesBlocks.DRIED_DIRT, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item BLUEBELL = new BlockItem(EcotonesBlocks.BLUEBELL, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item WIDE_FERN = new BlockItem(EcotonesBlocks.WIDE_FERN, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item SMALL_LILAC = new BlockItem(EcotonesBlocks.SMALL_LILAC, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item LICHEN = new BlockItem(EcotonesBlocks.LICHEN, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item MOSS = new BlockItem(EcotonesBlocks.MOSS, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item MAPLE_LEAVES = new BlockItem(EcotonesBlocks.MAPLE_LEAVES, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item NEST = new BlockItem(EcotonesBlocks.NEST, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item SWITCHGRASS = new BlockItem(EcotonesBlocks.SWITCHGRASS, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item LAVENDER = new BlockItem(EcotonesBlocks.LAVENDER, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item SPRUCE_LEAF_PILE = new BlockItem(EcotonesBlocks.SPRUCE_LEAF_PILE, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item MARIGOLD = new BlockItem(EcotonesBlocks.MARIGOLD, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item MAPLE_SAPLING = new BlockItem(EcotonesBlocks.MAPLE_SAPLING, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item LARCH_LEAVES = new BlockItem(EcotonesBlocks.LARCH_LEAVES, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item SAP_DISTILLERY = new BlockItem(EcotonesBlocks.SAP_DISTILLERY, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item LARCH_SAPLING = new BlockItem(EcotonesBlocks.LARCH_SAPLING, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item TREETAP = new BlockItem(EcotonesBlocks.TREETAP, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item LIMESTONE = new BlockItem(EcotonesBlocks.LIMESTONE, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item POOFY_DANDELION = new BlockItem(EcotonesBlocks.POOFY_DANDELION, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item CATTAIL = new BlockItem(EcotonesBlocks.CATTAIL, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item DUCKWEED = new PlaceOnFluidItem(EcotonesBlocks.DUCKWEED, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item THORN_BUSH = new BlockItem(EcotonesBlocks.THORN_BUSH, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item RED_ROCK = new BlockItem(EcotonesBlocks.RED_ROCK, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item MALACHITE = new BlockItem(EcotonesBlocks.MALACHITE, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item PYRITE = new BlockItem(EcotonesBlocks.PYRITE, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item SPARSE_GOLD_ORE = new BlockItem(EcotonesBlocks.SPARSE_GOLD_ORE, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item FERTILIZER_SPREADER = new BlockItem(EcotonesBlocks.FERTILIZER_SPREADER, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item WATERGRASS = new BlockItem(EcotonesBlocks.WATERGRASS, new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item GRINDSTONE = new BlockItem(EcotonesBlocks.GRINDSTONE, new Item.Settings().group(EcotonesItemGroups.ECOTONES));

    // Regular Items
    public static final Item COCONUT = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).build()));
    public static final Item HAZELNUT = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES).food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2F).build()));
    public static final Item PEAT_ITEM = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item PINE_SAP = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item SAP_BALL = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item BLUEBERRIES = new AliasedBlockItem(EcotonesBlocks.BLUEBERRY_BUSH, new Item.Settings().group(EcotonesItemGroups.ECOTONES).food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).snack().build()));
    public static final Item BLUEBERRY_JAM = new JamItem(EcotonesBlocks.BLUEBERRY_JAM_JAR, new Item.Settings().group(EcotonesItemGroups.ECOTONES).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.8F).build()));
    public static final Item DUCK_EGG = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item ROSEMARY = new AliasedBlockItem(EcotonesBlocks.ROSEMARY, new Item.Settings().group(EcotonesItemGroups.ECOTONES).food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 6 * 20), 0.75f).snack().build()));
    public static final Item ECOTONES_BOOK = new EcotonesBookItem(new Item.Settings().maxCount(1));
    public static final Item MAPLE_SAP = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item MAPLE_SAP_JAR = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item MAPLE_SYRUP = new JamItem(EcotonesBlocks.MAPLE_SYRUP_JAR, new Item.Settings().group(EcotonesItemGroups.ECOTONES).food(new FoodComponent.Builder().hunger(8).saturationModifier(1.2F).build()).recipeRemainder(Items.GLASS_BOTTLE));
    public static final Item PANCAKES = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES).food(new FoodComponent.Builder().hunger(10).saturationModifier(1.2F).build()));
    public static final Item GRASS_STRAND = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item GRASS_CORD = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item CACTUS_FRUIT = new AliasedBlockItem(EcotonesBlocks.SMALL_CACTUS, new Item.Settings().group(EcotonesItemGroups.ECOTONES).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).snack().build()));
    public static final Item MALACHITE_ITEM = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item PYRITE_ITEM = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item GOLD_CHUNK = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item TURPENTINE = new TurpentineItem(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item BASIC_FERTILIZER = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item MAGNIFYING_GLASS = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item SULFUR = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));
    public static final Item PHOSPHATE = new Item(new Item.Settings().group(EcotonesItemGroups.ECOTONES));


    public static final Item CYAN_ROSE = new BlockItem(EcotonesBlocks.CYAN_ROSE, new Item.Settings().group(EcotonesItemGroups.ECOTONES));

    public static void init() {
        register("peat", PEAT);
        register("short_grass", SHORT_GRASS);
        register("reeds", REEDS);
        register("wildflowers", WILDFLOWERS);
        register("small_shrub", SMALL_SHRUB);
        register("geyser", GEYSER);
        register("hazel_leaves", HAZEL_LEAVES);
        register("hazel_sapling", HAZEL_SAPLING);
        register("sandy_grass", SANDY_GRASS);
        register("surface_rock", SURFACE_ROCK);
        register("clover", CLOVER);
        register("pinecone", PINECONE);
        register("dried_dirt", DRIED_DIRT);
        register("bluebell", BLUEBELL);
        register("wide_fern", WIDE_FERN);
        register("small_lilac", SMALL_LILAC);
        register("lichen", LICHEN);
        register("moss", MOSS);
        register("maple_leaves", MAPLE_LEAVES);
        register("nest", NEST);
        register("switchgrass", SWITCHGRASS);
        register("lavender", LAVENDER);
        register("spruce_leaf_pile", SPRUCE_LEAF_PILE);
        register("marigold", MARIGOLD);
        register("maple_sapling", MAPLE_SAPLING);
        register("larch_leaves", LARCH_LEAVES);
        register("sap_distillery", SAP_DISTILLERY);
        register("larch_sapling", LARCH_SAPLING);
        register("treetap", TREETAP);
        register("limestone", LIMESTONE);
        register("poofy_dandelion", POOFY_DANDELION);
        register("cattail", CATTAIL);
        register("duckweed", DUCKWEED);
        register("thorn_bush", THORN_BUSH);
        register("red_rock", RED_ROCK);
        register("malachite", MALACHITE);
        register("pyrite", PYRITE);
        register("sparse_gold_ore", SPARSE_GOLD_ORE);
        register("fertilizer_spreader", FERTILIZER_SPREADER);
        register("watergrass", WATERGRASS);
        register("grindstone", GRINDSTONE);

        register("coconut", COCONUT);
        register("hazelnut", HAZELNUT);
        register("peat_item", PEAT_ITEM);
        register("pine_sap", PINE_SAP);
        register("sap_ball", SAP_BALL);
        register("blueberries", BLUEBERRIES);
        register("blueberry_jam", BLUEBERRY_JAM);
        register("duck_egg", DUCK_EGG);
        register("rosemary", ROSEMARY);
        register("ecotones_book", ECOTONES_BOOK);
        register("maple_sap", MAPLE_SAP);
        register("maple_sap_jar", MAPLE_SAP_JAR);
        register("maple_syrup", MAPLE_SYRUP);
        register("pancakes", PANCAKES);
        register("grass_strand", GRASS_STRAND);
        register("grass_cord", GRASS_CORD);
        register("cactus_fruit", CACTUS_FRUIT);
        register("malachite_item", MALACHITE_ITEM);
        register("pyrite_item", PYRITE_ITEM);
        register("gold_chunk", GOLD_CHUNK);
        register("turpentine", TURPENTINE);
        register("magnifying_glass", MAGNIFYING_GLASS);
        register("basic_fertilizer", BASIC_FERTILIZER);
        register("sulfur", SULFUR);
        register("phosphate", PHOSPHATE);

        register("cyan_rose", CYAN_ROSE);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, Ecotones.id(name), item);
        RegistryReport.increment("Item");
    }
}
