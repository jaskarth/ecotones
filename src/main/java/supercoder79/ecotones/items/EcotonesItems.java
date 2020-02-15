package supercoder79.ecotones.items;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EcotonesItems {
    public static void init() {
        Registry.register(Registry.ITEM, new Identifier("ecotones", "coconut"), new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).build())));
    }
}
