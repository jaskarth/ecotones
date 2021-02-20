package supercoder79.ecotones.mixin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import supercoder79.ecotones.api.client.ModelDataRegistry;

import java.util.Map;

@Mixin(EntityModels.class)
public class MixinEntityModels {
    @Redirect(method = "getModels", at = @At(target = "Lcom/google/common/collect/ImmutableMap;builder()Lcom/google/common/collect/ImmutableMap$Builder;", value = "INVOKE"))
    private static ImmutableMap.Builder<EntityModelLayer, TexturedModelData> get() {
        ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder = ImmutableMap.builder();

        for (Map.Entry<EntityModelLayer, TexturedModelData> entry : ModelDataRegistry.getModels().entrySet()) {
            builder.put(entry.getKey(), entry.getValue());
        }

        return builder;
    }
}
