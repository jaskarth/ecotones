package supercoder79.ecotones.api.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import java.util.HashMap;
import java.util.Map;

@Deprecated // Scheduled for removal as soon as i update fabric api
@Environment(EnvType.CLIENT)
public class ModelDataRegistry {
    private static final Map<EntityModelLayer, TexturedModelData> MODELS = new HashMap<>();

    public static void register(EntityModelLayer layer, TexturedModelData data) {
        MODELS.put(layer, data);
    }

    public static Map<EntityModelLayer, TexturedModelData> getModels() {
        return MODELS;
    }
}
