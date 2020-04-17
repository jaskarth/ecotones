package supercoder79.ecotones.items;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EcotonesItems {
    public static Item peatItem;

    public static void init() {
        Registry.register(Registry.ITEM, new Identifier("ecotones", "coconut"), new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).build())));
        Registry.register(Registry.ITEM, new Identifier("ecotones", "hazelnut"), new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2F).build())));
        peatItem = Registry.register(Registry.ITEM, new Identifier("ecotones", "peat_item"), new Item(new Item.Settings().group(ItemGroup.MISC)));
        peatItem = Registry.register(Registry.ITEM, new Identifier("ecotones", "pine_sap"), new Item(new Item.Settings().group(ItemGroup.MISC)));
        peatItem = Registry.register(Registry.ITEM, new Identifier("ecotones", "sap_ball"), new Item(new Item.Settings().group(ItemGroup.MISC)));
        FuelRegistry.INSTANCE.add(peatItem, 400);
    }
}
