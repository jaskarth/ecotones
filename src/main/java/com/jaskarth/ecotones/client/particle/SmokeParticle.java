package com.jaskarth.ecotones.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class SmokeParticle extends SpriteBillboardParticle {
    private boolean ephemeralForASecond = false;
    private boolean directed = false;

    public SmokeParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z);
        this.scale(3.0F);
        this.setBoundingBoxSpacing(0.25F, 0.25F);
        this.maxAge = this.random.nextInt(50) + 160;


        this.gravityStrength = 0.000003f;
        this.velocityX = velocityX;
        this.velocityY = velocityY + (double)(this.random.nextFloat() / 500.0F);
        this.velocityZ = velocityZ;
    }

    @Override
    public void tick() {
        if (ephemeralForASecond) {
            this.collidesWithWorld = age >= 8;
        }

        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ < this.maxAge && !(this.alpha <= 0.0F)) {
            if (directed) {
                this.velocityX *= 0.9;
                this.velocityZ *= 0.9;
            }

            this.velocityX += (double)(this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1));
            this.velocityZ += (double)(this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1));
            this.velocityY -= (double)this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
//            if (this.age >= this.maxAge - 80 && this.alpha > 0.01F) {
//                this.alpha -= 0.015F;
//            }

            this.scale *= 1.003;
            alpha *= 0.99;
        } else {
            this.markDead();
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class ChimneySmokeFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public ChimneySmokeFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            SmokeParticle p = new SmokeParticle(clientWorld, d, e, f, g, h, i);
            p.setSprite(this.spriteProvider);
            return p;
        }
    }

    public static class DirectedSmokeFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public DirectedSmokeFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            SmokeParticle p = new SmokeParticle(clientWorld, d, e, f, g, h, i);
            p.ephemeralForASecond = true;
            p.directed = true;
            p.setSprite(this.spriteProvider);
            return p;
        }
    }
}
