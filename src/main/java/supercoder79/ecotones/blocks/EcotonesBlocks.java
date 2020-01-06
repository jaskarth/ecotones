package supercoder79.ecotones.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FernBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EcotonesBlocks {
    public static Block peatBlock;
    public static Block shortGrass;
    public static Item shortGrassItem;
    public static Block reeds;

    public static void init() {
        peatBlock = Registry.register(Registry.BLOCK, new Identifier("ecotones", "peat"), new Block(FabricBlockSettings.copy(Blocks.DIRT).breakByTool(FabricToolTags.SHOVELS).hardness(1f).build()));
        Registry.register(Registry.ITEM, new Identifier("ecotones", "peat"), new BlockItem(peatBlock, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

        shortGrass = Registry.register(Registry.BLOCK, new Identifier("ecotones", "short_grass"), new EcotonesGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        shortGrassItem = Registry.register(Registry.ITEM, new Identifier("ecotones", "short_grass"), new BlockItem(shortGrass, new Item.Settings().group(ItemGroup.DECORATIONS)));

        reeds = Registry.register(Registry.BLOCK, new Identifier("ecotones", "reeds"), new EcotonesGrassBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
        Registry.register(Registry.ITEM, new Identifier("ecotones", "reeds"), new BlockItem(reeds, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }
}
