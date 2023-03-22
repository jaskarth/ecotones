package supercoder79.ecotones.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.blocks.sapling.HazelSaplingGenerator;
import supercoder79.ecotones.blocks.sapling.LarchSaplingGenerator;
import supercoder79.ecotones.blocks.sapling.MapleSaplingGenerator;
import supercoder79.ecotones.items.EcotonesItemBlocks;
import supercoder79.ecotones.util.RegistryReport;

public final class EcotonesBlocks {
    public static Block PEAT_BLOCK = new Block(FabricBlockSettings.copy(Blocks.DIRT)/*.breakByTool(FabricToolTags.SHOVELS)*/.hardness(1f));
    public static Block SHORT_GRASS = new EcotonesPlantBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block REEDS = new EcotonesPlantBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block WILDFLOWERS = new EcotonesPlantBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block SMALL_SHRUB = new EcotonesPlantBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block GEYSER = new GeyserBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F, 8.0F).ticksRandomly());
    public static Block COCONUT = new CoconutBlock(FabricBlockSettings.of(Material.PLANT).strength(0.2F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());
    public static Block HAZEL_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static Block HAZEL_SAPLING = new SimpleSaplingBlock(new HazelSaplingGenerator());
    public static Block SANDY_GRASS = new EcotonesSandyPlantBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block SURFACE_ROCK = new SurfaceRockBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.STONE));
    public static Block CLOVER = new EcotonesLeafPileBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block PINECONE = new PineconeBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block DRIED_DIRT = new Block(FabricBlockSettings.copy(Blocks.DIRT)/*.breakByTool(FabricToolTags.PICKAXES)*/.hardness(1f));
    public static Block BLUEBELL = new EcotonesPlantBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block WIDE_FERN = new EcotonesPlantBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block SMALL_LILAC = new EcotonesPlantBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block LICHEN = new LichenBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().hardness(0.2F).sounds(BlockSoundGroup.VINE));
    public static Block MOSS = new EcotonesLeafPileBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block MAPLE_LEAVES = new MapleLeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static Block BLUEBERRY_BUSH = new BlueberryBushBlock(AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH));
    public static Block NEST = new NestBlock(FabricBlockSettings.of(Material.WOOD).nonOpaque().breakInstantly().sounds(BlockSoundGroup.WOOD));
    public static Block SWITCHGRASS = new EcotonesPlantBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block ROSEMARY = new RosemaryBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block LAVENDER = new EcotonesPlantBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block BLUEBERRY_JAM_JAR = new JarBlock(FabricBlockSettings.of(Material.DECORATION).nonOpaque().breakInstantly().sounds(BlockSoundGroup.STONE));
    public static Block SPRUCE_LEAF_PILE = new EcotonesLeafPileBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block MARIGOLD = new EcotonesPlantBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block MAPLE_SAPLING = new SimpleSaplingBlock(new MapleSaplingGenerator());
    public static Block MAPLE_SYRUP_JAR = new JarBlock(FabricBlockSettings.of(Material.DECORATION).nonOpaque().breakInstantly().sounds(BlockSoundGroup.STONE));
    public static Block LARCH_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static Block SAP_DISTILLERY = new SapDistilleryBlock(FabricBlockSettings.of(Material.STONE)/*.breakByTool(FabricToolTags.PICKAXES)*/.strength(3.5F, 1.0F).sounds(BlockSoundGroup.STONE));
    public static Block SMALL_CACTUS = new SmallCactusBlock(FabricBlockSettings.of(Material.PLANT).ticksRandomly().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block LARCH_SAPLING = new SimpleSaplingBlock(new LarchSaplingGenerator());
    public static Block TREETAP = new TreetapBlock(FabricBlockSettings.of(Material.STONE)/*.breakByTool(FabricToolTags.PICKAXES)*/.strength(2.5F, 1.0F).sounds(BlockSoundGroup.STONE));
    public static Block LIMESTONE = new Block(FabricBlockSettings.copy(Blocks.DRIPSTONE_BLOCK)/*.breakByTool(FabricToolTags.PICKAXES)*/.hardness(1f));
    public static Block POOFY_DANDELION = new PoofyDandelionBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block CATTAIL = new CattailBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block DUCKWEED = new DuckweedBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block THORN_BUSH = new ThornBushBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block RED_ROCK = new Block(FabricBlockSettings.copy(Blocks.STONE)/*.breakByTool(FabricToolTags.PICKAXES)*/.hardness(1f));
    public static Block MALACHITE = new Block(FabricBlockSettings.copy(Blocks.STONE)/*.breakByTool(FabricToolTags.PICKAXES)*/.hardness(1f));
    public static Block PYRITE = new Block(FabricBlockSettings.copy(Blocks.STONE)/*.breakByTool(FabricToolTags.PICKAXES)*/.hardness(1f));
    public static Block SPARSE_GOLD_ORE = new Block(FabricBlockSettings.copy(Blocks.STONE)/*.breakByTool(FabricToolTags.PICKAXES)*/.hardness(2.5f));
    public static Block FERTILIZER_SPREADER = new FertilizerSpreaderBlock(FabricBlockSettings.of(Material.WOOD).nonOpaque()/*.breakByTool(FabricToolTags.AXES)*/.strength(1.5F, 1.0F).sounds(BlockSoundGroup.WOOD));
    public static Block WATERGRASS = new CattailBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block GRINDSTONE = new GrindstoneBlock(FabricBlockSettings.of(Material.STONE)/*.breakByTool(FabricToolTags.PICKAXES)*/.strength(2.5F, 1.0F).sounds(BlockSoundGroup.STONE));
    public static Block EXCURSION_FUNNEL = new ExcursionFunnelBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F, 8.0F).ticksRandomly());
    public static Block SULFUR_ORE = new Block(FabricBlockSettings.copy(Blocks.LAPIS_ORE)/*.breakByTool(FabricToolTags.PICKAXES)*/.hardness(3f));
    public static Block PHOSPHATE_ORE = new Block(FabricBlockSettings.copy(Blocks.LAPIS_ORE)/*.breakByTool(FabricToolTags.PICKAXES)*/.hardness(3f));
    public static Block FLAME_LILY = new EcotonesPlantBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static Block CYAN_ROSE = new CyanRoseBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));


    public static void init() {
        registerWithItem("peat", PEAT_BLOCK);
        registerWithItem("short_grass", SHORT_GRASS);
        registerWithItem("reeds", REEDS);
        registerWithItem("wildflowers", WILDFLOWERS);
        registerWithItem("small_shrub", SMALL_SHRUB);
        registerWithItem("geyser", GEYSER);
        register("coconut", COCONUT);
        registerWithItem("hazel_leaves", HAZEL_LEAVES);
        registerWithItem("hazel_sapling", HAZEL_SAPLING);
        registerWithItem("sandy_grass", SANDY_GRASS);
        registerWithItem("surface_rock", SURFACE_ROCK);
        registerWithItem("clover", CLOVER);
        registerWithItem("pinecone", PINECONE);
        registerWithItem("dried_dirt", DRIED_DIRT);
        registerWithItem("bluebell", BLUEBELL);
        registerWithItem("wide_fern", WIDE_FERN);
        registerWithItem("small_lilac", SMALL_LILAC);
        registerWithItem("lichen", LICHEN);
        registerWithItem("moss", MOSS);
        registerWithItem("maple_leaves", MAPLE_LEAVES);
        register("blueberry_bush", BLUEBERRY_BUSH);
        registerWithItem("nest", NEST);
        registerWithItem("switchgrass", SWITCHGRASS);
        register("rosemary", ROSEMARY);
        registerWithItem("lavender", LAVENDER);
        register("blueberry_jam_jar", BLUEBERRY_JAM_JAR);
        registerWithItem("spruce_leaf_pile", SPRUCE_LEAF_PILE);
        registerWithItem("marigold", MARIGOLD);
        registerWithItem("maple_sapling", MAPLE_SAPLING);
        register("maple_syrup_jar", MAPLE_SYRUP_JAR);
        registerWithItem("larch_leaves", LARCH_LEAVES);
        registerWithItem("sap_distillery", SAP_DISTILLERY);
        register("small_cactus", SMALL_CACTUS);
        registerWithItem("larch_sapling", LARCH_SAPLING);
        registerWithItem("treetap", TREETAP);
        registerWithItem("limestone", LIMESTONE);
        registerWithItem("poofy_dandelion", POOFY_DANDELION);
        registerWithItem("cattail", CATTAIL);
        registerWithItem("duckweed", DUCKWEED);
        registerWithItem("thorn_bush", THORN_BUSH);
        registerWithItem("red_rock", RED_ROCK);
        registerWithItem("malachite", MALACHITE);
        registerWithItem("pyrite", PYRITE);
        registerWithItem("sparse_gold_ore", SPARSE_GOLD_ORE);
        registerWithItem("fertilizer_spreader", FERTILIZER_SPREADER);
        registerWithItem("watergrass", WATERGRASS);
        registerWithItem("grindstone", GRINDSTONE);
        registerWithItem("steady_geyser", EXCURSION_FUNNEL);
        registerWithItem("sulfur_ore", SULFUR_ORE);
        registerWithItem("phosphate_ore", PHOSPHATE_ORE);
        registerWithItem("flame_lily", FLAME_LILY);
        register("cyan_rose", CYAN_ROSE);
    }

    private static void register(String name, Block block) {
        Registry.register(Registry.BLOCK, Ecotones.id(name), block);
        RegistryReport.increment("Block");
    }

    private static void registerWithItem(String name, Block block) {
        Registry.register(Registry.BLOCK, Ecotones.id(name), block);
        RegistryReport.increment("Block");
        EcotonesItemBlocks.associate(block, name);
    }
}
