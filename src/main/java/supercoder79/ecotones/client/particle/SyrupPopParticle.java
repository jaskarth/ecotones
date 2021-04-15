package supercoder79.ecotones.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SyrupPopParticle extends SpriteBillboardParticle {

    private SyrupPopParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.gravityStrength = 0.75F;
        this.field_28786 = 0.999F;
        this.velocityX *= 0.8;
        this.velocityY *= 0.8;
        this.velocityZ *= 0.8;

        this.colorRed = 0.9F;
        this.colorGreen = 0.5F;
        this.colorBlue = 0.2F;

        this.velocityY = this.random.nextFloat() * 0.1F + 0.05F;
        this.scale *= this.random.nextFloat() * 2.0F + 0.2F;
        this.maxAge = (int)(16.0D / (this.random.nextDouble() * 0.8D + 0.2D));
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public float getSize(float tickDelta) {
        float f = ((float)this.age + tickDelta) / (float)this.maxAge;
        return this.scale * (1.0F - f * f);
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            SyrupPopParticle particle = new SyrupPopParticle(clientWorld, d, e, f);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
