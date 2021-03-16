package supercoder79.ecotones.util.deco;

import net.minecraft.block.Blocks;

import java.util.*;

public final class BlockDecorations {
    private static final Map<BlockAttachment, Map<DecorationCategory, List<BlockDecoration>>> REGISTRY = new HashMap<>();

    public static void init() {
        register(new DefaultBlockDecoration(Blocks.CRAFTING_TABLE.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.TABLES);
        register(new DefaultBlockDecoration(Blocks.FLETCHING_TABLE.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.TABLES);
        register(new DefaultBlockDecoration(Blocks.CARTOGRAPHY_TABLE.getDefaultState()), BlockAttachment.FLOOR, DecorationCategory.TABLES);
        register(new DefaultBlockDecoration(Blocks.TORCH.getDefaultState()), BlockAttachment.WALL, DecorationCategory.LAMPS);
        register(new DefaultBlockDecoration(Blocks.BELL.getDefaultState()), BlockAttachment.CEILING, DecorationCategory.LAMPS);
    }

    private static void register(BlockDecoration decoration, BlockAttachment attachment, DecorationCategory category) {
        Map<DecorationCategory, List<BlockDecoration>> fromCategory = REGISTRY.getOrDefault(attachment, new HashMap<>());
        List<BlockDecoration> decorations = fromCategory.getOrDefault(category, new ArrayList<>());

        decorations.add(decoration);

        fromCategory.put(category, decorations);
        REGISTRY.put(attachment, fromCategory);
    }

    public static BlockDecoration get(Random random, BlockAttachment attachment) {
        Map<DecorationCategory, List<BlockDecoration>> fromCategory = REGISTRY.getOrDefault(attachment, new HashMap<>());
        DecorationCategory category = new ArrayList<>(fromCategory.keySet()).get(random.nextInt(fromCategory.keySet().size()));
        List<BlockDecoration> decorations = fromCategory.getOrDefault(category, new ArrayList<>());

        if (decorations.size() == 0) {
            throw new IllegalStateException("No registered blocks for category " + category + " of attachement " + attachment);
        }

        return decorations.get(random.nextInt(decorations.size()));
    }
}
