package supercoder79.ecotones.decorator;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.Decorator;

public class EcotonesDecorators {
    public static Decorator<ShrubDecoratorConfig> SHRUB_PLACEMENT_DECORATOR;

    public static void init() {
        SHRUB_PLACEMENT_DECORATOR = Registry.register(Registry.DECORATOR, new Identifier("ecotones", "shrub_placement_decorator"), new AnalyticShrubPlacementDecorator(ShrubDecoratorConfig::deserialize));
    }
}
