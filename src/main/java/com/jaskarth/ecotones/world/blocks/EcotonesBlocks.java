package com.jaskarth.ecotones.world.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.world.blocks.sapling.HazelSaplingGenerator;
import com.jaskarth.ecotones.world.blocks.sapling.LarchSaplingGenerator;
import com.jaskarth.ecotones.world.blocks.sapling.MapleSaplingGenerator;
import com.jaskarth.ecotones.world.items.EcotonesBlocksItems;
import com.jaskarth.ecotones.util.RegistryReport;
import net.minecraft.state.property.Properties;

import java.util.function.ToIntFunction;

public final class EcotonesBlocks {
    public static final Block PEAT_BLOCK = new Block(FabricBlockSettings.copy(Blocks.DIRT).hardness(1f));
    public static final Block SHORT_GRASS = new EcotonesPlantBlock(FabricBlockSettings.create().replaceable().offset(AbstractBlock.OffsetType.XZ).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block WILDFLOWERS = new EcotonesPlantBlock(FabricBlockSettings.create().nonOpaque().offset(AbstractBlock.OffsetType.XZ).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block SMALL_SHRUB = new EcotonesPlantBlock(FabricBlockSettings.create().replaceable().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block GEYSER = new GeyserBlock(FabricBlockSettings.create().strength(2.5F, 8.0F).ticksRandomly());
    public static final Block COCONUT = new CoconutBlock(FabricBlockSettings.create().strength(0.2F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());
    public static final Block HAZEL_LEAVES = new LeavesBlock(FabricBlockSettings.create().strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block HAZEL_SAPLING = new SimpleSaplingBlock(new HazelSaplingGenerator());
    public static final Block SANDY_GRASS = new EcotonesSandyPlantBlock(FabricBlockSettings.create().offset(AbstractBlock.OffsetType.XZ).replaceable().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block SURFACE_ROCK = new SurfaceRockBlock(FabricBlockSettings.create().offset(AbstractBlock.OffsetType.XZ).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.STONE));
    public static final Block CLOVER = new EcotonesLeafPileBlock(FabricBlockSettings.create().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block PINECONE = new PineconeBlock(FabricBlockSettings.create().offset(AbstractBlock.OffsetType.XZ).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block BLUEBELL = new EcotonesPlantBlock(FabricBlockSettings.create().offset(AbstractBlock.OffsetType.XZ).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block SMALL_LILAC = new EcotonesPlantBlock(FabricBlockSettings.create().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block LICHEN = new LichenBlock(FabricBlockSettings.create().replaceable().nonOpaque().noCollision().hardness(0.2F).sounds(BlockSoundGroup.VINE));
    public static final Block MOSS = new EcotonesLeafPileBlock(FabricBlockSettings.create().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block MAPLE_LEAVES = new MapleLeavesBlock(FabricBlockSettings.create().strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block BLUEBERRY_BUSH = new BlueberryBushBlock(FabricBlockSettings.create().ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH));
    public static final Block NEST = new NestBlock(FabricBlockSettings.create().nonOpaque().breakInstantly().sounds(BlockSoundGroup.WOOD));
    public static final Block SWITCHGRASS = new EcotonesPlantBlock(FabricBlockSettings.create().replaceable().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block ROSEMARY = new RosemaryBlock(FabricBlockSettings.create().replaceable().nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block LAVENDER = new EcotonesPlantBlock(FabricBlockSettings.create().offset(AbstractBlock.OffsetType.XZ).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block BLUEBERRY_JAM_JAR = new JarBlock(FabricBlockSettings.create().nonOpaque().breakInstantly().sounds(BlockSoundGroup.STONE));
    public static final Block SPRUCE_LEAF_PILE = new EcotonesLeafPileBlock(FabricBlockSettings.create().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block MARIGOLD = new EcotonesPlantBlock(FabricBlockSettings.create().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block MAPLE_SAPLING = new SimpleSaplingBlock(new MapleSaplingGenerator());
    public static final Block MAPLE_SYRUP_JAR = new JarBlock(FabricBlockSettings.create().nonOpaque().breakInstantly().sounds(BlockSoundGroup.STONE));
    public static final Block LARCH_LEAVES = new LeavesBlock(FabricBlockSettings.create().strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque());
    public static final Block SAP_DISTILLERY = new SapDistilleryBlock(FabricBlockSettings.create().strength(3.5F, 1.0F).sounds(BlockSoundGroup.STONE));
    public static final Block SMALL_CACTUS = new SmallCactusBlock(FabricBlockSettings.create().offset(AbstractBlock.OffsetType.XZ).ticksRandomly().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block LARCH_SAPLING = new SimpleSaplingBlock(new LarchSaplingGenerator());
    public static final Block TREETAP = new TreetapBlock(FabricBlockSettings.create().strength(2.5F, 1.0F).sounds(BlockSoundGroup.STONE));
    public static final Block LIMESTONE = new Block(FabricBlockSettings.copy(Blocks.DRIPSTONE_BLOCK).hardness(1f));
    public static final Block POOFY_DANDELION = new PoofyDandelionBlock(FabricBlockSettings.create().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block CATTAIL = new CattailBlock(FabricBlockSettings.create().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block DUCKWEED = new DuckweedBlock(FabricBlockSettings.create().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block THORN_BUSH = new ThornBushBlock(FabricBlockSettings.create().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block RED_ROCK = new Block(FabricBlockSettings.copy(Blocks.STONE).hardness(1f));
    public static final Block MALACHITE = new Block(FabricBlockSettings.copy(Blocks.STONE).hardness(1f));
    public static final Block PYRITE = new Block(FabricBlockSettings.copy(Blocks.STONE).hardness(1f));
    public static final Block SPARSE_GOLD_ORE = new Block(FabricBlockSettings.copy(Blocks.STONE).hardness(2.5f));
    public static final Block FERTILIZER_SPREADER = new FertilizerSpreaderBlock(FabricBlockSettings.create().nonOpaque()/*.breakByTool(FabricToolTags.AXES)*/.strength(1.5F, 1.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block WATERGRASS = new CattailBlock(FabricBlockSettings.create().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block GRINDSTONE = new GrindstoneBlock(FabricBlockSettings.create().strength(2.5F, 1.0F).sounds(BlockSoundGroup.STONE));
    public static final Block SULFUR_ORE = new Block(FabricBlockSettings.copy(Blocks.LAPIS_ORE).hardness(3f));
    public static final Block PHOSPHATE_ORE = new Block(FabricBlockSettings.copy(Blocks.LAPIS_ORE).hardness(3f));
    public static final Block FLAME_LILY = new EcotonesPlantBlock(FabricBlockSettings.create().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block POTTED_MAPLE_SAPLING = new FlowerPotBlock(MAPLE_SAPLING, FabricBlockSettings.create().breakInstantly().nonOpaque());
    public static final Block POTTED_LARCH_SAPLING = new FlowerPotBlock(LARCH_SAPLING, FabricBlockSettings.create().breakInstantly().nonOpaque());
    public static final Block POTTED_HAZEL_SAPLING = new FlowerPotBlock(HAZEL_SAPLING, FabricBlockSettings.create().breakInstantly().nonOpaque());
    public static final Block BRICK_COOKTOP = new CooktopBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(Instrument.BASEDRUM).requiresTool().strength(3.5F).luminance(createLightLevelFromLitBlockState(13)));

    public static final Block CYAN_ROSE = new CyanRoseBlock(FabricBlockSettings.create().nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));

    public static void init() {
        registerWithItem("peat", PEAT_BLOCK);
        registerWithItem("short_grass", SHORT_GRASS);
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
        registerWithItem("bluebell", BLUEBELL);
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
        registerWithItem("sulfur_ore", SULFUR_ORE);
        registerWithItem("phosphate_ore", PHOSPHATE_ORE);
        registerWithItem("flame_lily", FLAME_LILY);
        register("potted_maple_sapling", POTTED_MAPLE_SAPLING);
        register("potted_larch_sapling", POTTED_LARCH_SAPLING);
        register("potted_hazel_sapling", POTTED_HAZEL_SAPLING);
        registerWithItem("brick_cooktop", BRICK_COOKTOP);

        register("cyan_rose", CYAN_ROSE);
    }

    private static void register(String name, Block block) {
        Registry.register(Registries.BLOCK, Ecotones.id(name), block);
        RegistryReport.increment("Block");
    }

    private static void registerWithItem(String name, Block block) {
        Registry.register(Registries.BLOCK, Ecotones.id(name), block);
        RegistryReport.increment("Block");
        EcotonesBlocksItems.associate(block, name);
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }
}
