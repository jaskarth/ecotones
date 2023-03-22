package supercoder79.ecotones.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.screen.GrindstoneScreenHandler;

public class GrindstoneScreen extends HandledScreen<GrindstoneScreenHandler> {
    private static final Identifier TEXTURE = Ecotones.id("textures/gui/grinder.png");

    public GrindstoneScreen(GrindstoneScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    protected void init() {
        super.init();

        // Center title
        this.titleX = 10;
        this.titleY = 0;

        // GUI Height
        this.backgroundHeight = 166 + 15;
        this.playerInventoryTitleY = this.backgroundHeight - 91 - 8;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        // Draw gui
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        int progress = this.handler.getProgress();

        // Draw progress bar
        int percent = (int)(30 * ((double)progress / 200.0));
        if (percent > 0) {
            this.drawTexture(matrices, x + 89, y + 30, 176, 14, 14, percent + 1);
        }

        // Draw fire
        if (this.handler.getBurnTimeMax() > 0) {
            int burnTime = this.handler.getBurnTime();
            int scaledBurnTime = (burnTime * 14) / this.handler.getBurnTimeMax();
            this.drawTexture(matrices, x + 152, y + 57 + (13 - scaledBurnTime), 176, 13 - scaledBurnTime, 14, scaledBurnTime + 1);
        }

        // TODO: ghost textures
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);

        // TODO: move these to a custom 9patch so the bubble is dynamic

        if (this.handler.getMainChance() > 0) {
            this.textRenderer.draw(matrices, this.handler.getMainChance() + "%", 102, 67 - 7, 4210752);
        }

        if (this.handler.getAltChance() > 0) {
            this.textRenderer.draw(matrices, this.handler.getAltChance() + "%", 25, 54 - 7, 4210752);
        }
    }
}
