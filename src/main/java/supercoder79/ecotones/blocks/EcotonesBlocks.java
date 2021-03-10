package supercoder79.ecotones.blocks;

import com.terraformersmc.terraform.tree.block.TerraformSaplingBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import supercoder79.ecotones.blocks.sapling.HazelSaplingGenerator;

public final class EcotonesBlocks {
    // TODO: refactor out items
    public static Block PEAT_BLOCK;
    public static Block SHORT_GRASS;
    public static Item SHORT_GRASS_ITEM;
    public static Block REEDS;
    public static Block WILDFLOWERS;
    public static Item WILDFLOWERS_ITEM;
    public static Block SMALL_SHRUB;
    public static Item SMALL_SHRUB_ITEM;
    public static Block GEYSER;
    public static Block COCONUT;
    public static Block HAZEL_LEAVES;
    public static Item HAZEL_LEAVES_ITEM;
    public static Block HAZEL_SAPLING;
    public static Item HAZEL_SAPLING_ITEM;
    public static Block SANDY_GRASS;
    public static Item SANDY_GRASS_ITEM;
    public static Block SURFACE_ROCK;
    public static Item SURFACE_ROCK_ITEM;
    public static Block CLOVER;
    public static Item CLOVER_ITEM;
    public static Block PINECONE;
    public static Item PINECONE_ITEM;
    public static Block DRIED_DIRT;
    public static Item DRIED_DIRT_ITEM;
    public static Block BLUEBELL;
    public static Item BLUEBELL_ITEM;
    public static Block WIDE_FERN;
    public static Item WIDE_FERN_ITEM;
    public static Block SMALL_LILAC;
    public static Item  SMALL_LILAC_ITEM;
    public static Block LICHEN;
    public static Item  LICHEN_ITEM;
    public static Block MOSS;
    public static Item  MOSS_ITEM;
    public static Block MAPLE_LEAVES;
    public static Item MAPLE_LEAVES_ITEM;
    public static Block BLUEBERRY_BUSH;
    public static Block NEST;
    public static Item NEST_ITEM;
    public static Block SWITCHGRASS;
    public static Item SWITCHGRASS_ITEM;
    public static Block ROSEMARY;
    public static Block LAVENDER;
    public static Item  LAVENDER_ITEM;
    public static Block CYAN_ROSE;
    public static Item  CYAN_ROSE_ITEM;


    public static void init() {
        PEAT_BLOCK = Registry.register(Registry.BLOCK, new Identifier("ecotones", "peat"), new Block(FabricBlockSettings.copy(Blocks.DIRT).breakByTool(FabricToolTags.SHOVELS).hardness(1f).build()));
        Registry.register(Registry.ITEM, new Identifier("ecotones", "peat"), new BlockItem(PEAT_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

        SHORT_GRASS = Registry.register(Registry.BLOCK, new Identifier("ecotones", "short_grass"), new EcotonesGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        SHORT_GRASS_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "short_grass"), new BlockItem(SHORT_GRASS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        REEDS = Registry.register(Registry.BLOCK, new Identifier("ecotones", "reeds"), new EcotonesGrassBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        Registry.register(Registry.ITEM, new Identifier("ecotones", "reeds"), new BlockItem(REEDS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        WILDFLOWERS = Registry.register(Registry.BLOCK, new Identifier("ecotones", "wildflowers"), new EcotonesGrassBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        WILDFLOWERS_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "wildflowers"), new BlockItem(WILDFLOWERS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        SMALL_SHRUB = Registry.register(Registry.BLOCK, new Identifier("ecotones", "small_shrub"), new EcotonesGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        SMALL_SHRUB_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "small_shrub"), new BlockItem(SMALL_SHRUB, new Item.Settings().group(ItemGroup.DECORATIONS)));

        GEYSER = Registry.register(Registry.BLOCK, new Identifier("ecotones", "geyser"), new GeyserBlock(FabricBlockSettings.of(Material.STONE).strength(2.5F, 8.0F).ticksRandomly().build()));
        Registry.register(Registry.ITEM, new Identifier("ecotones", "geyser"), new BlockItem(GEYSER, new Item.Settings().group(ItemGroup.DECORATIONS)));

        COCONUT = Registry.register(Registry.BLOCK, new Identifier("ecotones", "coconut"), new CoconutBlock(FabricBlockSettings.of(Material.PLANT).strength(0.2F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().build()));
        HAZEL_LEAVES = Registry.register(Registry.BLOCK, new Identifier("ecotones", "hazel_leaves"), new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().build()));
        HAZEL_LEAVES_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "hazel_leaves"), new BlockItem(HAZEL_LEAVES, new Item.Settings().group(ItemGroup.DECORATIONS)));

        HAZEL_SAPLING = Registry.register(Registry.BLOCK, new Identifier("ecotones", "hazel_sapling"), new TerraformSaplingBlock(new HazelSaplingGenerator()));
        HAZEL_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "hazel_sapling"), new BlockItem(HAZEL_SAPLING, new Item.Settings().group(ItemGroup.DECORATIONS)));

        SANDY_GRASS = Registry.register(Registry.BLOCK, new Identifier("ecotones", "sandy_grass"), new EcotonesSandyGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        SANDY_GRASS_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "sandy_grass"), new BlockItem(SANDY_GRASS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        SURFACE_ROCK = Registry.register(Registry.BLOCK, new Identifier("ecotones", "surface_rock"), new SurfaceRockBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.STONE).build()));
        SURFACE_ROCK_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "surface_rock"), new BlockItem(SURFACE_ROCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

        CLOVER = Registry.register(Registry.BLOCK, new Identifier("ecotones", "clover"), new EcotonesLeafPileBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        CLOVER_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "clover"), new BlockItem(CLOVER, new Item.Settings().group(ItemGroup.DECORATIONS)));

        PINECONE = Registry.register(Registry.BLOCK, new Identifier("ecotones", "pinecone"), new PineconeBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        PINECONE_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "pinecone"), new BlockItem(PINECONE, new Item.Settings().group(ItemGroup.DECORATIONS)));

        DRIED_DIRT = Registry.register(Registry.BLOCK, new Identifier("ecotones", "dried_dirt"), new Block(FabricBlockSettings.copy(Blocks.DIRT).breakByTool(FabricToolTags.PICKAXES).hardness(1f).build()));
        DRIED_DIRT_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "dried_dirt"), new BlockItem(DRIED_DIRT, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

        BLUEBELL = Registry.register(Registry.BLOCK, new Identifier("ecotones", "bluebell"), new EcotonesGrassBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        BLUEBELL_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "bluebell"), new BlockItem(BLUEBELL, new Item.Settings().group(ItemGroup.DECORATIONS)));

        WIDE_FERN = Registry.register(Registry.BLOCK, new Identifier("ecotones", "wide_fern"), new EcotonesGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        WIDE_FERN_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "wide_fern"), new BlockItem(WIDE_FERN, new Item.Settings().group(ItemGroup.DECORATIONS)));

        SMALL_LILAC = Registry.register(Registry.BLOCK, new Identifier("ecotones", "small_lilac"), new EcotonesGrassBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        SMALL_LILAC_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "small_lilac"), new BlockItem(SMALL_LILAC, new Item.Settings().group(ItemGroup.DECORATIONS)));

        LICHEN = Registry.register(Registry.BLOCK, new Identifier("ecotones", "lichen"), new LichenBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().hardness(0.2F).sounds(BlockSoundGroup.VINE).build()));
        LICHEN_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "lichen"), new BlockItem(LICHEN, new Item.Settings().group(ItemGroup.DECORATIONS)));

        MOSS = Registry.register(Registry.BLOCK, new Identifier("ecotones", "moss"), new EcotonesLeafPileBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        MOSS_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "moss"), new BlockItem(MOSS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        CYAN_ROSE = Registry.register(Registry.BLOCK, new Identifier("ecotones", "cyan_rose"), new CyanRoseBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        CYAN_ROSE_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "cyan_rose"), new BlockItem(CYAN_ROSE, new Item.Settings().group(ItemGroup.DECORATIONS)));

        MAPLE_LEAVES = Registry.register(Registry.BLOCK, new Identifier("ecotones", "maple_leaves"), new MapleLeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().build()));
        MAPLE_LEAVES_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "maple_leaves"), new BlockItem(MAPLE_LEAVES, new Item.Settings().group(ItemGroup.DECORATIONS)));

        BLUEBERRY_BUSH = Registry.register(Registry.BLOCK, new Identifier("ecotones", "blueberry_bush"), new BlueberryBushBlock(AbstractBlock.Settings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)));

        NEST = Registry.register(Registry.BLOCK, new Identifier("ecotones", "nest"), new NestBlock(FabricBlockSettings.of(Material.WOOD).nonOpaque().breakInstantly().sounds(BlockSoundGroup.WOOD).build()));
        NEST_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "nest"), new BlockItem(NEST, new Item.Settings().group(ItemGroup.DECORATIONS)));

        SWITCHGRASS = Registry.register(Registry.BLOCK, new Identifier("ecotones", "switchgrass"), new EcotonesGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        SWITCHGRASS_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "switchgrass"), new BlockItem(SWITCHGRASS, new Item.Settings().group(ItemGroup.DECORATIONS)));

        ROSEMARY = Registry.register(Registry.BLOCK, new Identifier("ecotones", "rosemary"), new RosemaryBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));

        LAVENDER = Registry.register(Registry.BLOCK, new Identifier("ecotones", "lavender"), new EcotonesGrassBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        LAVENDER_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "lavender"), new BlockItem(LAVENDER, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }
}
