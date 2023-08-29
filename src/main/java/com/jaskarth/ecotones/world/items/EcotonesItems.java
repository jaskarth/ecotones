package com.jaskarth.ecotones.world.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.entity.EcotonesEntities;
import com.jaskarth.ecotones.util.RegistryReport;

import java.util.ArrayList;
import java.util.List;

public final class EcotonesItems {
    public static final List<Item> ITEMS_FOR_GROUP = new ArrayList<>();
    // Regular Items
    public static final Item COCONUT = new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).build()));
    public static final Item HAZELNUT = new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2F).build()));
    public static final Item PEAT_ITEM = new Item(new Item.Settings());
    public static final Item PINE_SAP = new Item(new Item.Settings());
    public static final Item SAP_BALL = new Item(new Item.Settings());
    public static final Item BLUEBERRIES = new AliasedBlockItem(EcotonesBlocks.BLUEBERRY_BUSH, new Item.Settings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).snack().build()));
    public static final Item BLUEBERRY_JAM = new JamItem(EcotonesBlocks.BLUEBERRY_JAM_JAR, new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.8F).build()));
    public static final Item DUCK_EGG = new Item(new Item.Settings());
    public static final Item DUCK_SPAWN_EGG = new SpawnEggItem(EcotonesEntities.DUCK, 0xFFFF0A, 0xB38134, new Item.Settings());
    public static final Item ROSEMARY = new AliasedBlockItem(EcotonesBlocks.ROSEMARY, new Item.Settings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 6 * 20), 0.75f).snack().build()));
    public static final Item ECOTONES_BOOK = new EcotonesBookItem(new Item.Settings().maxCount(1));
    public static final Item MAPLE_SAP = new Item(new Item.Settings());
    public static final Item MAPLE_SAP_JAR = new Item(new Item.Settings());
    public static final Item MAPLE_SYRUP = new JamItem(EcotonesBlocks.MAPLE_SYRUP_JAR, new Item.Settings().food(new FoodComponent.Builder().hunger(8).saturationModifier(1.2F).build()).recipeRemainder(EcotonesItems.JAR));
    public static final Item PANCAKES = new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(10).saturationModifier(1.2F).build()));
    public static final Item GRASS_STRAND = new Item(new Item.Settings());
    public static final Item GRASS_CORD = new Item(new Item.Settings());
    public static final Item CACTUS_FRUIT = new AliasedBlockItem(EcotonesBlocks.SMALL_CACTUS, new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).snack().build()));
    public static final Item MALACHITE_ITEM = new Item(new Item.Settings());
    public static final Item PYRITE_ITEM = new Item(new Item.Settings());
    public static final Item GOLD_CHUNK = new Item(new Item.Settings());
    public static final Item TURPENTINE = new TurpentineItem(new Item.Settings());
    public static final Item BASIC_FERTILIZER = new Item(new Item.Settings());
    public static final Item MAGNIFYING_GLASS = new Item(new Item.Settings());
    public static final Item SULFUR = new Item(new Item.Settings());
    public static final Item PHOSPHATE = new Item(new Item.Settings());
    public static final Item JAR = new Item(new Item.Settings());


    public static final Item CYAN_ROSE = new BlockItem(EcotonesBlocks.CYAN_ROSE, new Item.Settings());

    public static void init() {

        register("coconut", COCONUT);
        register("hazelnut", HAZELNUT);
        register("peat_item", PEAT_ITEM);
        register("pine_sap", PINE_SAP);
        register("sap_ball", SAP_BALL);
        register("blueberries", BLUEBERRIES);
        register("blueberry_jam", BLUEBERRY_JAM);
        register("duck_egg", DUCK_EGG);
        register("duck_spawn_egg", DUCK_SPAWN_EGG);
        register("rosemary", ROSEMARY);
        register("ecotones_book", ECOTONES_BOOK);
        ITEMS_FOR_GROUP.remove(ECOTONES_BOOK);
        register("maple_sap", MAPLE_SAP);
        register("maple_sap_jar", MAPLE_SAP_JAR);
        register("maple_syrup", MAPLE_SYRUP);
        register("pancakes", PANCAKES);
        register("grass_strand", GRASS_STRAND);
        register("grass_cord", GRASS_CORD);
        register("cactus_fruit", CACTUS_FRUIT);
        register("malachite_item", MALACHITE_ITEM);
        register("pyrite_item", PYRITE_ITEM);
        register("gold_chunk", GOLD_CHUNK);
        register("turpentine", TURPENTINE);
        register("magnifying_glass", MAGNIFYING_GLASS);
        register("basic_fertilizer", BASIC_FERTILIZER);
        register("sulfur", SULFUR);
        register("phosphate", PHOSPHATE);
        register("jar", JAR);

        register("cyan_rose", CYAN_ROSE);

        ItemGroup group = FabricItemGroup.builder().icon(() -> new ItemStack(ECOTONES_BOOK))
                .displayName(Text.translatable("itemGroup.ecotones")).entries((ctx, entries) -> {
            ITEMS_FOR_GROUP.forEach(entries::add);
        }).build();

        Registry.register(Registries.ITEM_GROUP, Ecotones.id("ecotones"), group);
    }

    private static void register(String name, Item item) {
        ITEMS_FOR_GROUP.add(item);
        Registry.register(Registries.ITEM, Ecotones.id(name), item);
        RegistryReport.increment("Item");
    }
}
