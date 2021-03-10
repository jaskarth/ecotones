package supercoder79.ecotones.world.decorator;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.api.TreeGenerationConfig;

public final class EcotonesDecorators {
    public static Decorator<ShrubDecoratorConfig> SHRUB_PLACEMENT_DECORATOR;
    public static Decorator<NopeDecoratorConfig> DRAINAGE_DECORATOR;
    public static Decorator<TreeGenerationConfig.DecorationData> TREE_DECORATOR;
    public static Decorator<SimpleTreeDecorationData> SIMPLE_TREE_DECORATOR;
    public static Decorator<SimpleTreeDecorationData> REVERSE_QUALITY_TREE_DECORATOR;
    public static Decorator<NopeDecoratorConfig> ABOVE_QUALITY;
    public static Decorator<NopeDecoratorConfig> ROCKINESS;
    public static Decorator<ChanceDecoratorConfig> LARGE_ROCK;
    public static Decorator<ShrubDecoratorConfig> BLUEBERRY_BUSH;
    public static Decorator<ShrubDecoratorConfig> DUCK_NEST;
    public static Decorator<ShrubDecoratorConfig> ROSEMARY;

    public static void init() {
        // TODO: cleanup
        SHRUB_PLACEMENT_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "shrub_placement"), new AnalyticShrubPlacementDecorator(ShrubDecoratorConfig.CODEC));
        DRAINAGE_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "drainage"), new DrainageSurfaceDecorator(NopeDecoratorConfig.CODEC));
        TREE_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "tree"), new AnalyticTreePlacementDecorator(TreeGenerationConfig.DecorationData.CODEC));
        ABOVE_QUALITY = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "above_quality"), new AboveQualityDecorator(NopeDecoratorConfig.CODEC));
        ROCKINESS = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "rockiness"), new SoilRockinessDecorator(NopeDecoratorConfig.CODEC));
        SIMPLE_TREE_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "simple_tree"), new SimpleTreePlacementDecorator(SimpleTreeDecorationData.CODEC));
        REVERSE_QUALITY_TREE_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "reverse_quality_tree"), new ReverseTreePlacementDecorator(SimpleTreeDecorationData.CODEC));
        LARGE_ROCK = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "large_rock"), new LargeRockDecorator(ChanceDecoratorConfig.CODEC));
        BLUEBERRY_BUSH = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "blueberry_bush"), new BlueberryBushDecorator(ShrubDecoratorConfig.CODEC));
        DUCK_NEST = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "duck_nest"), new DuckNestDecorator(ShrubDecoratorConfig.CODEC));
        ROSEMARY = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "rosemary"), new RosemaryDecorator(ShrubDecoratorConfig.CODEC));
    }
}
