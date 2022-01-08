package supercoder79.ecotones.world.structure;

import com.mojang.serialization.Codec;
import net.minecraft.structure.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.random.ChunkRandom;
import supercoder79.ecotones.world.structure.gen.CampfireGenerator;

public class CampfireStructureFeature extends StructureFeature<SingleStateFeatureConfig> {
    public CampfireStructureFeature(Codec<SingleStateFeatureConfig> codec) {
        super(codec, StructureGeneratorFactory.simple(CampfireStructureFeature::canGenerate, CampfireStructureFeature::addPieces));
    }

    private static <C extends FeatureConfig> boolean canGenerate(StructureGeneratorFactory.Context<C> context) {
        if (!context.isBiomeValid(Heightmap.Type.WORLD_SURFACE_WG)) {
            return false;
        }

        return true;
    }

    private static void addPieces(StructurePiecesCollector collector, StructurePiecesGenerator.Context<SingleStateFeatureConfig> context) {
        ChunkPos pos = context.chunkPos();
        ChunkRandom random = context.random();
        SingleStateFeatureConfig config = context.config();

        CampfireGenerator.generate(new BlockPos(ChunkSectionPos.getBlockCoord(pos.x), 64, ChunkSectionPos.getBlockCoord(pos.z)), collector, random, config.state.getBlock());
    }
}
