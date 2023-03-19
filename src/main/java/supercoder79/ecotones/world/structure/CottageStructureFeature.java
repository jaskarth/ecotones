package supercoder79.ecotones.world.structure;

import com.mojang.serialization.Codec;
import net.minecraft.structure.*;
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
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import supercoder79.ecotones.world.structure.gen.CottageGenerator;

import java.util.Optional;
import java.util.Random;

public class CottageStructureFeature extends Structure {
    public static final Codec<CottageStructureFeature> CODEC = createCodec(CottageStructureFeature::new);

    protected CottageStructureFeature(Config config) {
        super(config);
    }
//    public CottageStructureFeature(Codec<DefaultFeatureConfig> codec) {
//        super(codec, StructureGeneratorFactory.simple(CottageStructureFeature::canGenerate, CottageStructureFeature::addPieces));
//    }

    private static <C extends FeatureConfig> boolean canGenerate(StructureGeneratorFactory.Context<C> context) {
        if (!context.isBiomeValid(Heightmap.Type.WORLD_SURFACE_WG)) {
            return false;
        }

        return true;
    }

    private static void addPieces(StructurePiecesCollector collector, Context context) {
        HeightLimitView world = context.world();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        ChunkPos pos = context.chunkPos();
        ChunkRandom random = context.random();
        int x = ChunkSectionPos.getBlockCoord(pos.x) + random.nextInt(16);
        int z = ChunkSectionPos.getBlockCoord(pos.z) + random.nextInt(16);

        CottageGenerator.generate(chunkGenerator, world,
                new BlockPos(x, chunkGenerator.getHeightOnGround(x, z, Heightmap.Type.WORLD_SURFACE_WG, world, null), z),
                collector, new Random(random.nextLong()));
//        this.setBoundingBoxFromChildren();
    }

    @Override
    public Optional<StructurePosition> getStructurePosition(Context context) {
        return getStructurePosition(context, Heightmap.Type.OCEAN_FLOOR_WG, collector -> addPieces(collector, context));
    }

    @Override
    public StructureType<?> getType() {
        return EcotonesStructureTypes.COTTAGE;
    }
}
