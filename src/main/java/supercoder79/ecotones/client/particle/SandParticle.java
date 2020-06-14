package supercoder79.ecotones.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

import java.util.Random;

public class SandParticle extends SpriteBillboardParticle {
    protected SandParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
        this.collidesWithWorld = false;

        this.colorRed = 0.9F;
        this.colorGreen = 0.8F;
        this.colorBlue = 0.6F;

        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.scale *= this.random.nextFloat() * 0.4F + 0.7F;
        this.maxAge = 80;
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
            SandParticle particle = new SandParticle(clientWorld, x, y, z, random.nextDouble(), random.nextDouble() * -0.2, random.nextDouble());
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
