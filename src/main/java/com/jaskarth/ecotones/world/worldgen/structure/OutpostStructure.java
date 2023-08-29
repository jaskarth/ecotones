package com.jaskarth.ecotones.world.worldgen.structure;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.*;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import com.jaskarth.ecotones.util.BoxHelper;
import com.jaskarth.ecotones.world.worldgen.gen.EcotonesChunkGenerator;
import com.jaskarth.ecotones.world.worldgen.structure.gen.layout.OutpostLayout;

import java.util.Optional;
import java.util.Random;

public class OutpostStructure extends Structure {
    public static final Codec<OutpostStructure> CODEC = createCodec(OutpostStructure::new);

    protected OutpostStructure(Config config) {
        super(config);
    }

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
        int x = ChunkSectionPos.getBlockCoord(pos.x) + 8;
        int z = ChunkSectionPos.getBlockCoord(pos.z) + 8;

        // Not sure what impl the height limit is, so use fallback for now
        long seed = 0;
        if (chunkGenerator instanceof EcotonesChunkGenerator ecg) {
            seed = ecg.getSeed();
        }

        collector.addPiece(new OutpostPiece(seed, new BlockPos(x, chunkGenerator.getHeightOnGround(x, z, Heightmap.Type.WORLD_SURFACE_WG, world, null), z), chunkGenerator, world));
    }

    @Override
    public Optional<StructurePosition> getStructurePosition(Context context) {
        return Optional.of(new Structure.StructurePosition(context.chunkPos().getStartPos(), collector -> addPieces(collector, context)));
    }

    @Override
    public StructureType<?> getType() {
        return EcotonesStructureTypes.OUTPOST;
    }

    public static class OutpostPiece extends StructurePiece {
        private final OutpostLayout layout;
        protected final long seed;
        protected final BlockPos pos;

        protected OutpostPiece(long seed, BlockPos pos, ChunkGenerator generator, HeightLimitView height) {
            super(EcotonesStructurePieces.OUTPOST, 0, BoxHelper.box(pos.add(-40, -20, -40), pos.add(40, 20, 40)));

            this.pos = pos;
            this.seed = seed;
            this.layout = new OutpostLayout(seed, pos.getX(), pos.getZ());
            this.layout.generate(generator, height);
        }

        public OutpostPiece(StructureTemplateManager manager, NbtCompound nbt) {
            super(EcotonesStructurePieces.OUTPOST, nbt);
            this.pos = new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
            this.seed = nbt.getLong("seed");
            this.layout = new OutpostLayout(this.seed, this.pos.getX(), this.pos.getZ());
            // FIXME: needs server!
//            this.layout.generate(generator, );
        }

        @Override
        protected void writeNbt(StructureContext context, NbtCompound nbt) {
            nbt.putInt("x", this.pos.getX());
            nbt.putInt("y", this.pos.getY());
            nbt.putInt("z", this.pos.getZ());
            nbt.putLong("seed", this.seed);
        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, net.minecraft.util.math.random.Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
            this.layout.generateCells(world, structureAccessor, chunkGenerator, new Random(random.nextLong()), pos);
        }
    }
}
