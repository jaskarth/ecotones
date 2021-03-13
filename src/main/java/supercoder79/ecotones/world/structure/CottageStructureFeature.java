package supercoder79.ecotones.world.structure;

import com.mojang.serialization.Codec;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import supercoder79.ecotones.world.structure.gen.CampfireStructureGenerator;
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
        public Start(StructureFeature<DefaultFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
            super(feature, chunkX, chunkZ, box, references, seed);
        }

        @Override
        public void init(DynamicRegistryManager registryManager, ChunkGenerator generator, StructureManager manager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig config, HeightLimitView height) {
            int x = ChunkSectionPos.getBlockCoord(chunkX) + this.random.nextInt(16);
            int z = ChunkSectionPos.getBlockCoord(chunkZ) + this.random.nextInt(16);

            CottageGenerator.generate(new BlockPos(x, generator.getHeightOnGround(x, z, Heightmap.Type.WORLD_SURFACE_WG, height), z), this.children, this.random);
            this.setBoundingBoxFromChildren();
        }
    }
}
