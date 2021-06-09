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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import supercoder79.ecotones.world.structure.gen.CampfireStructureGenerator;

public class CampfireStructureFeature extends StructureFeature<SingleStateFeatureConfig> {
    public CampfireStructureFeature(Codec<SingleStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<SingleStateFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    public static final class Start extends StructureStart<SingleStateFeatureConfig> {
        public Start(StructureFeature<SingleStateFeatureConfig> feature, ChunkPos pos, int references, long seed) {
            super(feature, pos, references, seed);
        }

        @Override
        public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, ChunkPos pos, Biome biome, SingleStateFeatureConfig config, HeightLimitView world) {
            CampfireStructureGenerator.generate(new BlockPos(ChunkSectionPos.getBlockCoord(pos.x), 64, ChunkSectionPos.getBlockCoord(pos.z)), this.children, this.random, config.state.getBlock());
            this.setBoundingBoxFromChildren();
        }
    }
}
