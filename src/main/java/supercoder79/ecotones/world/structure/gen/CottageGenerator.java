package supercoder79.ecotones.world.structure.gen;

import net.minecraft.block.*;
import net.minecraft.block.enums.BedPart;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.structure.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import supercoder79.ecotones.util.BoxHelper;
import supercoder79.ecotones.util.deco.BlockAttachment;
import supercoder79.ecotones.util.deco.BlockDecorations;
import supercoder79.ecotones.util.deco.DecorationCategory;
import supercoder79.ecotones.world.features.FeatureHelper;
import supercoder79.ecotones.world.structure.EcotonesStructurePieces;
import supercoder79.ecotones.world.structure.StructureTerrainControl;

import java.util.List;
import java.util.Random;

public class CottageGenerator {
    public static void generate(ChunkGenerator chunkGenerator, HeightLimitView world, BlockPos pos, StructurePiecesCollector collector, Random random) {
        // I can't wait until 3 months from now where I completely regret implementing it this way
        collector.addPiece(new CenterRoom(pos.down()));
        collector.addPiece(new Porch(pos.down().add(-2, 0, 0)));

        // Generate 1-4 patches of farmland next to the house
        int farmland = 1 + random.nextInt(4);

        for (int i = 0; i < farmland; i++) {
            int dx = random.nextInt(24) - random.nextInt(24);
            int dz = random.nextInt(24) - random.nextInt(24);

            collector.addPiece(new Farm(new BlockPos(pos.getX() + dx, chunkGenerator.getHeightOnGround(pos.getX() + dx, pos.getZ() + dz, Heightmap.Type.WORLD_SURFACE_WG, world, null), pos.getZ() + dz)));
        }
    }

    public static abstract class CottagePiece extends StructurePiece {
        protected final BlockPos pos;

        protected CottagePiece(StructurePieceType type, BlockPos pos, BlockBox box) {
            super(type, 0, box);

            this.pos = pos;
        }

        public CottagePiece(StructurePieceType type, NbtCompound nbt) {
            super(type, nbt);
            this.pos = new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
        }

        protected void writeNbt(StructureContext world, NbtCompound nbt) {
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
            super(EcotonesStructurePieces.COTTAGE_CENTER, pos, BoxHelper.box(pos.add(-3, 0, -3), pos.add(4, 8, 8)));
        }

        public CenterRoom(NbtCompound nbt) {
            super(EcotonesStructurePieces.COTTAGE_CENTER, nbt);
        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, net.minecraft.util.math.random.Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
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

            Random juRandom = new Random(random.nextLong());
            // Decorations
            BlockDecorations.get(juRandom, BlockAttachment.FLOOR, DecorationCategory.TABLES).generate(world, this.pos.add(3, 1, 5), Direction.UP);
            BlockDecorations.get(juRandom, BlockAttachment.FLOOR, DecorationCategory.INDUSTRY).generate(world, this.pos.add(3, 1, 3), Direction.UP);
            BlockDecorations.get(juRandom, BlockAttachment.CEILING, DecorationCategory.LIGHTS).generate(world, this.pos.add(3, 3, 5), Direction.UP);
            BlockDecorations.get(juRandom, BlockAttachment.CEILING, DecorationCategory.LIGHTS).generate(world, this.pos.add(0, 3, 0), Direction.UP);

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
        }
    }

    public static class Porch extends CottagePiece {
        public Porch(BlockPos pos) {
            super(EcotonesStructurePieces.COTTAGE_PORCH, pos, BoxHelper.box(pos.add(-3, 0, -3), pos.add(4, 6, 4)));
        }

        public Porch(NbtCompound nbt) {
            super(EcotonesStructurePieces.COTTAGE_PORCH, nbt);
        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, net.minecraft.util.math.random.Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
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
        }
    }

    public static class Farm extends CottagePiece implements StructureTerrainControl {

        public Farm(BlockPos pos) {
            // TODO: variable sizes
            super(EcotonesStructurePieces.COTTAGE_FARM, pos, BoxHelper.box(pos.add(-2, -1, -2), pos.add(2, 3, 2)));
        }

        public Farm(NbtCompound nbt) {
            super(EcotonesStructurePieces.COTTAGE_FARM, nbt);
        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, net.minecraft.util.math.random.Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
            BlockState crop = Blocks.WHEAT.getDefaultState();

            Property<Integer> prop = Properties.AGE_7;
            int max = 7;

            // Randomly select crops
            if (random.nextInt(4) == 0) {
                int selection = random.nextInt(3);

                if (selection == 0) {
                    crop = Blocks.CARROTS.getDefaultState();
                } else if (selection == 1) {
                    crop = Blocks.POTATOES.getDefaultState();
                } else {
                    crop = Blocks.BEETROOTS.getDefaultState();
                    prop = Properties.AGE_3;
                    max = 3;
                }
            }

            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    // Skip edges
                    if (Math.abs(x) == 2 && Math.abs(z) == 2) {
                        continue;
                    }

                    BlockPos topPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, this.pos.add(x, 0, z)).down();
                    if (!world.getBlockState(topPos).isIn(BlockTags.DIRT)) {
                        continue;
                    }

                    boolean placeWater = true;
                    // Check edges for ability to place water
                    for (Direction direction : Direction.Type.HORIZONTAL) {
                        BlockState edge = world.getBlockState(topPos.offset(direction));

                        if (!(edge.isOpaque() || edge.getFluidState().isIn(FluidTags.WATER))) {
                            placeWater = false;
                            break;
                        }
                    }

                    if (placeWater && random.nextInt(4) == 0) {
                        world.setBlockState(topPos, Blocks.WATER.getDefaultState(), 3);
                        // TODO: rare chance of fertilizer spreader
                    } else {
                        // Skip random amount of farms
                        if (random.nextInt(5) == 0) {
                            continue;
                        }

                        world.setBlockState(topPos, Blocks.FARMLAND.getDefaultState(), 3);

                        // Skip random amount of crops
                        if (random.nextInt(3) == 0) {
                            continue;
                        }

                        world.setBlockState(topPos.up(), crop.with(prop, random.nextInt(max + 1)), 3);
                    }
                }
            }
        }

        @Override
        public boolean generateTerrainBelow() {
            return false;
        }
    }
}
