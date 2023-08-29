package com.jaskarth.ecotones.client.render;

import net.minecraft.client.render.*;
import com.jaskarth.ecotones.client.tex.SyrupTextureGenerator;

public final class EcotonesRenderLayers {
    public static final RenderLayer SYRUP_TEX_LAYER = RenderLayer.of("sap_distillery", VertexFormats.POSITION_COLOR_TEXTURE,
            VertexFormat.DrawMode.QUADS, 256, false, false,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(RenderPhase.POSITION_COLOR_TEXTURE_PROGRAM)
                    .texture(new RenderPhase.Texture(SyrupTextureGenerator.ID, false, false))
                    .build(false));
}
