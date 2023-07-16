package supercoder79.ecotones.mixin;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.api.BiomeRegistries;
import supercoder79.ecotones.api.FeatureList;
import supercoder79.ecotones.util.ImprovedChunkRandom;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;
import supercoder79.ecotones.world.gen.NetherGen;

import java.util.List;

@Mixin(ChunkStatus.class)
public class MixinChunkStatus {
    @Inject(method = "method_51375", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void genFeaturesButEcotonesStyle(ChunkStatus targetStatus, ServerWorld world, ChunkGenerator generator, List chunks, Chunk chunk, CallbackInfo ci, ChunkRegion region) {
        // Generate ecotones if we're in an ecotones world
        StructureAccessor accessor = world.getStructureAccessor().forRegion(region);
        if (Ecotones.isServerEcotones && world.toServerWorld().getRegistryKey() == World.NETHER) {
            ChunkPos chunkPos = region.getCenterPos();
            int x = chunkPos.getStartX();
            int z = chunkPos.getStartZ();
            BlockPos blockPos = new BlockPos(x, world.getBottomY(), z);
            NetherGen.generate(region, accessor, generator, blockPos);
        }

        // Or, alternatively, if this isn't an ecotones chunk generator and we see an ecotones biome, let's generate it.
        if (!(generator instanceof EcotonesChunkGenerator)) {
            ChunkPos chunkPos = region.getCenterPos();
            int startX = chunkPos.getStartX();
            int startZ = chunkPos.getStartZ();
            BlockPos pos = new BlockPos(startX, 0, startZ);
            int topY = region.getTopY(Heightmap.Type.WORLD_SURFACE_WG, startX, startZ);
            Biome biome = generator.getBiomeSource().getBiome((chunkPos.x << 2) + 2, topY >> 2, (chunkPos.z << 2) + 2, MultiNoiseUtil.createEmptyMultiNoiseSampler()).value();
            RegistryKey<Biome> key = world.getRegistryManager().get(RegistryKeys.BIOME).getKey(biome).orElseThrow();
            FeatureList featureList = BiomeRegistries.FEATURE_LISTS.get(key);

            if (featureList != null) {
                ImprovedChunkRandom random = new ImprovedChunkRandom(0);
                long populationSeed = random.setPopulationSeed(world.getSeed(), startX, startZ, 0);

                EcotonesChunkGenerator.generateEcotonesFeatures(generator, featureList, biome, accessor, region, populationSeed, new ChunkRandom(new CheckedRandom(random.nextLong())), pos, chunk);
            }
        }
    }
}
