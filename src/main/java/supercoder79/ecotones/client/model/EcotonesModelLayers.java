package supercoder79.ecotones.client.model;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import supercoder79.ecotones.mixin.client.EntityModelLayersAccessor;

public class EcotonesModelLayers {
    public static final EntityModelLayer DUCK = new EntityModelLayer(new Identifier("ecotones", "duck"), "main");

    public static void init() {
        EntityModelLayersAccessor.getLayers().add(DUCK);
    }
}
