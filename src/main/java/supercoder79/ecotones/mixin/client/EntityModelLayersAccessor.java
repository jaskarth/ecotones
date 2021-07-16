package supercoder79.ecotones.mixin.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(EntityModelLayers.class)
public interface EntityModelLayersAccessor {
    @Accessor(value = "LAYERS")
    static Set<EntityModelLayer> getLayers() {
        throw new UnsupportedOperationException();
    }
}
