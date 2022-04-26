package supercoder79.ecotones.client.gui.advancement;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class EcotonesAdvancementTab extends AdvancementTab {
    public EcotonesAdvancementTab(MinecraftClient client, AdvancementsScreen screen, AdvancementTabType type, int index, Advancement root, AdvancementDisplay display) {
        super(client, screen, type, index, root, display);
    }

    @Override
    public void render(MatrixStack matrices) {
        if (!this.initialized) {
            this.originX = (double)(117 - (this.maxPanX + this.minPanX) / 2);
            this.originY = (double)(56 - (this.maxPanY + this.minPanY) / 2);
            this.initialized = true;
        }

        matrices.push();
        matrices.translate(0.0D, 0.0D, 950.0D);
        RenderSystem.enableDepthTest();
        RenderSystem.colorMask(false, false, false, false);
        fill(matrices, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        matrices.translate(0.0D, 0.0D, -950.0D);
        RenderSystem.depthFunc(518);
        fill(matrices, 234, 113, 0, 0, -16777216);
        RenderSystem.depthFunc(515);
        Identifier identifier = this.display.getBackground();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        if (identifier != null) {
            RenderSystem.setShaderTexture(0, identifier);
        } else {
            RenderSystem.setShaderTexture(0, TextureManager.MISSING_IDENTIFIER);
        }

        int i = MathHelper.floor(this.originX);
        int j = MathHelper.floor(this.originY);
        int k = i % 16;
        int l = j % 16;

        for(int m = -1; m <= 15; ++m) {
            for(int n = -1; n <= 8; ++n) {
                drawTexture(matrices, k + 16 * m, l + 16 * n, 0.0F, 0.0F, 16, 16, 16, 16);
            }
        }

        matrices.push();
        matrices.scale(0.75F, 0.75F, 0.75F);
        renderLines(matrices, i, j, true, rootWidget);
        renderLines(matrices, i, j, false, rootWidget);
        this.rootWidget.renderWidgets(matrices, i, j);
        matrices.pop();

        RenderSystem.depthFunc(518);
        matrices.translate(0.0D, 0.0D, -950.0D);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrices, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthFunc(515);
        matrices.pop();
    }

    private void renderLines(MatrixStack matrices, int x, int y, boolean border, AdvancementWidget widget) {
        if (widget.parent != null) {
            if (widget.parent.getX() < widget.getX()) {
                int i = x + widget.parent.getX() + 13;
                int j = x + widget.parent.getX() + 26 + 4;
                int k = y + widget.parent.getY() + 13;
                int l = x + widget.getX() + 13;
                int m = y + widget.getY() + 13;

                int n = border ? -16777216 : -1;

                if (border) {
                    this.drawHorizontalLine(matrices, j, i, k - 1, n);
                    this.drawHorizontalLine(matrices, j + 1, i, k, n);
                    this.drawHorizontalLine(matrices, j, i, k + 1, n);
                    this.drawHorizontalLine(matrices, l, j - 1, m - 1, n);
                    this.drawHorizontalLine(matrices, l, j - 1, m, n);
                    this.drawHorizontalLine(matrices, l, j - 1, m + 1, n);
                    this.drawVerticalLine(matrices, j - 1, m, k, n);
                    this.drawVerticalLine(matrices, j + 1, m, k, n);
                } else {
                    this.drawHorizontalLine(matrices, j, i, k, n);
                    this.drawHorizontalLine(matrices, l, j, m, n);
                    this.drawVerticalLine(matrices, j, m, k, n);
                }
            } else if (widget.parent.getX() > widget.getX()) {
                int i = x + widget.parent.getX() + 13;
                int j = x + widget.parent.getX() - 8;
                int k = y + widget.parent.getY() + 13;
                int l = x + widget.getX() + 13;
                int m = y + widget.getY() + 13;

                int n = border ? -16777216 : -1;

                if (border) {
                    this.drawHorizontalLine(matrices, j, i, k - 1, n);
                    this.drawHorizontalLine(matrices, j + 1, i, k, n);
                    this.drawHorizontalLine(matrices, j, i, k + 1, n);
                    this.drawHorizontalLine(matrices, l, j - 1, m - 1, n);
                    this.drawHorizontalLine(matrices, l, j - 1, m, n);
                    this.drawHorizontalLine(matrices, l, j - 1, m + 1, n);
                    this.drawVerticalLine(matrices, j - 1, m, k, n);
                    this.drawVerticalLine(matrices, j + 1, m, k, n);
                } else {
                    this.drawHorizontalLine(matrices, j, i, k, n);
                    this.drawHorizontalLine(matrices, l, j, m, n);
                    this.drawVerticalLine(matrices, j, m, k, n);
                }
            } else if (widget.parent.getX() == widget.getX()) {
                int j = x + widget.parent.getX() + 15;
                int k = y + widget.parent.getY() + 15;
                int m = y + widget.getY() + 15;

                int n = border ? -16777216 : -1;

                if (border) {
                    this.drawVerticalLine(matrices, j - 1, m, k, n);
                    this.drawVerticalLine(matrices, j + 1, m, k, n);
                } else {
                    this.drawVerticalLine(matrices, j, m, k, n);
                }
            }
        }

        for(AdvancementWidget advancementWidget : widget.children) {
            renderLines(matrices, x, y, border, advancementWidget);
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
