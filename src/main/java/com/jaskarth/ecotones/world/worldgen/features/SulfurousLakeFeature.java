package com.jaskarth.ecotones.world.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;

public class SulfurousLakeFeature extends EcotonesFeature<SulfurousLakeFeature.Config> {
    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();

    public SulfurousLakeFeature(Codec<Config> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<Config> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        SulfurousLakeFeature.Config config = context.getConfig();

        if (blockPos.getY() <= structureWorldAccess.getBottomY() + 4) {
            return false;
        } else {
            blockPos = blockPos.down(4);
            boolean[] bls = new boolean[2048];
            int i = random.nextInt(4) + 4;

            for(int j = 0; j < i; ++j) {
                double d = random.nextDouble() * 6.0 + 3.0;
                double e = random.nextDouble() * 4.0 + 2.0;
                double f = random.nextDouble() * 6.0 + 3.0;
                double g = random.nextDouble() * (16.0 - d - 2.0) + 1.0 + d / 2.0;
                double h = random.nextDouble() * (8.0 - e - 4.0) + 2.0 + e / 2.0;
                double k = random.nextDouble() * (16.0 - f - 2.0) + 1.0 + f / 2.0;

                for(int l = 1; l < 15; ++l) {
                    for(int m = 1; m < 15; ++m) {
                        for(int n = 1; n < 7; ++n) {
                            double o = ((double)l - g) / (d / 2.0);
                            double p = ((double)n - h) / (e / 2.0);
                            double q = ((double)m - k) / (f / 2.0);
                            double r = o * o + p * p + q * q;
                            if (r < 1.0) {
                                bls[(l * 16 + m) * 8 + n] = true;
                            }
                        }
                    }
                }
            }

            BlockState j = config.fluid().get(random, blockPos);

            for(int d = 0; d < 16; ++d) {
                for(int s = 0; s < 16; ++s) {
                    for(int e = 0; e < 8; ++e) {
                        boolean bl = !bls[(d * 16 + s) * 8 + e]
                            && (
                                d < 15 && bls[((d + 1) * 16 + s) * 8 + e]
                                    || d > 0 && bls[((d - 1) * 16 + s) * 8 + e]
                                    || s < 15 && bls[(d * 16 + s + 1) * 8 + e]
                                    || s > 0 && bls[(d * 16 + (s - 1)) * 8 + e]
                                    || e < 7 && bls[(d * 16 + s) * 8 + e + 1]
                                    || e > 0 && bls[(d * 16 + s) * 8 + (e - 1)]
                            );
                        if (bl) {
                            BlockState f = structureWorldAccess.getBlockState(blockPos.add(d, e, s));
                            if (e >= 4 && f.isLiquid()) {
                                return false;
                            }

                            if (e < 4 && !f.isSolid() && structureWorldAccess.getBlockState(blockPos.add(d, e, s)) != j) {
                                return false;
                            }
                        }
                    }
                }
            }

            for(int d = 0; d < 16; ++d) {
                for(int s = 0; s < 16; ++s) {
                    for(int e = 0; e < 8; ++e) {
                        if (bls[(d * 16 + s) * 8 + e]) {
                            BlockPos bl = blockPos.add(d, e, s);
                            if (this.canReplace(structureWorldAccess.getBlockState(bl))) {
                                boolean f = e >= 4;
                                structureWorldAccess.setBlockState(bl, f ? CAVE_AIR : j, Block.NOTIFY_LISTENERS);
                                if (f) {
                                    structureWorldAccess.scheduleBlockTick(bl, CAVE_AIR.getBlock(), 0);
                                    this.markBlocksAboveForPostProcessing(structureWorldAccess, bl);
                                }
                            }
                        }
                    }
                }
            }

            BlockState d = config.barrier().get(random, blockPos);
            if (!d.isAir()) {
                for(int s = 0; s < 16; ++s) {
                    for(int e = 0; e < 16; ++e) {
                        for(int bl = 0; bl < 8; ++bl) {
                            boolean f = !bls[(s * 16 + e) * 8 + bl]
                                && (
                                    s < 15 && bls[((s + 1) * 16 + e) * 8 + bl]
                                        || s > 0 && bls[((s - 1) * 16 + e) * 8 + bl]
                                        || e < 15 && bls[(s * 16 + e + 1) * 8 + bl]
                                        || e > 0 && bls[(s * 16 + (e - 1)) * 8 + bl]
                                        || bl < 7 && bls[(s * 16 + e) * 8 + bl + 1]
                                        || bl > 0 && bls[(s * 16 + e) * 8 + (bl - 1)]
                                );
                            if (f && (bl < 4 || random.nextInt(2) != 0)) {
                                BlockState blockState = structureWorldAccess.getBlockState(blockPos.add(s, bl, e));
                                if (blockState.isSolid() && !blockState.isIn(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)) {
                                    BlockPos g = blockPos.add(s, bl, e);

                                    // Random check for sulfur ore
                                    BlockState toPlace = d;
                                    if (random.nextInt(6) == 0) {
                                        toPlace = EcotonesBlocks.SULFUR_ORE.getDefaultState();
                                    }

                                    structureWorldAccess.setBlockState(g, toPlace, Block.NOTIFY_LISTENERS);
                                    this.markBlocksAboveForPostProcessing(structureWorldAccess, g);
                                }
                            }
                        }
                    }
                }
            }

            for (int i1 = 0; i1 < 32; i1++) {
                int ax = random.nextInt(16);
                int az = random.nextInt(16);
                int ay = random.nextInt(8);

                BlockPos pos = blockPos.add(ax, ay, az);

                BlockState state = structureWorldAccess.getBlockState(pos);

                if (state.isIn(BlockTags.BASE_STONE_OVERWORLD)) {
                    structureWorldAccess.setBlockState(pos, EcotonesBlocks.SULFUR_ORE.getDefaultState(), Block.NOTIFY_LISTENERS);
                }
            }

            return true;
        }
    }

    private boolean canReplace(BlockState state) {
        return !state.isIn(BlockTags.FEATURES_CANNOT_REPLACE);
    }

    public static record Config(BlockStateProvider fluid, BlockStateProvider barrier) implements FeatureConfig {
        public static final Codec<SulfurousLakeFeature.Config> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                        BlockStateProvider.TYPE_CODEC.fieldOf("fluid").forGetter(SulfurousLakeFeature.Config::fluid),
                        BlockStateProvider.TYPE_CODEC.fieldOf("barrier").forGetter(SulfurousLakeFeature.Config::barrier)
                    )
                    .apply(instance, SulfurousLakeFeature.Config::new)
        );
    }
}