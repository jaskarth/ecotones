package supercoder79.ecotones.client.gui.advancement;

import net.minecraft.advancement.Advancement;
import net.minecraft.util.Identifier;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.util.Vec2d;
import supercoder79.ecotones.util.Vec2i;

import java.util.*;

public final class EcotonesAdvancementPositioning {
    private static final Map<Identifier, Vec2d> POSITIONS = new HashMap<>();

    static {
        POSITIONS.put(Ecotones.id("ecotones/root"), new Vec2d(0, 0));
        POSITIONS.put(Ecotones.id("ecotones/get_blueberries"), new Vec2d(-0.8, 1.2));
        POSITIONS.put(Ecotones.id("ecotones/get_blueberry_jam"), new Vec2d(-0.8, 2.4));
        POSITIONS.put(Ecotones.id("ecotones/get_coconut"), new Vec2d(1.2, -3));
        POSITIONS.put(Ecotones.id("ecotones/get_hazelnut"), new Vec2d(1.2, -2));
        POSITIONS.put(Ecotones.id("ecotones/get_rosemary"), new Vec2d(1.2, -1));
        POSITIONS.put(Ecotones.id("ecotones/get_sap_ball"), new Vec2d(1.2, 1));
        POSITIONS.put(Ecotones.id("ecotones/get_maple_sap"), new Vec2d(-0.8, -2));
        POSITIONS.put(Ecotones.id("ecotones/get_maple_syrup"), new Vec2d(-1.8, -2));
    }

    public static void position(Advancement advancement) {
        List<Advancement> advs = new ArrayList<>();
        Deque<Advancement> stack = new LinkedList<>();

        stack.push(advancement);

        while (!stack.isEmpty()) {
            Advancement adv = stack.pop();

            for (Advancement child : adv.getChildren()) {
                stack.add(child);
            }

            if (!advs.contains(adv)) {
                advs.add(adv);
            }
        }

        for (Advancement adv : advs) {
            Vec2d vec = POSITIONS.get(adv.getId());
            if (vec == null) {
                Ecotones.LOGGER.error("MISSING POSITION FOR " + adv.getId());
            } else {
                adv.getDisplay().setPos((float) vec.x(), (float) vec.y());
            }
        }
    }
}
