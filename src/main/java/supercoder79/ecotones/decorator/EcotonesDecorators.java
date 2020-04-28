package supercoder79.ecotones.decorator;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import supercoder79.ecotones.api.TreeGenerationConfig;

public class EcotonesDecorators {
    public static Decorator<ShrubDecoratorConfig> SHRUB_PLACEMENT_DECORATOR;
    public static Decorator<NopeDecoratorConfig> DRAINAGE_DECORATOR;
    public static Decorator<TreeGenerationConfig> TREE_DECORATOR;
    public static Decorator<NopeDecoratorConfig> ABOVE_QUALITY;
    public static Decorator<NopeDecoratorConfig> ROCKINESS;

    public static void init() {
        SHRUB_PLACEMENT_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "shrub_placement_decorator"), new AnalyticShrubPlacementDecorator(ShrubDecoratorConfig::deserialize));
        DRAINAGE_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "drainage_decorator"), new DrainageSurfaceDecorator(NopeDecoratorConfig::deserialize));
        TREE_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "tree_decorator"), new AnalyticTreePlacementDecorator(TreeGenerationConfig::deserialize));
        ABOVE_QUALITY = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "above_quality"), new AboveQualityDecorator(NopeDecoratorConfig::deserialize));
        ROCKINESS = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "rockiness"), new SoilRockinessDecorator(NopeDecoratorConfig::deserialize));
    }
}
