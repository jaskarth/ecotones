package supercoder79.ecotones.world.structure.gen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.world.ServerWorld;
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
import supercoder79.ecotones.items.EcotonesItems;
import supercoder79.ecotones.util.BoxHelper;
import supercoder79.ecotones.util.CampfireLogHelper;
import supercoder79.ecotones.util.book.*;
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
            super(EcotonesStructurePieces.CAMPFIRE, 0, BoxHelper.box(pos.add(-3, -3, -3), pos.add(3, 3, 3)));
            this.pos = pos;
            this.logSource = logSource;
        }

        public Piece(ServerWorld world, NbtCompound nbt) {
            super(EcotonesStructurePieces.CAMPFIRE, nbt);
            this.pos = new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
            this.logSource = Registry.BLOCK.get(new Identifier(nbt.getString("log_source")));
        }

        @Override
        protected void writeNbt(ServerWorld world, NbtCompound tag) {
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
                boolean placedChest = false;

                for (Direction direction : FeatureHelper.HORIZONTAL) {
                    BlockPos local = scaledPos.offset(direction, 2);

                    // Place seat if we have 2 blocks of air and 1 solid block
                    if (world.getBlockState(local).isAir() && world.getBlockState(local.up()).isAir() && world.getBlockState(local.down()).isOpaque()) {
                        // 2/3 chance to place log or place chest if we haven't
                        if (random.nextInt(3) > 0 || placedChest) {
                            BlockState state = CampfireLogHelper.stateFor(this.logSource);
                            world.setBlockState(local, state, 3);
                        } else {
                            world.setBlockState(local, Blocks.CHEST.getDefaultState(), 3);

                            // "We have loot tables at home"
                            ChestBlockEntity be = (ChestBlockEntity) world.getBlockEntity(local);

                            if (be != null) {
                                be.setStack(random.nextInt(27), generateBook(random));
                            }

                            placedChest = true;
                        }
                    }
                }
            }

            return true;
        }

        private ItemStack generateBook(Random random) {
            ItemStack stack = new ItemStack(EcotonesItems.ECOTONES_BOOK);
            NbtCompound tag = new NbtCompound();

            BookGenerator generator = BookList.get(random);
            tag.putString("title", TitleGenerator.generate(generator, random));
            tag.putString("author", AuthorGenerator.generate(random));

            NbtList pages = new NbtList();
            List<String> pageList = PageGenerator.generate(generator, random);

            for (String page : pageList) {
                pages.add(NbtString.of(page));
            }

            tag.put("pages", pages);
            stack.setTag(tag);

            return stack;
        }
    }
}
