package supercoder79.ecotones.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.util.RegistryReport;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class EcotonesItemBlocks {
    private static final Map<Block, Item> MAP = new HashMap<>();

    public static Item get(Block block) {
        return MAP.get(block);
    }

    public static Item[] getAll(Block... blocks) {
        return Arrays.stream(blocks).map(MAP::get).toArray(Item[]::new);
    }

    public static void associate(Block block, String name) {
        BlockItem item = new BlockItem(block, new Item.Settings());
        EcotonesItems.ITEMS_FOR_GROUP.add(item);
        Registry.register(Registries.ITEM, Ecotones.id(name), item);
        RegistryReport.increment("Item");

        MAP.put(block, item);
    }
}
