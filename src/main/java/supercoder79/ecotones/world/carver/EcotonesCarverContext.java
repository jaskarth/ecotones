package supercoder79.ecotones.world.carver;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.chunk.ChunkNoiseSampler;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.Optional;
import java.util.function.Function;

public class EcotonesCarverContext extends CarverContext {
    private final EcotonesChunkGenerator generator;
    private final ChunkRegion region;

    public EcotonesCarverContext(EcotonesChunkGenerator generator, NoiseChunkGenerator shim, DynamicRegistryManager registryManager, HeightLimitView heightLimitView) {
        super(shim, registryManager, heightLimitView, null, null, null);
        this.generator = generator;
        this.region = (ChunkRegion)heightLimitView;
    }

    @Override
    public Optional<BlockState> applyMaterialRule(Function<BlockPos, RegistryEntry<Biome>> posToBiome, Chunk chunk, BlockPos pos, boolean hasFluid) {
        return Optional.of(this.region.getBlockState(pos));
    }
}
