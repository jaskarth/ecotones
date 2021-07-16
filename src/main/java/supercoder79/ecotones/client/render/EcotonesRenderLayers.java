package supercoder79.ecotones.client.render;

import net.minecraft.client.render.*;
import supercoder79.ecotones.client.tex.SyrupTextureGenerator;

public final class EcotonesRenderLayers {
    public static final RenderLayer SYRUP_TEX_LAYER = RenderLayer.of("sap_distillery", VertexFormats.POSITION_COLOR_TEXTURE,
            VertexFormat.DrawMode.QUADS, 256, false, false,
            RenderLayer.MultiPhaseParameters.builder()
                    .shader(RenderPhase.POSITION_COLOR_TEXTURE_SHADER)
                    .texture(new RenderPhase.Texture(SyrupTextureGenerator.ID, false, false))
                    .build(false));
}
