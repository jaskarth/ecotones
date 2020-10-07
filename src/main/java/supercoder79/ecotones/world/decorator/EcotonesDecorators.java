package supercoder79.ecotones.world.decorator;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.api.TreeGenerationConfig;

public class EcotonesDecorators {
    public static Decorator<ShrubDecoratorConfig> SHRUB_PLACEMENT_DECORATOR;
    public static Decorator<NopeDecoratorConfig> DRAINAGE_DECORATOR;
    public static Decorator<TreeGenerationConfig.DecorationData> TREE_DECORATOR;
    public static Decorator<SimpleTreeDecorationData> SIMPLE_TREE_DECORATOR;
    public static Decorator<SimpleTreeDecorationData> REVERSE_QUALITY_TREE_DECORATOR;
    public static Decorator<NopeDecoratorConfig> ABOVE_QUALITY;
    public static Decorator<NopeDecoratorConfig> ROCKINESS;

    public static void init() {
        SHRUB_PLACEMENT_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "shrub_placement_decorator"), new AnalyticShrubPlacementDecorator(ShrubDecoratorConfig.CODEC));
        DRAINAGE_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "drainage_decorator"), new DrainageSurfaceDecorator(NopeDecoratorConfig.field_24891));
        TREE_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "tree_decorator"), new AnalyticTreePlacementDecorator(TreeGenerationConfig.DecorationData.CODEC));
        ABOVE_QUALITY = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "above_quality"), new AboveQualityDecorator(NopeDecoratorConfig.field_24891));
        ROCKINESS = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "rockiness"), new SoilRockinessDecorator(NopeDecoratorConfig.field_24891));
        SIMPLE_TREE_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "simple_tree_decorator"), new SimpleTreePlacementDecorator(SimpleTreeDecorationData.CODEC));
        REVERSE_QUALITY_TREE_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "reverse_quality_tree_decorator"), new ReverseTreePlacementDecorator(SimpleTreeDecorationData.CODEC));
    }
}
