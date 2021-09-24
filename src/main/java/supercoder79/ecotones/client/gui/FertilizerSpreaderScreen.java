package supercoder79.ecotones.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.screen.FertilizerSpreaderScreenHandler;

public class FertilizerSpreaderScreen extends HandledScreen<FertilizerSpreaderScreenHandler> {
    private static final Identifier TEXTURE = Ecotones.id("textures/gui/fertilizer_spreader.png");
    private static final Identifier WATER = new Identifier("minecraft", "textures/block/water_still.png");
    private static final int WATER_X_FRAC = 50 / 3;
    private static final int WATER_Y_FRAC = 52 / 3;

    public FertilizerSpreaderScreen(FertilizerSpreaderScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    protected void init() {
        super.init();

        // Center title
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
        this.titleY = 0;

        // GUI Height
        this.backgroundHeight = 166 + 15;
        this.playerInventoryTitleY = this.backgroundHeight - 91 - 8;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
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

        // Draw pointer on bar
        this.drawTexture(matrices, (int) (x + 110 + Math.min((this.handler.getPercent() / 100.0) * 52, 51)), y + 43, 176, 0, 11, 7);

        // Draw water
        Sprite sprite = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.WATER.getDefaultState()).getSprite();

        RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);

        for (int x0 = 0; x0 < 3; x0++) {
            for (int y0 = 0; y0 < 3; y0++) {
                drawWater(matrices.peek().getModel(), x + 10 + (x0 * WATER_X_FRAC), x + 10 + ((x0 + 1) * WATER_X_FRAC), y + 35 + (y0 * WATER_Y_FRAC), y + 35 + ((y0 + 1) * WATER_Y_FRAC), 1, sprite.getMinU(), sprite.getMaxU(), sprite.getMinV(), sprite.getMaxV());
            }
        }

        // Contain water in the vat area
        int zo = this.getZOffset();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);

        this.setZOffset(2);

        this.drawTexture(matrices, x + 8, y + 80, 176, 12, 8, 8);
        this.drawTexture(matrices, x + 52, y + 80, 186, 12, 8, 8);

        // Fertilizer

        int fertilizerAmt = (this.handler.getFertilizerAmount() * 33) / 20000;
        this.drawTexture(matrices, x + 10, y + 53 + (33 - fertilizerAmt), 176, 55 - fertilizerAmt, 48, fertilizerAmt);

        this.setZOffset(zo);

    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);

        int x = mouseX - (this.width - this.backgroundWidth) / 2;
        int y = mouseY - (this.height - this.backgroundHeight) / 2;

        // Syrup tooltip
        if (x >= 8 && x <= 59 && y >= 31 && y <= 87) {
            this.renderTooltip(matrices, new LiteralText(this.handler.getFertilizerAmount() + " / 20000 (Basic Fertilizer)"), x, y);
        }

        // Sap tooltip
        if (x >= 113 && x <= 168 && y >= 36 && y <= 48) {
            this.renderTooltip(matrices, new LiteralText(this.handler.getPercent() + "% (Basic Fertilizer max: 40%)"), x, y);
        }

        this.textRenderer.draw(matrices, "Farms: " + this.handler.getFarmCount(), 117, 58 - 8, 4210752);
        this.textRenderer.draw(matrices, "Water: " + this.handler.getWaterCount(), 117, 58 - 8 + 9, 4210752);
        this.textRenderer.draw(matrices, "Working...", 117, 58 - 8 + 18, 4210752);
    }

    // Status options: "Working!" "Full!" "Empty!"

    private static void drawWater(Matrix4f matrices, int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1) {
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferBuilder.vertex(matrices, (float)x0, (float)y1, (float)z).color(63, 118, 228, 255).texture(u0, v1).next();
        bufferBuilder.vertex(matrices, (float)x1, (float)y1, (float)z).color(63, 118, 228, 255).texture(u1, v1).next();
        bufferBuilder.vertex(matrices, (float)x1, (float)y0, (float)z).color(63, 118, 228, 255).texture(u1, v0).next();
        bufferBuilder.vertex(matrices, (float)x0, (float)y0, (float)z).color(63, 118, 228, 255).texture(u0, v0).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
    }
}
