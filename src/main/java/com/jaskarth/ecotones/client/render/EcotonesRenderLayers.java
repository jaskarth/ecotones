package com.jaskarth.ecotones.client.render;

import com.jaskarth.ecotones.client.tex.CooktopTextureGenerator;
import net.minecraft.client.render.*;
import com.jaskarth.ecotones.client.tex.SyrupTextureGenerator;

import java.util.OptionalDouble;

import static net.minecraft.client.render.RenderPhase.*;

public final class EcotonesRenderLayers {
    public static final RenderLayer SYRUP_TEX_LAYER = RenderLayer.of("sap_distillery", VertexFormats.POSITION_COLOR_TEXTURE,
            VertexFormat.DrawMode.QUADS, 256, false, false,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(POSITION_COLOR_TEXTURE_PROGRAM)
                    .texture(new RenderPhase.Texture(SyrupTextureGenerator.ID, false, false))
                    .build(false)
    );

    public static final RenderLayer COOKTOP_FACE = RenderLayer.of(
            "cooktop_face",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            2097152,
            true,
            false,
            RenderLayer.MultiPhaseParameters.builder()
                    .lightmap(ENABLE_LIGHTMAP)
                    .program(SOLID_PROGRAM)
                    .layering(POLYGON_OFFSET_LAYERING)
                    .texture(new RenderPhase.Texture(CooktopTextureGenerator.ID, false, false))
                    .build(true)
    );

    public static final RenderLayer SOLID_LINES = RenderLayer.of(
            "solid_lines",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            VertexFormat.DrawMode.LINES,
            2097152,
            true,
            false,
            RenderLayer.MultiPhaseParameters.builder()
                    .lightmap(ENABLE_LIGHTMAP)
                    .program(LINES_PROGRAM)
                    .lineWidth(new RenderPhase.LineWidth(OptionalDouble.empty()))
                    .texture(MIPMAP_BLOCK_ATLAS_TEXTURE)
                    .writeMaskState(ALL_MASK)
                    .cull(DISABLE_CULLING)
                    .build(true)
    );
}
