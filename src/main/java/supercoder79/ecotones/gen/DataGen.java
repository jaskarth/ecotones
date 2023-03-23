package supercoder79.ecotones.gen;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import supercoder79.ecotones.blocks.EcotonesBlocks;

import java.io.IOException;
import java.util.List;

public final class DataGen {
    public static final Logger LOGGER = LogManager.getLogger("ecotones-datagen");
    public static final DataGenData DATA = new DataGenData();

    private static final boolean RUN = true;
    public static void run() {
        if (RUN && FabricLoader.getInstance().isDevelopmentEnvironment()) {
            try {
                LOGGER.info("Hello! Starting datagen...");

                long start = System.currentTimeMillis();

                LangFileGen.init();

                runDataGen();

                LangFileGen.commit();

                LOGGER.info("Datagen finished in " + (System.currentTimeMillis() - start) + "ms.");
                DATA.log();
                LOGGER.info("That's all from me, have a nice day!");
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        }
    }

    private static void runDataGen() throws IOException {
        TagGen.automakeStructureHas();

        crossBlock("flame_lily", "Flame Lily");

        writeRecipes();

        LangFileGen.addBlockItemBlock("steady_geyser", "Steady Geyser");
        ItemModelGen.block("grindstone");

        TagGen.block(BlockTags.SAPLINGS, EcotonesBlocks.HAZEL_SAPLING, EcotonesBlocks.LARCH_SAPLING, EcotonesBlocks.MAPLE_SAPLING);
        TagGen.block(BlockTags.FLOWERS, floral());
        TagGen.block(BlockTags.SMALL_FLOWERS, floral());
        LangFileGen.addItemOnly("jar", "Jar");
        LangFileGen.addBlock("grindstone", "Grindstone");

        LootTableGen.dropsSelf("sap_distillery");
        LootTableGen.dropsSelf("treetap");
        LootTableGen.dropsSelf("grindstone");
        LootTableGen.dropsSelf("fertilizer_spreader");

        writeMineableTags();
    }

    private static Block[] floral() {
        return new Block[] {
                EcotonesBlocks.WILDFLOWERS, EcotonesBlocks.BLUEBELL, EcotonesBlocks.SMALL_LILAC,
                EcotonesBlocks.FLAME_LILY, EcotonesBlocks.LAVENDER, EcotonesBlocks.LAVENDER
        };
    }

    private static void writeMineableTags() throws IOException {
        TagGen.block(BlockTags.PICKAXE_MINEABLE, EcotonesBlocks.SAP_DISTILLERY, EcotonesBlocks.TREETAP, EcotonesBlocks.GRINDSTONE,
                EcotonesBlocks.LIMESTONE, EcotonesBlocks.MALACHITE, EcotonesBlocks.PYRITE, EcotonesBlocks.SPARSE_GOLD_ORE, EcotonesBlocks.SULFUR_ORE,
                EcotonesBlocks.PHOSPHATE_ORE, EcotonesBlocks.RED_ROCK, EcotonesBlocks.DRIED_DIRT);

        TagGen.block(BlockTags.NEEDS_STONE_TOOL, EcotonesBlocks.LIMESTONE, EcotonesBlocks.MALACHITE, EcotonesBlocks.PYRITE);
        TagGen.block(BlockTags.NEEDS_IRON_TOOL, EcotonesBlocks.SPARSE_GOLD_ORE, EcotonesBlocks.SULFUR_ORE, EcotonesBlocks.PHOSPHATE_ORE);

        TagGen.block(BlockTags.AXE_MINEABLE, EcotonesBlocks.FERTILIZER_SPREADER);

        TagGen.block(BlockTags.SHOVEL_MINEABLE, EcotonesBlocks.PEAT_BLOCK);
    }

    private static void writeRecipes() throws IOException {
        RecipeGen.shapeless("flame_lily_to_dye", "minecraft:red_dye", 1, "ecotones:flame_lily");

        RecipeGen.shaped("blueberry_to_blue_dye", "minecraft:blue_dye", 1, " X ", "XWX", " X ",
                List.of("X", "ecotones:blueberries", "W", "minecraft:white_dye"));

        RecipeGen.shaped("turpentine", "ecotones:turpentine", 1, " X ", "XJX", " X ",
                List.of("X", "ecotones:pine_sap", "J", "ecotones:jar"));

        RecipeGen.shaped("treetap", "ecotones:treetap", 1, " X ", "XJX", "X  ",
                List.of("X", "minecraft:iron_ingot", "J", "ecotones:jar"));

        RecipeGen.shaped("sap_distillery", "ecotones:sap_distillery", 1, "XJX", "XXX",
                List.of("X", "minecraft:iron_ingot", "J", "ecotones:jar"));

        RecipeGen.shaped("grindstone", "ecotones:grindstone", 1, "XXX", "XJX", "W W",
                List.of("W", "minecraft:stick", "J", "ecotones:jar", "X", "minecraft:cobblestone"));

        RecipeGen.shaped("fertilizer_spreader", "ecotones:fertilizer_spreader", 1, "W W", "WFW", "PPP",
                List.of("W", "minecraft:stick", "F", "ecotones:basic_fertilizer", "P", "#minecraft:planks"));

        RecipeGen.shaped("torch_s", "minecraft:torch", 2, "X", "S",
                List.of("S", "minecraft:stick", "X", "ecotones:sulfur"));

        RecipeGen.shaped("torch_p", "minecraft:torch", 1, "X", "S",
                List.of("S", "minecraft:stick", "X", "ecotones:phosphate"));
    }

    private static void crossBlock(String name, String localizedName) throws IOException {
        BlockstateGen.simple(name);
        BlockModelGen.cross(name);
        ItemModelGen.flat(name);

        LangFileGen.addBlock(name, localizedName);
        LangFileGen.addItemblock(name, localizedName);

        LootTableGen.dropsSelf(name);
    }

    public static class DataGenData {
        public int blockstates = 0;
        public int blockModels = 0;
        public int itemModels = 0;
        public int langs = 0;
        public int loottables = 0;
        public int recipes = 0;
        public int tags = 0;

        public void log() {
            LOGGER.info("Created " + blockstates + " blockstate jsons.");
            LOGGER.info("Created " + blockModels + " block model jsons.");
            LOGGER.info("Created " + itemModels + " item model jsons.");
            LOGGER.info("Updated " + langs + " lang entries.");
            LOGGER.info("Created " + loottables + " loot table jsons.");
            LOGGER.info("Created " + recipes + " recipe jsons.");
            LOGGER.info("Created " + tags + " tag jsons.");
            LOGGER.info("That's a total of " + (blockstates + blockModels + itemModels + loottables + recipes + tags) + " jsons.");
        }
    }
}
