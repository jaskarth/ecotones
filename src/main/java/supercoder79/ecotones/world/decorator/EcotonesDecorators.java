package supercoder79.ecotones.world.decorator;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.util.RegistryReport;

public final class EcotonesDecorators {
    public static final EcotonesPlacementModifierType<ShrubPlacementDecorator> SHRUB_PLACEMENT_DECORATOR = EcotonesPlacementModifierType.make(ShrubPlacementDecorator.class, ShrubPlacementDecorator.CODEC);
    public static final EcotonesPlacementModifierType<DrainageSurfaceDecorator> DRAINAGE_DECORATOR = EcotonesPlacementModifierType.make(DrainageSurfaceDecorator.class,  DrainageSurfaceDecorator.CODEC);
    public static final EcotonesPlacementModifierType<TreePlacementDecorator> TREE_DECORATOR = EcotonesPlacementModifierType.make(TreePlacementDecorator.class, TreePlacementDecorator.CODEC);
    public static final EcotonesPlacementModifierType<SimpleTreePlacementDecorator> SIMPLE_TREE_DECORATOR = EcotonesPlacementModifierType.make(SimpleTreePlacementDecorator.class, SimpleTreePlacementDecorator.CODEC);
    public static final EcotonesPlacementModifierType<ReverseTreePlacementDecorator> REVERSE_QUALITY_TREE_DECORATOR = EcotonesPlacementModifierType.make(ReverseTreePlacementDecorator.class, ReverseTreePlacementDecorator.CODEC);
    public static final EcotonesPlacementModifierType<AboveQualityDecorator> ABOVE_QUALITY = EcotonesPlacementModifierType.make(AboveQualityDecorator.class, AboveQualityDecorator.CODEC);
    public static final EcotonesPlacementModifierType<SoilRockinessDecorator> ROCKINESS = EcotonesPlacementModifierType.make(SoilRockinessDecorator.class, SoilRockinessDecorator.CODEC);
    public static final EcotonesPlacementModifierType<LargeRockDecorator> LARGE_ROCK = EcotonesPlacementModifierType.make(LargeRockDecorator.class, LargeRockDecorator.CODEC);
    public static final EcotonesPlacementModifierType<BlueberryBushDecorator> BLUEBERRY_BUSH = EcotonesPlacementModifierType.make(BlueberryBushDecorator.class, BlueberryBushDecorator.CODEC);
    public static final EcotonesPlacementModifierType<DuckNestDecorator> DUCK_NEST = EcotonesPlacementModifierType.make(DuckNestDecorator.class, DuckNestDecorator.CODEC);
    public static final EcotonesPlacementModifierType<RosemaryDecorator> ROSEMARY = EcotonesPlacementModifierType.make(RosemaryDecorator.class, RosemaryDecorator.CODEC);

    public static final EcotonesPlacementModifierType<Spread32Decorator> SPREAD_32 = EcotonesPlacementModifierType.make(Spread32Decorator.class, Spread32Decorator.CODEC);
    public static final EcotonesPlacementModifierType<SpreadDoubleDecorator> SPREAD_DOUBLE = EcotonesPlacementModifierType.make(SpreadDoubleDecorator.class, SpreadDoubleDecorator.CODEC);
    public static final EcotonesPlacementModifierType<CountExtraDecorator> COUNT_EXTRA = EcotonesPlacementModifierType.make(CountExtraDecorator.class, CountExtraDecorator.CODEC);

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

        register("spread_32", SPREAD_32);
        register("spread_double", SPREAD_DOUBLE);
        register("count_extra", COUNT_EXTRA);
    }

    private static void register(String name, PlacementModifierType<?> decorator) {
        Registry.register(Registry.PLACEMENT_MODIFIER_TYPE, Ecotones.id(name), decorator);
        RegistryReport.increment("Decorator");
    }
}
