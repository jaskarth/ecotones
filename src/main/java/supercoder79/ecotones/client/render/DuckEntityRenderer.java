package supercoder79.ecotones.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import supercoder79.ecotones.client.model.DuckEntityModel;
import supercoder79.ecotones.client.model.EcotonesModelLayers;
import supercoder79.ecotones.entity.DuckEntity;

@Environment(EnvType.CLIENT)
public class DuckEntityRenderer extends MobEntityRenderer<DuckEntity, DuckEntityModel<DuckEntity>> {
    private static final Identifier TEXTURE = new Identifier("ecotones", "textures/entity/duck.png");

    public DuckEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DuckEntityModel<>(context.getPart(EcotonesModelLayers.DUCK)), 0.3F);
    }

    @Override
    public Identifier getTexture(DuckEntity entity) {
        return TEXTURE;
    }

    @Override
    protected float getAnimationProgress(DuckEntity entity, float delta) {
        float flapProgress = MathHelper.lerp(delta, entity.prevFlapProgress, entity.flapProgress);
        float wingDeviation = MathHelper.lerp(delta, entity.prevMaxWingDeviation, entity.maxWingDeviation);
        return (MathHelper.sin(flapProgress) + 1.0F) * wingDeviation;
    }
}
