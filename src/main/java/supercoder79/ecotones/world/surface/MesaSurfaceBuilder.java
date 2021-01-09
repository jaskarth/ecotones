package supercoder79.ecotones.world.surface;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.BadlandsSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class MesaSurfaceBuilder extends BadlandsSurfaceBuilder {
    private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.getDefaultState();
    private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
    private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.getDefaultState();

    private final Predicate<Integer> grassPlacement;
    private final Function<Long, BlockState[]> layerSetup;
    private final BlockState cosineState;

    public MesaSurfaceBuilder(Codec<TernarySurfaceConfig> codec, Predicate<Integer> grassPlacement, Function<Long, BlockState[]> layerSetup, BlockState cosineState) {
        super(codec);
        this.grassPlacement = grassPlacement;
        this.layerSetup = layerSetup;
        this.cosineState = cosineState;
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState stone, BlockState water, int seaLevel, long seed, TernarySurfaceConfig config) {
        int localX = x & 15;
        int localZ = z & 15;
        BlockState blockState3 = WHITE_TERRACOTTA;
        BlockState blockState4 = biome.getGenerationSettings().getSurfaceConfig().getUnderMaterial();
        int dirtDepth = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        boolean cosineNoise = Math.cos(noise / 3.0D * 3.141592653589793D) > 0.0D;
        int placedDirtDepth = -1;
        boolean setTop = false;
        int r = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int y = height; y >= 0; --y) {
            if (r < 15) {
                mutable.set(localX, y, localZ);
                BlockState stateHere = chunk.getBlockState(mutable);
                if (stateHere.isAir()) {
                    placedDirtDepth = -1;
                } else if (stateHere.isOf(stone.getBlock())) {
                    if (placedDirtDepth == -1) {
                        setTop = false;
                        if (dirtDepth <= 0) {
                            blockState3 = Blocks.AIR.getDefaultState();
                            blockState4 = stone;
                        } else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
                            blockState3 = WHITE_TERRACOTTA;
                            blockState4 = biome.getGenerationSettings().getSurfaceConfig().getUnderMaterial();
                        }

                        if (y < seaLevel && (blockState3 == null || blockState3.isAir())) {
                            blockState3 = water;
                        }

                        placedDirtDepth = dirtDepth + Math.max(0, y - seaLevel);
                        if (y >= seaLevel - 1) {
                            if (this.grassPlacement.test(y)) {
                                chunk.setBlockState(mutable, Blocks.GRASS_BLOCK.getDefaultState(), false);
                            } else if (y > seaLevel + 3 + dirtDepth) {
                                BlockState placeLayer;
                                if (y >= 64 && y <= 127) {
                                    if (cosineNoise) {
                                        placeLayer = this.cosineState;
                                    } else {
                                        placeLayer = this.calculateLayerBlockState(x, y, z);
                                    }
                                } else {
                                    placeLayer = ORANGE_TERRACOTTA;
                                }

                                chunk.setBlockState(mutable, placeLayer, false);
                            } else {
                                chunk.setBlockState(mutable, biome.getGenerationSettings().getSurfaceConfig().getTopMaterial(), false);
                                setTop = true;
                            }
                        } else {
                            chunk.setBlockState(mutable, blockState4, false);
                            if (blockState4 == WHITE_TERRACOTTA) {
                                chunk.setBlockState(mutable, ORANGE_TERRACOTTA, false);
                            }
                        }
                    } else if (placedDirtDepth > 0) {
                        --placedDirtDepth;
                        if (setTop) {
                            chunk.setBlockState(mutable, ORANGE_TERRACOTTA, false);
                        } else {
                            chunk.setBlockState(mutable, this.calculateLayerBlockState(x, y, z), false);
                        }
                    }

                    ++r;
                }
            }
        }
    }

    @Override
    public void initSeed(long seed) {
        if (this.seed != seed || this.layerBlocks == null) {
            this.initLayerBlocks(seed);
        }

        if (this.seed != seed || this.heightCutoffNoise == null || this.heightNoise == null) {
            ChunkRandom chunkRandom = new ChunkRandom(seed);
            this.heightCutoffNoise = new OctaveSimplexNoiseSampler(chunkRandom, IntStream.rangeClosed(-3, 0));
            this.heightNoise = new OctaveSimplexNoiseSampler(chunkRandom, ImmutableList.of(0));
            this.layerNoise = new OctaveSimplexNoiseSampler(chunkRandom, ImmutableList.of(0));
        }

        this.seed = seed;
    }

    @Override
    protected void initLayerBlocks(long seed) {
        this.layerBlocks = this.layerSetup.apply(seed);
    }
}
