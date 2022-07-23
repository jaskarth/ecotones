package supercoder79.ecotones.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supercoder79.ecotones.api.BiomeRegistries;

@Mixin(SlimeEntity.class)
public class MixinSlimeEntity {
    // Code adapted from https://github.com/TerraformersMC/Terraform/blob/1.17/terraform-biome-builder-api-v1/src/main/java/com/terraformersmc/terraform/biomebuilder/mixin/MixinSlimeEntity.java

    @Inject(method = "canSpawn(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)Z", at = @At(value = "HEAD"), cancellable = true)
    private static void canSpawnInjection(EntityType<SlimeEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        // Add slime spawns

        if (world.getDifficulty() != Difficulty.PEACEFUL) {
            RegistryKey<Biome> key = world.getBiome(pos).getKey().get();

            if (BiomeRegistries.SLIME_SPAWN_BIOMES.contains(key) && pos.getY() > 50 && pos.getY() < 70 && random.nextFloat() < 0.5F && random.nextFloat() < world.getMoonSize() && world.getLightLevel(pos) <= random.nextInt(8)) {
                BlockPos down = pos.down();
                cir.setReturnValue(world.getBlockState(down).allowsSpawning(world, down, type));
            }
        }
    }
}
