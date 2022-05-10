package supercoder79.ecotones.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

import java.util.Random;

public class AsbestosParticle extends SpriteBillboardParticle {
    private final double xStart;
    private final double zStart;
    private final SpriteProvider spriteProvider;
    private double theta = 0;

    protected AsbestosParticle(ClientWorld clientWorld, double xStart, double y, double zStart, SpriteProvider spriteProvider) {
        super(clientWorld, xStart, y, zStart);
        this.xStart = xStart;
        this.zStart = zStart;
        this.spriteProvider = spriteProvider;

        float f = this.random.nextFloat() * 0.3F + 0.6F;
        this.red = f;
        this.green = f;
        this.blue = f;
        this.scale = 0.12F * (this.random.nextFloat() * this.random.nextFloat() * 1.5F + 1.0F);
        // 7-10 seconds
        this.maxAge = 200 - this.random.nextInt(60);
        this.setSpriteForAge(spriteProvider);

    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.y += 0.025;
            this.x = this.xStart + Math.cos(this.theta) * 1.2;
            this.z = this.zStart + Math.sin(this.theta) * 1.2;

            theta += 0.125;
        }

        this.setSpriteForAge(this.spriteProvider);
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

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new AsbestosParticle(clientWorld, d, e, f, this.spriteProvider);
        }
    }
}
