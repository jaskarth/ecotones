package supercoder79.ecotones.world.structure.gen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesCollector;
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

import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class CampfireGenerator {
    public static void generate(BlockPos pos, StructurePiecesCollector pieces, Random random, Block logSource) {
        pieces.addPiece(new Piece(pos.add(random.nextInt(16), 0, random.nextInt(16)), logSource));
    }

    public static class Piece extends StructurePiece {
        private final BlockPos pos;
        private final Block logSource;

        protected Piece(BlockPos pos, Block logSource) {
            super(EcotonesStructurePieces.CAMPFIRE, 0, BoxHelper.box(pos.add(-3, -3, -3), pos.add(3, 3, 3)));
            this.pos = pos;
            this.logSource = logSource;
        }

        public Piece(NbtCompound nbt) {
            super(EcotonesStructurePieces.CAMPFIRE, nbt);
            this.pos = new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
            this.logSource = Registry.BLOCK.get(new Identifier(nbt.getString("log_source")));
        }

        @Override
        protected void writeNbt(StructureContext context, NbtCompound tag) {
            tag.putInt("x", this.pos.getX());
            tag.putInt("y", this.pos.getY());
            tag.putInt("z", this.pos.getZ());
            tag.putString("log_source", Registry.BLOCK.getId(this.logSource).toString());
        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, net.minecraft.util.math.random.Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
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
                            BitSet chest = new BitSet(27);

                            int stickStacks = 4 + random.nextInt(4);
                            int foodStacks = 3 + random.nextInt(4);
                            int charcoalStacks = 1 + random.nextInt(2);
                            int jarStacks = random.nextBoolean() ? 0 : (random.nextInt(2) + 1);
                            int woodStacks = random.nextInt(3);
                            int plankStacks = random.nextInt(3);
                            int specials = random.nextInt(3);

                            if (be != null) {
                                int idx = random.nextInt(27);
                                be.setStack(idx, generateBook(new Random(random.nextLong())));
                                chest.set(idx);

                                for (int i = 0; i < foodStacks; i++) {
                                    idx = random.nextInt(27);

                                    if (!chest.get(idx)) {
                                        Item item = Items.COOKED_BEEF;
                                        if (random.nextBoolean()) {
                                            item = random.nextBoolean() ? Items.COOKED_PORKCHOP : Items.COOKED_MUTTON;
                                        }

                                        be.setStack(idx, new ItemStack(item, 1 + random.nextInt(8)));
                                    }
                                }

                                place(stickStacks, random, chest, be, () -> new ItemStack(Items.STICK, 1 + random.nextInt(8)));
                                place(charcoalStacks, random, chest, be, () -> new ItemStack(Items.CHARCOAL, 1 + random.nextInt(3)));
                                place(jarStacks, random, chest, be, () -> new ItemStack(EcotonesItems.JAR, 1 + random.nextInt(4)));
                                place(woodStacks, random, chest, be, () -> new ItemStack(this.logSource, 2 + random.nextInt(4)));
                                place(plankStacks, random, chest, be, () -> new ItemStack(logToPlank(this.logSource).getBlock(), 2 + random.nextInt(3)));
                                Item special = special(this.logSource);
                                if (special != null) {
                                    place(specials, random, chest, be, () -> new ItemStack(special, 1 + random.nextInt(2)));
                                }
                            }

                            placedChest = true;
                        }
                    }
                }
            }
        }

        private void place(int stacks, net.minecraft.util.math.random.Random random, BitSet chest, ChestBlockEntity be, Supplier<ItemStack> stack) {
            for (int i = 0; i < stacks; i++) {
                int idx = random.nextInt(27);

                if (!chest.get(idx)) {
                    be.setStack(idx, stack.get());
                }
            }
        }

        private static BlockState logToPlank(Block log) {
            if (log == Blocks.OAK_LOG) {
                return Blocks.OAK_PLANKS.getDefaultState();
            } else if (log == Blocks.SPRUCE_LOG) {
                return Blocks.SPRUCE_PLANKS.getDefaultState();
            } else if (log == Blocks.BIRCH_LOG) {
                return Blocks.BIRCH_PLANKS.getDefaultState();
            } else if (log == Blocks.DARK_OAK_LOG) {
                return Blocks.DARK_OAK_PLANKS.getDefaultState();
            }

            throw new IllegalStateException("Invalid log type: " + log);
        }

        private static Item special(Block log) {
            if (log == Blocks.OAK_LOG) {
                return Items.APPLE;
            } else if (log == Blocks.SPRUCE_LOG) {
                return EcotonesItems.BLUEBERRIES;
            }

            return null;
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
            stack.setNbt(tag);

            return stack;
        }
    }
}
