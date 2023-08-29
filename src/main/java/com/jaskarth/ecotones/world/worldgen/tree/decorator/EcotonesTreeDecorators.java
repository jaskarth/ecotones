package com.jaskarth.ecotones.world.worldgen.tree.decorator;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.mixin.TreeDecoratorTypeAccessor;
import com.jaskarth.ecotones.util.RegistryReport;

public final class EcotonesTreeDecorators {
    public static final TreeDecoratorType<PineconeTreeDecorator> PINECONES = TreeDecoratorTypeAccessor.createTreeDecoratorType(PineconeTreeDecorator.CODEC);
    public static final TreeDecoratorType<LeafVineTreeDecorator> LEAF_VINE = TreeDecoratorTypeAccessor.createTreeDecoratorType(LeafVineTreeDecorator.CODEC);
    public static final TreeDecoratorType<LichenTreeDecorator> LICHEN = TreeDecoratorTypeAccessor.createTreeDecoratorType(LichenTreeDecorator.CODEC);
    public static final TreeDecoratorType<LeafPileTreeDecorator> LEAF_PILE = TreeDecoratorTypeAccessor.createTreeDecoratorType(LeafPileTreeDecorator.CODEC);

    public static void init() {
        register("pinecone", PINECONES);
        register("leaf_vine", LEAF_VINE);
        register("lichen", LICHEN);
        register("leaf_pile", LEAF_PILE);
    }

    private static void register(String name, TreeDecoratorType<?> type) {
        Registry.register(Registries.TREE_DECORATOR_TYPE, Ecotones.id(name), type);
        RegistryReport.increment("Tree Decorator");
    }
}
