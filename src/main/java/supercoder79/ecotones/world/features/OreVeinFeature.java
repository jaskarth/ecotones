package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.util.noise.OctaveNoiseSampler;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.util.vein.OreVein;
import supercoder79.ecotones.util.vein.OreVeins;
import supercoder79.ecotones.world.features.config.RockFeatureConfig;

import java.util.Optional;

public class OreVeinFeature extends EcotonesFeature<DefaultFeatureConfig> {
    private long seed;
    private OctaveNoiseSampler<OpenSimplexNoise> enabledNoise;
    private OctaveNoiseSampler<OpenSimplexNoise> floorNoise;
    private OctaveNoiseSampler<OpenSimplexNoise> ceilNoise;
    private OctaveNoiseSampler<OpenSimplexNoise> veinANoise;
    private OctaveNoiseSampler<OpenSimplexNoise> veinBNoise;
    private OctaveNoiseSampler<OpenSimplexNoise> veinQualityNoise;
    private OctaveNoiseSampler<OpenSimplexNoise> xNoise;
    private OctaveNoiseSampler<OpenSimplexNoise> zNoise;

    public OreVeinFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();

        initSeed(world.getSeed());

        OreVein vein = null;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // Debug chunk removal
//                for (int y = 0; y < 256; y++) {
//                    world.setBlockState(pos.add(x, y, z), Blocks.AIR.getDefaultState(), 3);
//                }

                if (this.enabledNoise.sample(pos.getX() + x, pos.getZ() + z) > 0.2) {
                    OreVein res = generateColumn(world, random, pos.add(x, 0, z));
                    if (vein == null) {
                        vein = res;
                    }
                }
            }
        }

        if (vein != null && random.nextInt(36) == 0) {
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            int y = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, pos.getX() + x, pos.getZ() + z);

            RockFeatureConfig config = new RockFeatureConfig(vein.getMainState(), 1, true);
            EcotonesFeatures.ROCK.generate(new FeatureContext<>(Optional.empty(), world, context.getGenerator(), random, pos.add(x, y, z), config));
        }

        return true;
    }

    private OreVein generateColumn(StructureWorldAccess world, Random random, BlockPos pos) {
        int x = pos.getX();
        int z = pos.getZ();
        double xNoise = this.xNoise.sample(x, z);
        double zNoise = this.zNoise.sample(x, z);

        OreVein vein = null;
        double dist = Double.MAX_VALUE;

        for (OreVein v : OreVeins.VEINS) {
            double distHere = v.getPoint().dist(xNoise, zNoise);
            if (distHere < dist) {
                dist = distHere;
                vein = v;
            }
        }

        if (vein == null) {
            throw new IllegalArgumentException("Vein was null or no veins exist! How is this possible?");
        }

        int floorY = (int) (this.floorNoise.sample(x, z) * vein.getySpread() + vein.getMinY());
        int ceilY = (int) (this.ceilNoise.sample(x, z) * vein.getySpread() + vein.getMaxY());

        for (int y = floorY; y <= ceilY; y++) {
            if (y < 0) {
                continue;
            }

            BlockPos local = pos.withY(y);
            if (!world.getBlockState(local).isIn(BlockTags.BASE_STONE_OVERWORLD)) {
                continue;
            }

            if (isVein(this.veinANoise.sample(x, y * 1.75, z), this.veinBNoise.sample(x, y * 1.75, z))) {
                double quality = this.veinQualityNoise.sample(x, y, z);
                double skipChance = MathHelper.clampedMap(quality, -1, 1, 0.875, 0.4);

                if (random.nextDouble() < skipChance) {
                    continue;
                }

                if (random.nextDouble() < MathHelper.clampedMap(quality, -0.25, 1.0, 0, 0.65)) {
                    double rareChance = MathHelper.clampedMap(quality, 0.45, 1.0, 0.02, 0.4);

                    if (random.nextDouble() < rareChance) {
                        world.setBlockState(local, vein.getRareState(), 3);
                    } else {
                        world.setBlockState(local, vein.getOuterState(), 3);
                    }
                } else {
                    world.setBlockState(local, vein.getMainState(), 3);
                }
            }
        }

        return vein;
    }

    private static boolean isVein(double veinA, double veinB) {
        double d = Math.abs(1.0D * veinA) - 0.08;
        double e = Math.abs(1.0D * veinB) - 0.08;
        return Math.max(d, e) < 0.0D;
    }

    private void initSeed(long seed) {
        if (this.seed != seed || this.enabledNoise == null) {
            java.util.Random random = new java.util.Random(seed);
            this.seed = seed;
            this.enabledNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 3, 512 + 256, 1, 1);
            this.floorNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 2, 256, 1, 1);
            this.ceilNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 2, 256, 1, 1);
            this.veinANoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 1, 128, 1, 1);
            this.veinBNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 1, 128, 1, 1);
            this.veinQualityNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 3, 80, 1, 1);
            this.xNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 2, 512, 1, 1);
            this.zNoise = new OctaveNoiseSampler<>(OpenSimplexNoise.class, random, 2, 512, 1, 1);
        }
    }
}