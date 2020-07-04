package supercoder79.ecotones.world.generation;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.JigsawJunction;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.pool.StructurePool.Projection;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;

public abstract class BaseEcotonesChunkGenerator extends ChunkGenerator {
    private static final float[] NOISE_WEIGHT_TABLE = Util.make(new float[13824], (table) -> {
        for(int i = 0; i < 24; ++i) {
            for(int j = 0; j < 24; ++j) {
                for(int k = 0; k < 24; ++k) {
                    table[i * 24 * 24 + j * 24 + k] = (float) computeNoiseWeight(j - 12, k - 12, i - 12);
                }
            }
        }

    });
    private static final BlockState AIR;
    private final int verticalNoiseResolution;
    private final int horizontalNoiseResolution;
    private final int noiseSizeX;
    private final int noiseSizeY;
    private final int noiseSizeZ;
    protected final ChunkRandom random;
    private final OctavePerlinNoiseSampler lowerInterpolatedNoise;
    private final OctavePerlinNoiseSampler upperInterpolatedNoise;
    private final OctavePerlinNoiseSampler interpolationNoise;
    private final NoiseSampler surfaceDepthNoise;
    protected final BlockState defaultBlock;
    protected final BlockState defaultFluid;
    private final int field_24512;
    private final int field_24513;

    public BaseEcotonesChunkGenerator(BiomeSource biomeSource, long l) {
        super(biomeSource, biomeSource, new StructuresConfig(true), l);
        this.verticalNoiseResolution = 8;
        this.horizontalNoiseResolution = 4;
        this.defaultBlock = Blocks.STONE.getDefaultState();
        this.defaultFluid = Blocks.WATER.getDefaultState();
        this.noiseSizeX = 16 / this.horizontalNoiseResolution;
        this.noiseSizeY = 256 / this.verticalNoiseResolution;
        this.noiseSizeZ = 16 / this.horizontalNoiseResolution;
        this.random = new ChunkRandom(l);
        this.lowerInterpolatedNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
        this.upperInterpolatedNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
        this.interpolationNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-7, 0));
        this.surfaceDepthNoise = new OctaveSimplexNoiseSampler(this.random, IntStream.rangeClosed(-3, 0));
        this.field_24512 = -10;
        this.field_24513 = 0;
    }

    public double sampleNoise(int x, int y, int z, double horizontalScale, double verticalScale, double horizontalStretch, double verticalStretch) {
        // To generate it's terrain, Minecraft uses two different perlin noises.
        // It interpolates these two noises to create the final sample at a position.
        // However, the interpolation noise is not all that good and spends most of it's time at > 1 or < 0, rendering
        // one of the noises completely unnecessary in the process.
        // By taking advantage of that, we can reduce the sampling needed per block through the interpolation noise.

        // This controls both the frequency and amplitude of the noise.
        double frequency = 1.0;
        double interpolationValue = 0.0;

        // Calculate interpolation data to decide what noise to sample.
        for (int octave = 0; octave < 8; octave++) {
            double scaledVerticalScale = verticalStretch * frequency;
            double scaledY = y * scaledVerticalScale;

            interpolationValue += sampleOctave(this.interpolationNoise.getOctave(octave),
                    OctavePerlinNoiseSampler.maintainPrecision(x * horizontalStretch * frequency),
                    OctavePerlinNoiseSampler.maintainPrecision(scaledY),
                    OctavePerlinNoiseSampler.maintainPrecision(z * horizontalStretch * frequency), scaledVerticalScale, scaledY, frequency);

            frequency /= 2.0;
        }

        double clampedInterpolation = (interpolationValue / 10.0 + 1.0) / 2.0;

        if (clampedInterpolation >= 1) {
            // Sample only upper noise, as the lower noise will be interpolated out.
            frequency = 1.0;
            double noise = 0.0;
            for (int octave = 0; octave < 16; octave++) {
                double scaledVerticalScale = verticalScale * frequency;
                double scaledY = y * scaledVerticalScale;

                noise += sampleOctave(this.upperInterpolatedNoise.getOctave(octave),
                        OctavePerlinNoiseSampler.maintainPrecision(x * horizontalScale * frequency),
                        OctavePerlinNoiseSampler.maintainPrecision(scaledY),
                        OctavePerlinNoiseSampler.maintainPrecision(z * horizontalScale * frequency), scaledVerticalScale, scaledY, frequency);

                frequency /= 2.0;
            }

            return noise / 512.0;
        } else if (clampedInterpolation <= 0) {
            // Sample only lower noise, as the upper noise will be interpolated out.
            frequency = 1.0;
            double noise = 0.0;
            for (int octave = 0; octave < 16; octave++) {
                double scaledVerticalScale = verticalScale * frequency;
                double scaledY = y * scaledVerticalScale;
                noise += sampleOctave(this.lowerInterpolatedNoise.getOctave(octave),
                        OctavePerlinNoiseSampler.maintainPrecision(x * horizontalScale * frequency),
                        OctavePerlinNoiseSampler.maintainPrecision(scaledY),
                        OctavePerlinNoiseSampler.maintainPrecision(z * horizontalScale * frequency), scaledVerticalScale, scaledY, frequency);

                frequency /= 2.0;
            }

            return noise / 512.0;
        } else {
            // [VanillaCopy] SurfaceChunkGenerator#sampleNoise
            // Sample both and interpolate, as in vanilla.

            frequency = 1.0;
            double lowerNoise = 0.0;
            double upperNoise = 0.0;

            for (int octave = 0; octave < 16; octave++) {
                // Pre calculate these values to share them
                double scaledVerticalScale = verticalScale * frequency;
                double scaledY = y * scaledVerticalScale;
                double xVal = OctavePerlinNoiseSampler.maintainPrecision(x * horizontalScale * frequency);
                double yVal = OctavePerlinNoiseSampler.maintainPrecision(scaledY);
                double zVal = OctavePerlinNoiseSampler.maintainPrecision(z * horizontalScale * frequency);

                upperNoise += sampleOctave(this.upperInterpolatedNoise.getOctave(octave), xVal, yVal, zVal, scaledVerticalScale, scaledY, frequency);
                lowerNoise += sampleOctave(this.lowerInterpolatedNoise.getOctave(octave), xVal, yVal, zVal, scaledVerticalScale, scaledY, frequency);

                frequency /= 2.0;
            }

            // Vanilla behavior, return interpolated noise
            return MathHelper.lerp(clampedInterpolation, lowerNoise / 512.0, upperNoise / 512.0);
        }
    }

    private static double sampleOctave(PerlinNoiseSampler sampler, double x, double y, double z, double scaledVerticalScale, double scaledY, double frequency) {
        return sampler.sample(x, y, z, scaledVerticalScale, scaledY) / frequency;
    }

    protected double[] sampleNoiseColumn(int x, int z) {
        double[] ds = new double[this.noiseSizeY + 1];
        this.sampleNoiseColumn(ds, x, z);
        return ds;
    }

    protected void sampleNoiseColumn(double[] buffer, int x, int z, double d, double e, double f, double g, int i, int j) {
        double[] ds = this.computeNoiseRange(x, z);
        double h = ds[0];
        double k = ds[1];
        double l = this.upperInterpolationStart();
        double m = this.lowerInterpolationStart();

        for(int n = 0; n < this.getNoiseSizeY(); ++n) {
            double o = this.sampleNoise(x, n, z, d, e, f, g);
            o -= this.computeNoiseFalloff(h, k, n);
            if ((double)n > l) {
                o = MathHelper.clampedLerp(o, (double)j, ((double)n - l) / (double)i);
            } else if ((double)n < m) {
                o = MathHelper.clampedLerp(o, -30.0D, (m - (double)n) / (m - 1.0D));
            }

            buffer[n] = o;
        }

    }

    protected abstract double[] computeNoiseRange(int x, int z);

    protected abstract double computeNoiseFalloff(double depth, double scale, int y);

    protected double upperInterpolationStart() {
        return this.getNoiseSizeY() - 4;
    }

    protected double lowerInterpolationStart() {
        return 0.0D;
    }

    public int getHeight(int x, int z, Type heightmapType) {
        return this.sampleHeightmap(x, z, null, heightmapType.getBlockPredicate());
    }

    public BlockView getColumnSample(int x, int z) {
        BlockState[] blockStates = new BlockState[this.noiseSizeY * this.verticalNoiseResolution];
        this.sampleHeightmap(x, z, blockStates, null);
        return new VerticalBlockSample(blockStates);
    }

    private int sampleHeightmap(int i, int j, @Nullable BlockState[] blockStates, @Nullable Predicate<BlockState> predicate) {
        int k = Math.floorDiv(i, this.horizontalNoiseResolution);
        int l = Math.floorDiv(j, this.horizontalNoiseResolution);
        int m = Math.floorMod(i, this.horizontalNoiseResolution);
        int n = Math.floorMod(j, this.horizontalNoiseResolution);
        double d = (double)m / (double)this.horizontalNoiseResolution;
        double e = (double)n / (double)this.horizontalNoiseResolution;
        double[][] noiseData = new double[][]{this.sampleNoiseColumn(k, l), this.sampleNoiseColumn(k, l + 1), this.sampleNoiseColumn(k + 1, l), this.sampleNoiseColumn(k + 1, l + 1)};

        for(int o = this.noiseSizeY - 1; o >= 0; --o) {
            double f = noiseData[0][o];
            double g = noiseData[1][o];
            double h = noiseData[2][o];
            double p = noiseData[3][o];
            double q = noiseData[0][o + 1];
            double r = noiseData[1][o + 1];
            double s = noiseData[2][o + 1];
            double t = noiseData[3][o + 1];

            for(int u = this.verticalNoiseResolution - 1; u >= 0; --u) {
                double v = (double)u / (double)this.verticalNoiseResolution;
                double w = MathHelper.lerp3(v, d, e, f, q, h, s, g, r, p, t);
                int x = o * this.verticalNoiseResolution + u;
                BlockState blockState = this.getBlockState(w, x);
                if (blockStates != null) {
                    blockStates[x] = blockState;
                }

                if (predicate != null && predicate.test(blockState)) {
                    return x + 1;
                }
            }
        }

        return 0;
    }

    protected BlockState getBlockState(double density, int y) {
        BlockState blockState3;
        if (density > 0.0D) {
            blockState3 = this.defaultBlock;
        } else if (y < this.getSeaLevel()) {
            blockState3 = this.defaultFluid;
        } else {
            blockState3 = AIR;
        }

        return blockState3;
    }

    protected abstract void sampleNoiseColumn(double[] buffer, int x, int z);

    public int getNoiseSizeY() {
        return this.noiseSizeY + 1;
    }

    public void buildSurface(ChunkRegion region, Chunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        int i = chunkPos.x;
        int j = chunkPos.z;
        ChunkRandom chunkRandom = new ChunkRandom();
        chunkRandom.setTerrainSeed(i, j);
        ChunkPos chunkPos2 = chunk.getPos();
        int k = chunkPos2.getStartX();
        int l = chunkPos2.getStartZ();
        double d = 0.0625D;
        Mutable mutable = new Mutable();

        for(int m = 0; m < 16; ++m) {
            for(int n = 0; n < 16; ++n) {
                int o = k + m;
                int p = l + n;
                int q = chunk.sampleHeightmap(Type.WORLD_SURFACE_WG, m, n) + 1;
                double e = this.surfaceDepthNoise.sample((double)o * 0.0625D, (double)p * 0.0625D, 0.0625D, (double)m * 0.0625D) * 15.0D;
                region.getBiome(mutable.set(k + m, q, l + n)).buildSurface(chunkRandom, chunk, o, p, q, e, this.defaultBlock, this.defaultFluid, this.getSeaLevel(), region.getSeed());
            }
        }

        this.buildBedrock(chunk, chunkRandom);
    }

    protected void buildBedrock(Chunk chunk, Random random) {
        Mutable mutable = new Mutable();
        int i = chunk.getPos().getStartX();
        int j = chunk.getPos().getStartZ();
        int k = this.field_24512;
        int l = this.field_24513;
        Iterator var8 = BlockPos.iterate(i, 0, j, i + 15, 0, j + 15).iterator();

        while(true) {
            BlockPos blockPos;
            int n;
            do {
                if (!var8.hasNext()) {
                    return;
                }

                blockPos = (BlockPos)var8.next();
                if (l > 0) {
                    for(n = l; n >= l - 4; --n) {
                        if (n >= l - random.nextInt(5)) {
                            chunk.setBlockState(mutable.set(blockPos.getX(), n, blockPos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                        }
                    }
                }
            } while(k >= 256);

            for(int o = 4; o >= 0; --o) {
                if (o <= random.nextInt(5)) {
                    chunk.setBlockState(mutable.set(blockPos.getX(), o, blockPos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                }
            }
        }
    }

    public void populateNoise(WorldAccess world, StructureAccessor structureAccessor, Chunk chunk) {
        ObjectList<StructurePiece> objectList = new ObjectArrayList<>(10);
        ObjectList<JigsawJunction> objectList2 = new ObjectArrayList<>(32);
        ChunkPos chunkPos = chunk.getPos();
        int chunkX = chunkPos.x;
        int chunkZ = chunkPos.z;
        int chunkStartX = chunkX << 4;
        int chunkStartZ = chunkZ << 4;

        for (StructureFeature<?> feature : StructureFeature.field_24861) {
            structureAccessor.getStructuresWithChildren(ChunkSectionPos.from(chunkPos, 0), feature).forEach((structureStart) -> {
                Iterator var6 = structureStart.getChildren().iterator();

                while (true) {
                    while (true) {
                        StructurePiece structurePiece;
                        do {
                            if (!var6.hasNext()) {
                                return;
                            }

                            structurePiece = (StructurePiece) var6.next();
                        } while (!structurePiece.intersectsChunk(chunkPos, 12));

                        if (structurePiece instanceof PoolStructurePiece) {
                            PoolStructurePiece poolStructurePiece = (PoolStructurePiece) structurePiece;
                            Projection projection = poolStructurePiece.getPoolElement().getProjection();
                            if (projection == Projection.RIGID) {
                                objectList.add(poolStructurePiece);
                            }

                            Iterator var10 = poolStructurePiece.getJunctions().iterator();

                            while (var10.hasNext()) {
                                JigsawJunction jigsawJunction = (JigsawJunction) var10.next();
                                int kx = jigsawJunction.getSourceX();
                                int lx = jigsawJunction.getSourceZ();
                                if (kx > chunkStartX - 12 && lx > chunkStartZ - 12 && kx < chunkStartX + 15 + 12 && lx < chunkStartZ + 15 + 12) {
                                    objectList2.add(jigsawJunction);
                                }
                            }
                        } else {
                            objectList.add(structurePiece);
                        }
                    }
                }
            });
        }

        double[][][] noiseData = new double[2][this.noiseSizeZ + 1][this.noiseSizeY + 1];

        for(int z = 0; z < this.noiseSizeZ + 1; ++z) {
            noiseData[0][z] = new double[this.noiseSizeY + 1];
            this.sampleNoiseColumn(noiseData[0][z], chunkX * this.noiseSizeX, chunkZ * this.noiseSizeZ + z);
            noiseData[1][z] = new double[this.noiseSizeY + 1];
        }

        ProtoChunk protoChunk = (ProtoChunk)chunk;
        Heightmap heightmap = protoChunk.getHeightmap(Type.OCEAN_FLOOR_WG);
        Heightmap heightmap2 = protoChunk.getHeightmap(Type.WORLD_SURFACE_WG);
        Mutable mutable = new Mutable();
        ObjectListIterator<StructurePiece> objectListIterator = objectList.iterator();
        ObjectListIterator<JigsawJunction> objectListIterator2 = objectList2.iterator();

        for(int noiseX = 0; noiseX < this.noiseSizeX; ++noiseX) {
            int noiseZ;
            for(noiseZ = 0; noiseZ < this.noiseSizeZ + 1; ++noiseZ) {
                this.sampleNoiseColumn(noiseData[1][noiseZ], chunkX * this.noiseSizeX + noiseX + 1, chunkZ * this.noiseSizeZ + noiseZ);
            }

            for(noiseZ = 0; noiseZ < this.noiseSizeZ; ++noiseZ) {
                ChunkSection chunkSection = protoChunk.getSection(15);
                chunkSection.lock();

                for(int noiseY = this.noiseSizeY - 1; noiseY >= 0; --noiseY) {
                    double d = noiseData[0][noiseZ][noiseY];
                    double e = noiseData[0][noiseZ + 1][noiseY];
                    double f = noiseData[1][noiseZ][noiseY];
                    double g = noiseData[1][noiseZ + 1][noiseY];
                    double h = noiseData[0][noiseZ][noiseY + 1];
                    double r = noiseData[0][noiseZ + 1][noiseY + 1];
                    double s = noiseData[1][noiseZ][noiseY + 1];
                    double t = noiseData[1][noiseZ + 1][noiseY + 1];

                    for(int u = this.verticalNoiseResolution - 1; u >= 0; --u) {
                        int v = noiseY * this.verticalNoiseResolution + u;
                        int w = v & 15;
                        int x = v >> 4;
                        if (chunkSection.getYOffset() >> 4 != x) {
                            chunkSection.unlock();
                            chunkSection = protoChunk.getSection(x);
                            chunkSection.lock();
                        }

                        double y = (double)u / (double)this.verticalNoiseResolution;
                        double z = MathHelper.lerp(y, d, h);
                        double aa = MathHelper.lerp(y, f, s);
                        double ab = MathHelper.lerp(y, e, r);
                        double ac = MathHelper.lerp(y, g, t);

                        for(int ad = 0; ad < this.horizontalNoiseResolution; ++ad) {
                            int ae = chunkStartX + noiseX * this.horizontalNoiseResolution + ad;
                            int af = ae & 15;
                            double ag = (double)ad / (double)this.horizontalNoiseResolution;
                            double ah = MathHelper.lerp(ag, z, aa);
                            double ai = MathHelper.lerp(ag, ab, ac);

                            for(int aj = 0; aj < this.horizontalNoiseResolution; ++aj) {
                                int ak = chunkStartZ + noiseZ * this.horizontalNoiseResolution + aj;
                                int al = ak & 15;
                                double am = (double)aj / (double)this.horizontalNoiseResolution;
                                double an = MathHelper.lerp(am, ah, ai);
                                double ao = MathHelper.clamp(an / 200.0D, -1.0D, 1.0D);

                                int at;
                                int au;
                                int ar;
                                for(ao = ao / 2.0D - ao * ao * ao / 24.0D; objectListIterator.hasNext(); ao += getNoiseWeight(at, au, ar) * 0.8D) {
                                    StructurePiece structurePiece = objectListIterator.next();
                                    BlockBox blockBox = structurePiece.getBoundingBox();
                                    at = Math.max(0, Math.max(blockBox.minX - ae, ae - blockBox.maxX));
                                    au = v - (blockBox.minY + (structurePiece instanceof PoolStructurePiece ? ((PoolStructurePiece)structurePiece).getGroundLevelDelta() : 0));
                                    ar = Math.max(0, Math.max(blockBox.minZ - ak, ak - blockBox.maxZ));
                                }

                                objectListIterator.back(objectList.size());

                                while(objectListIterator2.hasNext()) {
                                    JigsawJunction jigsawJunction = objectListIterator2.next();
                                    int as = ae - jigsawJunction.getSourceX();
                                    at = v - jigsawJunction.getSourceGroundY();
                                    au = ak - jigsawJunction.getSourceZ();
                                    ao += getNoiseWeight(as, at, au) * 0.4D;
                                }

                                objectListIterator2.back(objectList2.size());
                                BlockState blockState = this.getBlockState(ao, v);
                                if (blockState != AIR) {
                                    if (blockState.getLuminance() != 0) {
                                        mutable.set(ae, v, ak);
                                        protoChunk.addLightSource(mutable);
                                    }

                                    chunkSection.setBlockState(af, w, al, blockState, false);
                                    heightmap.trackUpdate(af, v, al, blockState);
                                    heightmap2.trackUpdate(af, v, al, blockState);
                                }
                            }
                        }
                    }
                }

                chunkSection.unlock();
            }

            double[][] es = noiseData[0];
            noiseData[0] = noiseData[1];
            noiseData[1] = es;
        }

    }

    private static double getNoiseWeight(int x, int y, int z) {
        int l = x + 12;
        int m = y + 12;
        int n = z + 12;
        if (l >= 0 && l < 24) {
            if (m >= 0 && m < 24) {
                return n >= 0 && n < 24 ? (double) NOISE_WEIGHT_TABLE[n * 24 * 24 + l * 24 + m] : 0.0D;
            } else {
                return 0.0D;
            }
        } else {
            return 0.0D;
        }
    }

    private static double computeNoiseWeight(int i, int j, int k) {
        double d = (double)(i * i + k * k);
        double e = (double)j + 0.5D;
        double f = e * e;
        double g = Math.pow(2.718281828459045D, -(f / 16.0D + d / 16.0D));
        double h = -e * MathHelper.fastInverseSqrt(f / 2.0D + d / 2.0D) / 2.0D;
        return h * g;
    }

    static {
        AIR = Blocks.AIR.getDefaultState();
    }
}
