package com.jaskarth.ecotones.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;

public class MapleLeafParticle extends SpriteBillboardParticle {
    protected MapleLeafParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
        this.collidesWithWorld = true;

        double noise = (Biome.FOLIAGE_NOISE.sample(x / 80.0, z / 80.0, false) + 1) / 2.0;

        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;

        this.red = (float) MathHelper.lerp(noise, 0.75, 0.85);
        this.green = (float) MathHelper.lerp(noise, 0.2, 0.4);
        this.blue = (float) MathHelper.lerp(noise, 0.18, 0.09);

        this.setBoundingBoxSpacing(0.05F, 0.05F);
        this.scale = 0.25f;
        this.maxAge = 160;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.prevAngle = this.angle;
            if (this.onGround) {
                this.prevAngle = this.angle = 0.0F;
            }

            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityY -= 0.003;
            this.velocityY = Math.max(this.velocityY, -0.14);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            Random random = clientWorld.random;
            MapleLeafParticle particle = new MapleLeafParticle(clientWorld, x, y, z, (random.nextDouble() - 0.5) * 0.01, -0.02, (random.nextDouble() - 0.5) * 0.01);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
