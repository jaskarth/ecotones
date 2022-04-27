package supercoder79.ecotones.world.river.deco;

import net.minecraft.world.gen.feature.util.FeatureContext;

public class RiverDecorator {
    public static RiverDecorator EMPTY = new RiverDecorator();

    private final RiverDecorationCollector decorations = new RiverDecorationCollector();

    public RiverDecorationCollector getDecorations() {
        return decorations;
    }

    public void decorate(boolean openToAir, int minimumDist, FeatureContext<?> context) {
        for (RiverDecorationCollector.Entry feature : decorations.features()) {
            if (feature.needsOpenToAir() && !openToAir) {
                continue;
            }

            if (feature.minimumDist() < minimumDist) {
                continue;
            }

            feature.feature().generateUnregistered(context.getWorld(), context.getGenerator(), context.getRandom(), context.getOrigin());
        }
    }
}
