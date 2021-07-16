package supercoder79.ecotones.world.structure.gen;

import net.minecraft.block.*;
import net.minecraft.block.enums.BedPart;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import supercoder79.ecotones.util.BoxHelper;
import supercoder79.ecotones.util.deco.BlockAttachment;
import supercoder79.ecotones.util.deco.BlockDecorations;
import supercoder79.ecotones.util.deco.DecorationCategory;
import supercoder79.ecotones.world.features.FeatureHelper;
import supercoder79.ecotones.world.structure.EcotonesStructurePieces;

import java.util.List;
import java.util.Random;

public class CottageGenerator {
    public static void generate(BlockPos pos, List<StructurePiece> pieces, Random random) {
        // I can't wait until 3 months from now where I completely regret implementing it this way
        pieces.add(new CenterRoom(pos.down()));
        pieces.add(new Porch(pos.down().add(-2, 0, 0)));
    }

    private static abstract class CottagePiece extends StructurePiece {
        protected final BlockPos pos;

        protected CottagePiece(StructurePieceType type, BlockPos pos, BlockBox box) {
            super(type, 0, box);

            this.pos = pos;
        }

        public CottagePiece(StructurePieceType type, NbtCompound nbt) {
            super(type, nbt);
            this.pos = new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
        }

        @Override
        protected void writeNbt(ServerWorld world, NbtCompound nbt) {
            nbt.putInt("x", this.pos.getX());
            nbt.putInt("y", this.pos.getY());
            nbt.putInt("z", this.pos.getZ());
        }

        protected void setPostProcess(StructureWorldAccess world, BlockState state, BlockPos pos) {
            world.setBlockState(pos, state, 3);
            world.getChunk(pos).markBlockForPostProcessing(pos);
        }
    }

    public static class CenterRoom extends CottagePiece {
        public CenterRoom(BlockPos pos) {
            super(EcotonesStructurePieces.COTTAGE_CENTER, pos, BoxHelper.box(pos.add(-5, 0, -5), pos.add(6, 8, 10)));
        }

        public CenterRoom(ServerWorld world, NbtCompound nbt) {
            super(EcotonesStructurePieces.COTTAGE_CENTER, nbt);
        }

        @Override
        public boolean generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
            // Place floor
            for (int x = 0; x < 4; x++) {
                for (int z = 0; z < 6; z++) {
                    world.setBlockState(this.pos.add(x, 0, z), Blocks.OAK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 3);
                }
            }

            // Place short wall
            for (int x = 0; x < 4; x++) {
                // Add stripped logs
                world.setBlockState(this.pos.add(x, 0, -1), Blocks.STRIPPED_SPRUCE_LOG.getDefaultState().with(PillarBlock.AXIS, Direction.Axis.X), 3);
                world.setBlockState(this.pos.add(x, 0, 6), Blocks.STRIPPED_SPRUCE_LOG.getDefaultState().with(PillarBlock.AXIS, Direction.Axis.X), 3);

                for (int y = 1; y < 5; y++) {
                    // Create a shape that's like -^^- for the roof
                    if ((x == 0 || x == 3) && y == 4) {
                        continue;
                    }

                    // Place wall
                    world.setBlockState(this.pos.add(x, y, -1), Blocks.SPRUCE_PLANKS.getDefaultState(), 3);
                    world.setBlockState(this.pos.add(x, y, 6), Blocks.SPRUCE_PLANKS.getDefaultState(), 3);
                }
            }

            // Place long wall
            for (int z = -1; z < 7; z++) {
                // Make edges logs
                BlockState state = Blocks.SPRUCE_PLANKS.getDefaultState();
                BlockState stripped = Blocks.STRIPPED_SPRUCE_LOG.getDefaultState().with(PillarBlock.AXIS, Direction.Axis.Z);
                if (z == -1 || z == 6) {
                    state = Blocks.SPRUCE_LOG.getDefaultState();
                    stripped = Blocks.SPRUCE_LOG.getDefaultState();
                }

                // Add stripped logs
                world.setBlockState(this.pos.add(-1, 0, z), stripped, 3);
                world.setBlockState(this.pos.add(4, 0, z), stripped, 3);

                // Place wall
                for (int y = 1; y < 4; y++) {
                    world.setBlockState(this.pos.add(-1, y, z), state, 3);
                    world.setBlockState(this.pos.add(4, y, z), state, 3);
                }
            }

            // Door
            world.setBlockState(this.pos.add(-1, 1, 4), Blocks.SPRUCE_DOOR.getDefaultState(), 3);
            world.setBlockState(this.pos.add(-1, 2, 4), Blocks.SPRUCE_DOOR.getDefaultState().with(DoorBlock.HALF, DoubleBlockHalf.UPPER), 3);

            // Roof
            for (int z = -2; z < 8; z++) {
                // Place special end bits
                if (z == -2 || z == 7) {
                    world.setBlockState(this.pos.add(1, 4, z), Blocks.BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 3);
                    world.setBlockState(this.pos.add(2, 4, z), Blocks.BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 3);

                    world.setBlockState(this.pos.add(-1, 3, z), Blocks.BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 3);
                    world.setBlockState(this.pos.add(4, 3, z), Blocks.BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 3);
                }

                // 1 offset roof
                world.setBlockState(this.pos.add(0, 4, z), Blocks.BRICKS.getDefaultState(), 3);
                world.setBlockState(this.pos.add(3, 4, z), Blocks.BRICKS.getDefaultState(), 3);

                // Top roof
                world.setBlockState(this.pos.add(1, 5, z), Blocks.BRICK_SLAB.getDefaultState(), 3);
                world.setBlockState(this.pos.add(2, 5, z), Blocks.BRICK_SLAB.getDefaultState(), 3);

                if (!((z == -1 || z == 6))) {
                    // Top roof interior
                    world.setBlockState(this.pos.add(1, 4, z), Blocks.BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 3);
                    world.setBlockState(this.pos.add(2, 4, z), Blocks.BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 3);
                }

                // 2 offset roof
                world.setBlockState(this.pos.add(-1, 4, z), Blocks.BRICK_SLAB.getDefaultState(), 3);
                world.setBlockState(this.pos.add(4, 4, z), Blocks.BRICK_SLAB.getDefaultState(), 3);

                // Dangly bit on the far end
                world.setBlockState(this.pos.add(5, 3, z), Blocks.BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 3);
            }

            // Windows
            world.setBlockState(this.pos.add(-1, 2, 2), Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, true).with(PaneBlock.SOUTH, true), 3);
            world.setBlockState(this.pos.add(4, 2, 4), Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, true).with(PaneBlock.SOUTH, true), 3);
            world.setBlockState(this.pos.add(2, 2, 6), Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, true).with(PaneBlock.WEST, true), 3);

            // Decorations
            BlockDecorations.get(random, BlockAttachment.FLOOR, DecorationCategory.TABLES).generate(world, this.pos.add(3, 1, 5), Direction.UP);
            BlockDecorations.get(random, BlockAttachment.FLOOR, DecorationCategory.INDUSTRY).generate(world, this.pos.add(3, 1, 3), Direction.UP);
            BlockDecorations.get(random, BlockAttachment.CEILING, DecorationCategory.LIGHTS).generate(world, this.pos.add(3, 3, 5), Direction.UP);
            BlockDecorations.get(random, BlockAttachment.CEILING, DecorationCategory.LIGHTS).generate(world, this.pos.add(0, 3, 0), Direction.UP);

            // Bed
            world.setBlockState(this.pos.add(0, 1, 0), Blocks.RED_BED.getDefaultState().with(BedBlock.FACING, Direction.NORTH).with(BedBlock.PART, BedPart.HEAD), 3);
            world.setBlockState(this.pos.add(0, 1, 1), Blocks.RED_BED.getDefaultState().with(BedBlock.FACING, Direction.NORTH).with(BedBlock.PART, BedPart.FOOT), 3);

            // Fireplace
            for (int x = 2; x <= 3; x++) {
                for (int z = 0; z <= 2; z++) {
                    world.setBlockState(this.pos.add(x, 0, z), Blocks.COBBLESTONE.getDefaultState(), 3);
                }
            }

            for (int z = 0; z <= 2; z+=2) {
                world.setBlockState(this.pos.add(3, 1, z), Blocks.COBBLESTONE.getDefaultState(), 3);
                world.setBlockState(this.pos.add(3, 2, z), Blocks.COBBLESTONE.getDefaultState(), 3);
            }

            world.setBlockState(this.pos.add(3, 2, 1), Blocks.COBBLESTONE.getDefaultState(), 3);
            world.setBlockState(this.pos.add(3, 3, 1), Blocks.COBBLESTONE.getDefaultState(), 3);

            world.setBlockState(this.pos.add(3, 1, 1), Blocks.CAMPFIRE.getDefaultState(), 3);

            // Chimney
            world.setBlockState(this.pos.add(3, 4, 1), Blocks.COBBLESTONE.getDefaultState(), 3);
            world.setBlockState(this.pos.add(4, 4, 1), Blocks.COBBLESTONE.getDefaultState(), 3);

            for (int y = 5; y <= 7; y++) {
                world.setBlockState(this.pos.add(3, y, 1), Blocks.COBBLESTONE.getDefaultState(), 3);

                for (Direction direction : FeatureHelper.HORIZONTAL) {
                    world.setBlockState(this.pos.add(3, y, 1).offset(direction), Blocks.COBBLESTONE.getDefaultState(), 3);
                }
            }

            world.setBlockState(this.pos.add(3, 7, 1), Blocks.CAMPFIRE.getDefaultState().with(CampfireBlock.SIGNAL_FIRE, true), 3);

            return true;
        }
    }

    public static class Porch extends CottagePiece {
        public Porch(BlockPos pos) {
            super(EcotonesStructurePieces.COTTAGE_PORCH, pos, BoxHelper.box(pos.add(-5, 0, -5), pos.add(6, 6, 6)));
        }

        public Porch(ServerWorld world, NbtCompound nbt) {
            super(EcotonesStructurePieces.COTTAGE_PORCH, nbt);
        }

        @Override
        public boolean generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
            // Generate porch
            for (int z = -1; z < 7; z++) {
                BlockState state = Blocks.OAK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP);
                if (z == -1 || z == 6) {
                    state = Blocks.SPRUCE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP);
                }

                // Set inner porch
                world.setBlockState(this.pos.add(0, 0, z), state, 3);
                world.setBlockState(this.pos.add(-1, 0, z), state, 3);

                // Set porch edge
                BlockState edgeState = Blocks.SPRUCE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP);
                if (z == -1 || z == 3 || z == 6) {
                    edgeState = Blocks.SPRUCE_LOG.getDefaultState();
                } else if (z == 4 || z == 5) {
                    edgeState = Blocks.OAK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM);
                }

                world.setBlockState(this.pos.add(-2, 0, z), edgeState, 3);
            }

            // Fences
            for (int z = -1; z < 7; z++) {
                if (z == 4 || z == 5) {
                    continue;
                }

                setPostProcess(world, Blocks.OAK_FENCE.getDefaultState(),  this.pos.add(-2, 1, z));

                // Make fences go up and towards the house on edges
                if (z == -1 || z == 6) {
                    setPostProcess(world, Blocks.OAK_FENCE.getDefaultState(),  this.pos.add(-2, 2, z));
                    setPostProcess(world, Blocks.OAK_FENCE.getDefaultState(),  this.pos.add(-1, 1, z));
                    setPostProcess(world, Blocks.OAK_FENCE.getDefaultState(),  this.pos.add(0, 1, z));
                }
            }

            // Roof
            for (int z = -2; z < 8; z++) {
                world.setBlockState(this.pos.add(0, 3, z), Blocks.SPRUCE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 3);
                world.setBlockState(this.pos.add(-1, 3, z), Blocks.SPRUCE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 3);

                SlabType edgeType = SlabType.BOTTOM;
                if (z == 4 || z == 5) {
                    edgeType = SlabType.TOP;
                }

                // Set edge slab
                world.setBlockState(this.pos.add(-2, 3, z), Blocks.SPRUCE_SLAB.getDefaultState().with(SlabBlock.TYPE, edgeType), 3);
            }

            return true;
        }
    }
}
