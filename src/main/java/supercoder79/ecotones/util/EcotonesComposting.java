package supercoder79.ecotones.util;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.ItemConvertible;
import supercoder79.ecotones.items.EcotonesItems;

public final class EcotonesComposting {
    public static void init() {
        register(EcotonesItems.SHORT_GRASS, 0.3);
        register(EcotonesItems.WILDFLOWERS, 0.5);
        register(EcotonesItems.SMALL_SHRUB, 0.2);
        register(EcotonesItems.HAZEL_LEAVES, 0.3);
        register(EcotonesItems.HAZEL_SAPLING, 0.3);
        register(EcotonesItems.SANDY_GRASS, 0.2);
        register(EcotonesItems.CLOVER, 0.4);
        register(EcotonesItems.BLUEBELL, 0.65);
        register(EcotonesItems.WIDE_FERN, 0.3);
        register(EcotonesItems.SMALL_LILAC, 0.65);
        register(EcotonesItems.LICHEN, 0.4);
        register(EcotonesItems.MOSS, 0.4);
        register(EcotonesItems.MAPLE_LEAVES, 0.3);
        register(EcotonesItems.SWITCHGRASS, 0.3);
        register(EcotonesItems.LAVENDER, 0.65);
        register(EcotonesItems.SPRUCE_LEAF_PILE, 0.3);
        register(EcotonesItems.MARIGOLD, 0.65);
        register(EcotonesItems.MAPLE_SAPLING, 0.3);
        register(EcotonesItems.LARCH_LEAVES, 0.3);
        register(EcotonesItems.LARCH_SAPLING, 0.5);
        register(EcotonesItems.POOFY_DANDELION, 0.3);
        register(EcotonesItems.CATTAIL, 0.3);
        register(EcotonesItems.DUCKWEED, 0.3);
        register(EcotonesItems.THORN_BUSH, 0.3);
        register(EcotonesItems.PINECONE, 0.4);

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
