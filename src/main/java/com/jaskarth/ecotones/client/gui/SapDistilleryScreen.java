package com.jaskarth.ecotones.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import com.jaskarth.ecotones.Ecotones;
import com.jaskarth.ecotones.screen.SapDistilleryScreenHandler;
import com.jaskarth.ecotones.util.Vec2i;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SapDistilleryScreen extends HandledScreen<SapDistilleryScreenHandler> {
    private static final Identifier TEXTURE = Ecotones.id("textures/gui/sap_distillery.png");
    private final List<ParticleState> particles = new CopyOnWriteArrayList<>();

    public SapDistilleryScreen(SapDistilleryScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    protected void init() {
        super.init();
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
        this.titleY = 0;
        this.backgroundHeight = 166 + 15;
        this.playerInventoryTitleY = this.backgroundHeight - 94 - 8;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        // Fire tex
        int burnTime = this.handler.getBurnTime();
        int scaledBurnTime = (burnTime * 14) / 600;
        context.drawTexture(TEXTURE, x + 80, y + 62 + (13 - scaledBurnTime), 176, 13 - scaledBurnTime, 14, scaledBurnTime + 1);

        // Sap bar
        int sapAmt = this.handler.getSapAmount();
        int scaledSapAmt = (sapAmt * 36) / 40000;
        context.drawTexture(TEXTURE, x + 7, y + 21 + (35 - scaledSapAmt), 176, 123 - scaledSapAmt, 5, scaledSapAmt + 1);

        // Render overlay with alpha based on how much heat there is
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.handler.getHeat() / 3000.f);
        context.drawTexture(TEXTURE, x + 50, y + 23, 176, 14, 76, 38);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // Syrup cauldron
        // Maple: 0xE58632
        RenderSystem.setShaderColor(229.f / 255.f, 134.f / 255.f, 50 / 255.f, 1.0F);
        int syrupAmt = this.handler.getSyrupAmount();
        int scaledSyrupAmt = (syrupAmt * 34) / 5000;
        context.drawTexture(TEXTURE, x + 52, y + 26 + (33 - scaledSyrupAmt), 176, 88 - scaledSyrupAmt, 72, scaledSyrupAmt);


        // Maple: 0xD96927
        RenderSystem.setShaderColor(217 / 255.f, 105 / 255.f, 39 / 255.f, 1.0F);

        for (ParticleState particle : this.particles) {
            context.drawTexture(TEXTURE, x + particle.pos.x(), y + particle.pos.y(), particle.getU(), 0, 3, 3);
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // Reset state
        RenderSystem.disableBlend();
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();

        if (this.client != null && this.client.world != null) {
            if (this.client.world.getTime() % 10 == 0) {
                Random random = this.client.world.getRandom();
                int syrupAmt = this.handler.getSyrupAmount();
                int scaledSyrupAmt = (syrupAmt * 34) / 5000;
                if (random.nextInt(2) == 0 && this.particles.size() < 8 && scaledSyrupAmt > 3) {
                    Vec2i pos = new Vec2i(52 + random.nextInt(72), 26 + (30 - scaledSyrupAmt) + random.nextInt(scaledSyrupAmt));

                    // Make sure we're not at the corner bits
                    if (!((pos.x() >= 52 && pos.x() <= 65 && pos.y() >= 51 && pos.y() <= 61) || (pos.x() >= 110 && pos.x() <= 125 && pos.y() >= 51 && pos.y() <= 61))) {
                        this.particles.add(new ParticleState(pos));
                    }
                }
            }
        }

        for (ParticleState particle : this.particles) {
            particle.tick();

            if (particle.isTooOld()) {
                // FIXME: remove copy on write
                this.particles.remove(particle);
            }
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);

        int x = mouseX - (this.width - this.backgroundWidth) / 2;
        int y = mouseY - (this.height - this.backgroundHeight) / 2;

        // Syrup tooltip
        if (x >= 50 && x <= 122 && y >= 23 && y <= 61) {
            context.drawTooltip(this.textRenderer, Text.literal(this.handler.getSyrupAmount() + " / 5000"), x, y);
        }

        // Sap tooltip
        if (x >= 7 && x <= 12 && y >= 21 && y <= 57) {
            context.drawTooltip(this.textRenderer, Text.literal(this.handler.getSapAmount() + " / 40000"), x, y);
        }
    }

    private static final class ParticleState {
        private final Vec2i pos;
        // 0 .. 3
        private int phase = 0;
        private int age = 0;

        private ParticleState(Vec2i pos) {
            this.pos = pos;
        }

        private void tick() {
            this.age++;
            this.phase = this.age / 15;
        }

        private boolean isTooOld() {
            return this.age >= 60;
        }

        private int getU() {
            return 190 + this.phase * 3;
        }
    }
}
