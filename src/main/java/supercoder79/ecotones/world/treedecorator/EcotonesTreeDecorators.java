package supercoder79.ecotones.world.treedecorator;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.tree.TreeDecoratorType;
import supercoder79.ecotones.mixin.TreeDecoratorTypeAccessor;

public final class EcotonesTreeDecorators {
    public static TreeDecoratorType<PineconeTreeDecorator> PINECONES;
    public static TreeDecoratorType<LeafVineTreeDecorator> LEAF_VINE;
    public static TreeDecoratorType<LichenTreeDecorator> LICHEN;
    public static TreeDecoratorType<LeafPileTreeDecorator> LEAF_PILE;

    public static void init() {
        PINECONES = Registry.register(Registry.TREE_DECORATOR_TYPE, new Identifier("ecotones", "pinecone"), TreeDecoratorTypeAccessor.createTreeDecoratorType(PineconeTreeDecorator.CODEC));
        LEAF_VINE = Registry.register(Registry.TREE_DECORATOR_TYPE, new Identifier("ecotones", "leaf_vine"), TreeDecoratorTypeAccessor.createTreeDecoratorType(LeafVineTreeDecorator.CODEC));
        LICHEN = Registry.register(Registry.TREE_DECORATOR_TYPE, new Identifier("ecotones", "lichen"), TreeDecoratorTypeAccessor.createTreeDecoratorType(LichenTreeDecorator.CODEC));
        LEAF_PILE = Registry.register(Registry.TREE_DECORATOR_TYPE, new Identifier("ecotones", "leaf_pile"), TreeDecoratorTypeAccessor.createTreeDecoratorType(LeafPileTreeDecorator.CODEC));
    }
}
