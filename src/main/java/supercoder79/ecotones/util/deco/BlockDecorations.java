package supercoder79.ecotones.util.deco;

import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;

import java.util.*;

public final class BlockDecorations {
    private static final Map<BlockAttachment, Map<DecorationCategory, List<BlockDecoration>>> REGISTRY = new HashMap<>();

    public static void init() {
        register(new DefaultBlockDecoration(Blocks.CRAFTING_TABLE.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.TABLES);
        register(new DefaultBlockDecoration(Blocks.FLETCHING_TABLE.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.TABLES);
        register(new DefaultBlockDecoration(Blocks.CARTOGRAPHY_TABLE.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.TABLES);
        register(new DefaultBlockDecoration(Blocks.STONECUTTER.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.TABLES);
        register(new DefaultBlockDecoration(Blocks.LOOM.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.TABLES);

        register(new DefaultBlockDecoration(Blocks.FURNACE.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.INDUSTRY);
        register(new DefaultBlockDecoration(Blocks.BLAST_FURNACE.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.INDUSTRY);
        register(new DefaultBlockDecoration(Blocks.SMOKER.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.INDUSTRY);
        register(new DefaultBlockDecoration(Blocks.ANVIL.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.INDUSTRY);
        register(new DefaultBlockDecoration(Blocks.SMITHING_TABLE.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.INDUSTRY);
        register(new DefaultBlockDecoration(Blocks.GRINDSTONE.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.INDUSTRY);

        register(new DefaultBlockDecoration(Blocks.TORCH.getDefaultState()), BlockAttachment.WALL, DecorationCategory.LIGHTS);
        register(new DefaultBlockDecoration(Blocks.LANTERN.getDefaultState().with(LanternBlock.HANGING, true)), BlockAttachment.CEILING, DecorationCategory.LIGHTS);
        register(new DefaultBlockDecoration(Blocks.LANTERN.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.LIGHTS);
    }

    private static void register(BlockDecoration decoration, BlockAttachment attachment, DecorationCategory category) {
        Map<DecorationCategory, List<BlockDecoration>> fromCategory = REGISTRY.getOrDefault(attachment, new HashMap<>());
        List<BlockDecoration> decorations = fromCategory.getOrDefault(category, new ArrayList<>());

        decorations.add(decoration);

        fromCategory.put(category, decorations);
        REGISTRY.put(attachment, fromCategory);
    }

    public static BlockDecoration get(Random random, BlockAttachment attachment, DecorationCategory category) {
        Map<DecorationCategory, List<BlockDecoration>> fromCategory = REGISTRY.getOrDefault(attachment, new HashMap<>());
        List<BlockDecoration> decorations = fromCategory.getOrDefault(category, new ArrayList<>());

        if (decorations.size() == 0) {
            throw new IllegalStateException("No registered blocks for category " + category + " of attachment " + attachment);
        }

        return decorations.get(random.nextInt(decorations.size()));
    }
}
