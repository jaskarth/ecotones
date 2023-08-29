package com.jaskarth.ecotones.client.gui.advancement;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;

public class EcotonesAdvancementTab extends AdvancementTab {
    public EcotonesAdvancementTab(MinecraftClient client, AdvancementsScreen screen, AdvancementTabType type, int index, Advancement root, AdvancementDisplay display) {
        super(client, screen, type, index, root, display);
    }

    @Override
    public void render(DrawContext context, int x, int y) {
        if (!this.initialized) {
            this.originX = (double)(117 - (this.maxPanX + this.minPanX) / 2);
            this.originY = (double)(56 - (this.maxPanY + this.minPanY) / 2);
            this.initialized = true;
        }

        context.enableScissor(x, y, x + 234, y + 113);
        context.getMatrices().push();
        context.getMatrices().translate((float)x, (float)y, 0.0F);
        Identifier identifier = (Identifier) Objects.requireNonNullElse(this.display.getBackground(), TextureManager.MISSING_IDENTIFIER);
        int i = MathHelper.floor(this.originX);
        int j = MathHelper.floor(this.originY);
        int k = i % 16;
        int l = j % 16;

        for(int m = -1; m <= 15; ++m) {
            for(int n = -1; n <= 8; ++n) {
                context.drawTexture(identifier, k + 16 * m, l + 16 * n, 0.0F, 0.0F, 16, 16, 16, 16);
            }
        }

        renderLines(context, i, j, true, rootWidget);
        renderLines(context, i, j, false, rootWidget);
        this.rootWidget.renderWidgets(context, i, j);
        context.getMatrices().pop();
        context.disableScissor();
    }

    private void renderLines(DrawContext context, int x, int y, boolean border, AdvancementWidget widget) {
        if (widget.parent != null) {
            if (widget.parent.getX() < widget.getX()) {
                int i = x + widget.parent.getX() + 13;
                int j = x + widget.parent.getX() + 26 + 4;
                int k = y + widget.parent.getY() + 13;
                int l = x + widget.getX() + 13;
                int m = y + widget.getY() + 13;

                int n = border ? -16777216 : -1;

                if (border) {
                    context.drawHorizontalLine(j, i, k - 1, n);
                    context.drawHorizontalLine(j + 1, i, k, n);
                    context.drawHorizontalLine(j, i, k + 1, n);
                    context.drawHorizontalLine(l, j - 1, m - 1, n);
                    context.drawHorizontalLine(l, j - 1, m, n);
                    context.drawHorizontalLine(l, j - 1, m + 1, n);
                    context.drawVerticalLine(j - 1, m, k, n);
                    context.drawVerticalLine(j + 1, m, k, n);
                } else {
                    context.drawHorizontalLine(j, i, k, n);
                    context.drawHorizontalLine(l, j, m, n);
                    context.drawVerticalLine(j, m, k, n);
                }
            } else if (widget.parent.getX() > widget.getX()) {
                int i = x + widget.parent.getX() + 13;
                int j = x + widget.parent.getX() - 8;
                int k = y + widget.parent.getY() + 13;
                int l = x + widget.getX() + 13;
                int m = y + widget.getY() + 13;

                int n = border ? -16777216 : -1;

                if (border) {
                    context.drawHorizontalLine(j, i, k - 1, n);
                    context.drawHorizontalLine(j + 1, i, k, n);
                    context.drawHorizontalLine(j, i, k + 1, n);
                    context.drawHorizontalLine(l, j - 1, m - 1, n);
                    context.drawHorizontalLine(l, j - 1, m, n);
                    context.drawHorizontalLine(l, j - 1, m + 1, n);
                    context.drawVerticalLine(j - 1, m, k, n);
                    context.drawVerticalLine(j + 1, m, k, n);
                } else {
                    context.drawHorizontalLine(j, i, k, n);
                    context.drawHorizontalLine(l, j, m, n);
                    context.drawVerticalLine(j, m, k, n);
                }
            } else if (widget.parent.getX() == widget.getX()) {
                int j = x + widget.parent.getX() + 15;
                int k = y + widget.parent.getY() + 15;
                int m = y + widget.getY() + 15;

                int n = border ? -16777216 : -1;

                if (border) {
                    context.drawVerticalLine(j - 1, m, k, n);
                    context.drawVerticalLine(j + 1, m, k, n);
                } else {
                    context.drawVerticalLine(j, m, k, n);
                }
            }
        }

        for(AdvancementWidget advancementWidget : widget.children) {
            renderLines(context, x, y, border, advancementWidget);
        }

    }

    @Override
    public void move(double offsetX, double offsetY) {
//        if (this.maxPanX - this.minPanX > 234) {
            this.originX = this.originX + offsetX;//MathHelper.clamp(this.originX + offsetX, (double)(-(this.maxPanX - 234)), 0.0D);
//        }

//        if (this.maxPanY - this.minPanY > 113) {
            this.originY = this.originY + offsetY;//MathHelper.clamp(this.originY + offsetY, (double)(-(this.maxPanY - 113)), 0.0D);
//        }

    }


}
