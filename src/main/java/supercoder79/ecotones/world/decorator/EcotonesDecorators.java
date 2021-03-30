package supercoder79.ecotones.world.decorator;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.SimpleTreeDecorationData;
import supercoder79.ecotones.api.TreeGenerationConfig;

public final class EcotonesDecorators {
    public static final Decorator<ShrubDecoratorConfig> SHRUB_PLACEMENT_DECORATOR = new AnalyticShrubPlacementDecorator(ShrubDecoratorConfig.CODEC);
    public static final Decorator<NopeDecoratorConfig> DRAINAGE_DECORATOR = new DrainageSurfaceDecorator(NopeDecoratorConfig.CODEC);
    public static final Decorator<TreeGenerationConfig.DecorationData> TREE_DECORATOR = new AnalyticTreePlacementDecorator(TreeGenerationConfig.DecorationData.CODEC);
    public static final Decorator<SimpleTreeDecorationData> SIMPLE_TREE_DECORATOR = new SimpleTreePlacementDecorator(SimpleTreeDecorationData.CODEC);
    public static final Decorator<SimpleTreeDecorationData> REVERSE_QUALITY_TREE_DECORATOR = new ReverseTreePlacementDecorator(SimpleTreeDecorationData.CODEC);
    public static final Decorator<NopeDecoratorConfig> ABOVE_QUALITY = new AboveQualityDecorator(NopeDecoratorConfig.CODEC);
    public static final Decorator<NopeDecoratorConfig> ROCKINESS = new SoilRockinessDecorator(NopeDecoratorConfig.CODEC);
    public static final Decorator<ChanceDecoratorConfig> LARGE_ROCK = new LargeRockDecorator(ChanceDecoratorConfig.CODEC);
    public static final Decorator<ShrubDecoratorConfig> BLUEBERRY_BUSH = new BlueberryBushDecorator(ShrubDecoratorConfig.CODEC);
    public static final Decorator<ShrubDecoratorConfig> DUCK_NEST = new DuckNestDecorator(ShrubDecoratorConfig.CODEC);
    public static final Decorator<ShrubDecoratorConfig> ROSEMARY = new RosemaryDecorator(ShrubDecoratorConfig.CODEC);

    public static void init() {
        register("shrub_placement", SHRUB_PLACEMENT_DECORATOR);
        register("drainage", DRAINAGE_DECORATOR);
        register("tree", TREE_DECORATOR);
        register("simple_tree", SIMPLE_TREE_DECORATOR);
        register("reverse_quality_tree", REVERSE_QUALITY_TREE_DECORATOR);
        register("above_quality", ABOVE_QUALITY);
        register("rockiness", ROCKINESS);
        register("large_rock", LARGE_ROCK);
        register("blueberry_bush", BLUEBERRY_BUSH);
        register("duck_nest", DUCK_NEST);
        register("rosemary", ROSEMARY);
    }

    private static void register(String name, Decorator<?> decorator) {
        Registry.register(Registry.DECORATOR, Ecotones.id(name), decorator);
    }
}
