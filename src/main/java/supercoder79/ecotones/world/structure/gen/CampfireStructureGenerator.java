package supercoder79.ecotones.world.structure.gen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import supercoder79.ecotones.util.CampfireLogHelper;
import supercoder79.ecotones.world.features.FeatureHelper;
import supercoder79.ecotones.world.structure.EcotonesStructurePieces;

import java.util.List;
import java.util.Random;

public class CampfireStructureGenerator {
    public static void generate(BlockPos pos, List<StructurePiece> pieces, Random random, Block logSource) {
        pieces.add(new Piece(pos.add(random.nextInt(16), 0, random.nextInt(16)), logSource));
    }

    public static class Piece extends StructurePiece {
        private final BlockPos pos;
        private final Block logSource;

        protected Piece(BlockPos pos, Block logSource) {
            super(EcotonesStructurePieces.CAMPFIRE, 0);
            this.pos = pos;
            this.logSource = logSource;
            this.boundingBox = new BlockBox(pos.add(-3, -3, -3), pos.add(3, 3, 3));
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(EcotonesStructurePieces.CAMPFIRE, tag);
            this.pos = new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
            this.logSource = Registry.BLOCK.get(new Identifier(tag.getString("log_source")));
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            tag.putInt("x", this.pos.getX());
            tag.putInt("y", this.pos.getY());
            tag.putInt("z", this.pos.getZ());
            tag.putString("log_source", Registry.BLOCK.getId(this.logSource).toString());
        }

        @Override
        public boolean generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
            // Sample heightmap
            BlockPos scaledPos = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR_WG, this.pos);

            // TODO: don't set campfire if no seats were placed

            // Place if the block below is opaque
            if (world.getBlockState(scaledPos.down()).isOpaque() && world.getBlockState(scaledPos).isAir()) {
                world.setBlockState(scaledPos, Blocks.CAMPFIRE.getDefaultState().with(CampfireBlock.LIT, random.nextDouble() > 0.6), 3);

                // Each direction has 2/3 chance of attempting
                for (Direction direction : FeatureHelper.HORIZONTAL) {
                    if (random.nextInt(3) > 0) {
                        BlockPos local = scaledPos.offset(direction, 2);

                        // Place seat if we have 2 blocks of air and 1 solid block
                        if (world.getBlockState(local).isAir() && world.getBlockState(local.up()).isAir() && world.getBlockState(local.down()).isOpaque()) {
                            BlockState state = CampfireLogHelper.stateFor(this.logSource);
                            world.setBlockState(local, state, 3);
                        }
                    }
                }
            }

            return true;
        }
    }
}
