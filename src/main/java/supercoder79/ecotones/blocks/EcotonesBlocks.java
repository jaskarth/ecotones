package supercoder79.ecotones.blocks;

import com.terraformersmc.terraform.tree.block.TerraformSaplingBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.blocks.sapling.HazelSaplingGenerator;
import supercoder79.ecotones.blocks.sapling.LarchSaplingGenerator;
import supercoder79.ecotones.blocks.sapling.MapleSaplingGenerator;
import supercoder79.ecotones.util.RegistryReport;

public final class EcotonesBlocks {
    public static Block PEAT_BLOCK = new Block(FabricBlockSettings.copy(Blocks.DIRT).breakByTool(FabricToolTags.SHOVELS).hardness(1f).build());
    public static Block SHORT_GRASS = new EcotonesGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block REEDS = new EcotonesGrassBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block WILDFLOWERS = new EcotonesGrassBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block SMALL_SHRUB = new EcotonesGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block GEYSER = new GeyserBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F, 8.0F).ticksRandomly().build());
    public static Block COCONUT = new CoconutBlock(FabricBlockSettings.of(Material.PLANT).strength(0.2F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().build());
    public static Block HAZEL_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().build());
    public static Block HAZEL_SAPLING = new TerraformSaplingBlock(new HazelSaplingGenerator());
    public static Block SANDY_GRASS = new EcotonesSandyGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block SURFACE_ROCK = new SurfaceRockBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.STONE).build());
    public static Block CLOVER = new EcotonesLeafPileBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block PINECONE = new PineconeBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block DRIED_DIRT = new Block(FabricBlockSettings.copy(Blocks.DIRT).breakByTool(FabricToolTags.PICKAXES).hardness(1f).build());
    public static Block BLUEBELL = new EcotonesGrassBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block WIDE_FERN = new EcotonesGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block SMALL_LILAC = new EcotonesGrassBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block LICHEN = new LichenBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().hardness(0.2F).sounds(BlockSoundGroup.VINE).build());
    public static Block MOSS = new EcotonesLeafPileBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block MAPLE_LEAVES = new MapleLeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().build());
    public static Block BLUEBERRY_BUSH = new BlueberryBushBlock(AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH));
    public static Block NEST = new NestBlock(FabricBlockSettings.of(Material.WOOD).nonOpaque().breakInstantly().sounds(BlockSoundGroup.WOOD).build());
    public static Block SWITCHGRASS = new EcotonesGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block ROSEMARY = new RosemaryBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block LAVENDER = new EcotonesGrassBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block BLUEBERRY_JAM_JAR = new JarBlock(FabricBlockSettings.of(Material.DECORATION).nonOpaque().breakInstantly().sounds(BlockSoundGroup.STONE).build());
    public static Block SPRUCE_LEAF_PILE = new EcotonesLeafPileBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block MARIGOLD = new EcotonesGrassBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block MAPLE_SAPLING = new TerraformSaplingBlock(new MapleSaplingGenerator());
    public static Block MAPLE_SYRUP_JAR = new JarBlock(FabricBlockSettings.of(Material.DECORATION).nonOpaque().breakInstantly().sounds(BlockSoundGroup.STONE).build());
    public static Block LARCH_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().build());
    public static Block SAP_DISTILLERY = new SapDistilleryBlock(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES).strength(3.5F, 1.0F).sounds(BlockSoundGroup.STONE).build());
    public static Block SMALL_CACTUS = new SmallCactusBlock(FabricBlockSettings.of(Material.PLANT).ticksRandomly().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block LARCH_SAPLING = new TerraformSaplingBlock(new LarchSaplingGenerator());
    public static Block TREETAP = new TreetapBlock(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES).strength(3.5F, 1.0F).sounds(BlockSoundGroup.STONE).build());
    public static Block LIMESTONE = new Block(FabricBlockSettings.copy(Blocks.DRIPSTONE_BLOCK).breakByTool(FabricToolTags.PICKAXES).hardness(1f).build());
    public static Block POOFY_DANDELION = new PoofyDandelionBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block CATTAIL = new CattailBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block DUCKWEED = new DuckweedBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());
    public static Block CYAN_ROSE = new CyanRoseBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build());


    public static void init() {
        register("peat", PEAT_BLOCK);
        register("short_grass", SHORT_GRASS);
        register("reeds", REEDS);
        register("wildflowers", WILDFLOWERS);
        register("small_shrub", SMALL_SHRUB);
        register("geyser", GEYSER);
        register("coconut", COCONUT);
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
        register("blueberry_bush", BLUEBERRY_BUSH);
        register("nest", NEST);
        register("switchgrass", SWITCHGRASS);
        register("rosemary", ROSEMARY);
        register("lavender", LAVENDER);
        register("blueberry_jam_jar", BLUEBERRY_JAM_JAR);
        register("spruce_leaf_pile", SPRUCE_LEAF_PILE);
        register("marigold", MARIGOLD);
        register("maple_sapling", MAPLE_SAPLING);
        register("maple_syrup_jar", MAPLE_SYRUP_JAR);
        register("larch_leaves", LARCH_LEAVES);
        register("sap_distillery", SAP_DISTILLERY);
        register("small_cactus", SMALL_CACTUS);
        register("larch_sapling", LARCH_SAPLING);
        register("treetap", TREETAP);
        register("limestone", LIMESTONE);
        register("poofy_dandelion", POOFY_DANDELION);
        register("cattail", CATTAIL);
        register("duckweed", DUCKWEED);
        register("cyan_rose", CYAN_ROSE);
    }

    private static void register(String name, Block block) {
        Registry.register(Registry.BLOCK, Ecotones.id(name), block);
        RegistryReport.increment("Block");
    }
}
