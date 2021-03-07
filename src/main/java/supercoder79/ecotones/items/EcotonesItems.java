package supercoder79.ecotones.items;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import supercoder79.ecotones.blocks.EcotonesBlocks;

public class EcotonesItems {
    public static Item PEAT_ITEM;
    public static final Item BLUEBERRIES = new AliasedBlockItem(EcotonesBlocks.BLUEBERRY_BUSH, new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).snack().build()));
    public static final Item BLUEBERRY_JAM = new JamItem(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3F).build()));
    public static final Item DUCK_EGG = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item ROSEMARY = new AliasedBlockItem(EcotonesBlocks.ROSEMARY, new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 6 * 20), 0.75f).snack().build()));

    public static void init() {
        // TODO: fix this
        Registry.register(Registry.ITEM, new Identifier("ecotones", "coconut"), new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).build())));
        Registry.register(Registry.ITEM, new Identifier("ecotones", "hazelnut"), new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2F).build())));
        PEAT_ITEM = Registry.register(Registry.ITEM, new Identifier("ecotones", "peat_item"), new Item(new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("ecotones", "pine_sap"), new Item(new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("ecotones", "sap_ball"), new Item(new Item.Settings().group(ItemGroup.MISC)));

        Registry.register(Registry.ITEM, new Identifier("ecotones", "blueberries"), BLUEBERRIES);
        Registry.register(Registry.ITEM, new Identifier("ecotones", "blueberry_jam"), BLUEBERRY_JAM);
        Registry.register(Registry.ITEM, new Identifier("ecotones", "duck_egg"), DUCK_EGG);
        Registry.register(Registry.ITEM, new Identifier("ecotones", "rosemary"), ROSEMARY);

        FuelRegistry.INSTANCE.add(PEAT_ITEM, 400);
    }
}
