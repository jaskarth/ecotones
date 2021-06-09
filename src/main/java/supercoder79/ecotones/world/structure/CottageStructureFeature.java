package supercoder79.ecotones.world.structure;

import com.mojang.serialization.Codec;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import supercoder79.ecotones.world.structure.gen.CottageGenerator;

public class CottageStructureFeature extends StructureFeature<DefaultFeatureConfig> {
    public CottageStructureFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    public static class Start extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> feature, ChunkPos pos, int references, long seed) {
            super(feature, pos, references, seed);
        }

        @Override
        public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, ChunkPos pos, Biome biome, DefaultFeatureConfig config, HeightLimitView world) {
            int x = ChunkSectionPos.getBlockCoord(pos.x) + this.random.nextInt(16);
            int z = ChunkSectionPos.getBlockCoord(pos.z) + this.random.nextInt(16);

            CottageGenerator.generate(new BlockPos(x, chunkGenerator.getHeightOnGround(x, z, Heightmap.Type.WORLD_SURFACE_WG, world), z), this.children, this.random);
            this.setBoundingBoxFromChildren();
        }
    }
}
