package com.jaskarth.ecotones.util;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.ItemConvertible;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.items.EcotonesBlocksItems;
import com.jaskarth.ecotones.world.items.EcotonesItems;

public final class EcotonesComposting {
    public static void init() {
        register(EcotonesBlocksItems.get(EcotonesBlocks.SHORT_GRASS), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.WILDFLOWERS), 0.5);
        register(EcotonesBlocksItems.get(EcotonesBlocks.SMALL_SHRUB), 0.2);
        register(EcotonesBlocksItems.get(EcotonesBlocks.HAZEL_LEAVES), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.HAZEL_SAPLING), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.SANDY_GRASS), 0.2);
        register(EcotonesBlocksItems.get(EcotonesBlocks.CLOVER), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.BLUEBELL), 0.55);
        register(EcotonesBlocksItems.get(EcotonesBlocks.SMALL_LILAC), 0.65);
        register(EcotonesBlocksItems.get(EcotonesBlocks.LICHEN), 0.4);
        register(EcotonesBlocksItems.get(EcotonesBlocks.MOSS), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.MAPLE_LEAVES), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.SWITCHGRASS), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.LAVENDER), 0.65);
        register(EcotonesBlocksItems.get(EcotonesBlocks.SPRUCE_LEAF_PILE), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.MARIGOLD), 0.65);
        register(EcotonesBlocksItems.get(EcotonesBlocks.MAPLE_SAPLING), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.LARCH_LEAVES), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.LARCH_SAPLING), 0.5);
        register(EcotonesBlocksItems.get(EcotonesBlocks.POOFY_DANDELION), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.CATTAIL), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.DUCKWEED), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.THORN_BUSH), 0.3);
        register(EcotonesBlocksItems.get(EcotonesBlocks.PINECONE), 0.4);

        register(EcotonesItems.COCONUT, 0.65);
        register(EcotonesItems.HAZELNUT, 0.65);
        register(EcotonesItems.PEAT_ITEM, 0.4);
        register(EcotonesItems.PINE_SAP, 0.4);
        register(EcotonesItems.SAP_BALL, 0.8);
        register(EcotonesItems.BLUEBERRIES, 0.6);
        register(EcotonesItems.DUCK_EGG, 0.6);
        register(EcotonesItems.ROSEMARY, 0.6);
        register(EcotonesItems.MAPLE_SAP, 0.4);
        register(EcotonesItems.PANCAKES, 0.9);
        register(EcotonesItems.GRASS_STRAND, 0.3);
        register(EcotonesItems.GRASS_CORD, 0.6);
        register(EcotonesItems.CACTUS_FRUIT, 0.6);

        // TODO: compostable items that have a remainder (jars)
    }

    private static void register(ItemConvertible item, double chance) {
        CompostingChanceRegistry.INSTANCE.add(item, (float) chance);
    }
}
