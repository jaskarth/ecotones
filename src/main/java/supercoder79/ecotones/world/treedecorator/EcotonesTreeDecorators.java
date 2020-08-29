package supercoder79.ecotones.world.treedecorator;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.TreeDecoratorType;
import supercoder79.ecotones.mixin.TreeDecoratorTypeAccessor;

public class EcotonesTreeDecorators {
    public static TreeDecoratorType<PineconeTreeDecorator> PINECONES;
    public static TreeDecoratorType<LeafVineTreeDecorator> LEAF_VINE;

    public static void init() {
        PINECONES = Registry.register(Registry.TREE_DECORATOR_TYPE, new Identifier("ecotones", "pinecone_decorator"), TreeDecoratorTypeAccessor.createTreeDecoratorType(PineconeTreeDecorator.CODEC));
        LEAF_VINE = Registry.register(Registry.TREE_DECORATOR_TYPE, new Identifier("ecotones", "leaf_vine_decorator"), TreeDecoratorTypeAccessor.createTreeDecoratorType(LeafVineTreeDecorator.CODEC));
    }
}
