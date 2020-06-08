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
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;

public abstract class BaseEcotonesChunkGenerator extends ChunkGenerator {
    private static final float[] field_16649 = (float[])Util.make(new float[13824], (fs) -> {
        for(int i = 0; i < 24; ++i) {
            for(int j = 0; j < 24; ++j) {
                for(int k = 0; k < 24; ++k) {
                    fs[i * 24 * 24 + j * 24 + k] = (float)method_16571(j - 12, k - 12, i - 12);
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
    private final OctavePerlinNoiseSampler field_16574;
    private final OctavePerlinNoiseSampler field_16581;
    private final OctavePerlinNoiseSampler field_16575;
    private final NoiseSampler surfaceDepthNoise;
    protected final BlockState defaultBlock;
    protected final BlockState defaultFluid;
    private final int field_24512;
    private final int field_24513;

    public BaseEcotonesChunkGenerator(BiomeSource biomeSource, long l) {
        super(biomeSource, biomeSource, new StructuresConfig(false), l);
        this.verticalNoiseResolution = 8;
        this.horizontalNoiseResolution = 4;
        this.defaultBlock = Blocks.STONE.getDefaultState();
        this.defaultFluid = Blocks.WATER.getDefaultState();
        this.noiseSizeX = 16 / this.horizontalNoiseResolution;
        this.noiseSizeY = 256 / this.verticalNoiseResolution;
        this.noiseSizeZ = 16 / this.horizontalNoiseResolution;
        this.random = new ChunkRandom(l);
        this.field_16574 = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
        this.field_16581 = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
        this.field_16575 = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-7, 0));
        this.surfaceDepthNoise = new OctaveSimplexNoiseSampler(this.random, IntStream.rangeClosed(-3, 0));
        this.field_24512 = -10;
        this.field_24513 = 0;
    }

    private double sampleNoise(int x, int y, int z, double d, double e, double f, double g) {
        double h = 0.0D;
        double i = 0.0D;
        double j = 0.0D;
        double k = 1.0D;

        for(int l = 0; l < 16; ++l) {
            double m = OctavePerlinNoiseSampler.maintainPrecision((double)x * d * k);
            double n = OctavePerlinNoiseSampler.maintainPrecision((double)y * e * k);
            double o = OctavePerlinNoiseSampler.maintainPrecision((double)z * d * k);
            double p = e * k;
            PerlinNoiseSampler perlinNoiseSampler = this.field_16574.getOctave(l);
            if (perlinNoiseSampler != null) {
                h += perlinNoiseSampler.sample(m, n, o, p, (double)y * p) / k;
            }

            PerlinNoiseSampler perlinNoiseSampler2 = this.field_16581.getOctave(l);
            if (perlinNoiseSampler2 != null) {
                i += perlinNoiseSampler2.sample(m, n, o, p, (double)y * p) / k;
            }

            if (l < 8) {
                PerlinNoiseSampler perlinNoiseSampler3 = this.field_16575.getOctave(l);
                if (perlinNoiseSampler3 != null) {
                    j += perlinNoiseSampler3.sample(OctavePerlinNoiseSampler.maintainPrecision((double)x * f * k), OctavePerlinNoiseSampler.maintainPrecision((double)y * g * k), OctavePerlinNoiseSampler.maintainPrecision((double)z * f * k), g * k, (double)y * g * k) / k;
                }
            }

            k /= 2.0D;
        }

        return MathHelper.clampedLerp(h / 512.0D, i / 512.0D, (j / 10.0D + 1.0D) / 2.0D);
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
        double l = this.method_16409();
        double m = this.method_16410();

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

    protected double method_16409() {
        return (double)(this.getNoiseSizeY() - 4);
    }

    protected double method_16410() {
        return 0.0D;
    }

    public int getHeight(int x, int z, Type heightmapType) {
        return this.sampleHeightmap(x, z, (BlockState[])null, heightmapType.getBlockPredicate());
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
        double[][] ds = new double[][]{this.sampleNoiseColumn(k, l), this.sampleNoiseColumn(k, l + 1), this.sampleNoiseColumn(k + 1, l), this.sampleNoiseColumn(k + 1, l + 1)};

        for(int o = this.noiseSizeY - 1; o >= 0; --o) {
            double f = ds[0][o];
            double g = ds[1][o];
            double h = ds[2][o];
            double p = ds[3][o];
            double q = ds[0][o + 1];
            double r = ds[1][o + 1];
            double s = ds[2][o + 1];
            double t = ds[3][o + 1];

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

            for(n = k + 4; n >= k; --n) {
                if (n <= k + random.nextInt(5)) {
                    chunk.setBlockState(mutable.set(blockPos.getX(), n, blockPos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                }
            }
        }
    }

    public void populateNoise(WorldAccess world, StructureAccessor structureAccessor, Chunk chunk) {
        ObjectList<StructurePiece> objectList = new ObjectArrayList(10);
        ObjectList<JigsawJunction> objectList2 = new ObjectArrayList(32);
        ChunkPos chunkPos = chunk.getPos();
        int i = chunkPos.x;
        int j = chunkPos.z;
        int k = i << 4;
        int l = j << 4;
        Iterator var11 = StructureFeature.field_24861.iterator();

        while(var11.hasNext()) {
            StructureFeature<?> structureFeature = (StructureFeature)var11.next();
            structureAccessor.getStructuresWithChildren(ChunkSectionPos.from(chunkPos, 0), structureFeature).forEach((structureStart) -> {
                Iterator var6 = structureStart.getChildren().iterator();

                while(true) {
                    while(true) {
                        StructurePiece structurePiece;
                        do {
                            if (!var6.hasNext()) {
                                return;
                            }

                            structurePiece = (StructurePiece)var6.next();
                        } while(!structurePiece.intersectsChunk(chunkPos, 12));

                        if (structurePiece instanceof PoolStructurePiece) {
                            PoolStructurePiece poolStructurePiece = (PoolStructurePiece)structurePiece;
                            Projection projection = poolStructurePiece.getPoolElement().getProjection();
                            if (projection == Projection.RIGID) {
                                objectList.add(poolStructurePiece);
                            }

                            Iterator var10 = poolStructurePiece.getJunctions().iterator();

                            while(var10.hasNext()) {
                                JigsawJunction jigsawJunction = (JigsawJunction)var10.next();
                                int kx = jigsawJunction.getSourceX();
                                int lx = jigsawJunction.getSourceZ();
                                if (kx > k - 12 && lx > l - 12 && kx < k + 15 + 12 && lx < l + 15 + 12) {
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

        double[][][] ds = new double[2][this.noiseSizeZ + 1][this.noiseSizeY + 1];

        for(int m = 0; m < this.noiseSizeZ + 1; ++m) {
            ds[0][m] = new double[this.noiseSizeY + 1];
            this.sampleNoiseColumn(ds[0][m], i * this.noiseSizeX, j * this.noiseSizeZ + m);
            ds[1][m] = new double[this.noiseSizeY + 1];
        }

        ProtoChunk protoChunk = (ProtoChunk)chunk;
        Heightmap heightmap = protoChunk.getHeightmap(Type.OCEAN_FLOOR_WG);
        Heightmap heightmap2 = protoChunk.getHeightmap(Type.WORLD_SURFACE_WG);
        Mutable mutable = new Mutable();
        ObjectListIterator<StructurePiece> objectListIterator = objectList.iterator();
        ObjectListIterator<JigsawJunction> objectListIterator2 = objectList2.iterator();

        for(int n = 0; n < this.noiseSizeX; ++n) {
            int p;
            for(p = 0; p < this.noiseSizeZ + 1; ++p) {
                this.sampleNoiseColumn(ds[1][p], i * this.noiseSizeX + n + 1, j * this.noiseSizeZ + p);
            }

            for(p = 0; p < this.noiseSizeZ; ++p) {
                ChunkSection chunkSection = protoChunk.getSection(15);
                chunkSection.lock();

                for(int q = this.noiseSizeY - 1; q >= 0; --q) {
                    double d = ds[0][p][q];
                    double e = ds[0][p + 1][q];
                    double f = ds[1][p][q];
                    double g = ds[1][p + 1][q];
                    double h = ds[0][p][q + 1];
                    double r = ds[0][p + 1][q + 1];
                    double s = ds[1][p][q + 1];
                    double t = ds[1][p + 1][q + 1];

                    for(int u = this.verticalNoiseResolution - 1; u >= 0; --u) {
                        int v = q * this.verticalNoiseResolution + u;
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
                            int ae = k + n * this.horizontalNoiseResolution + ad;
                            int af = ae & 15;
                            double ag = (double)ad / (double)this.horizontalNoiseResolution;
                            double ah = MathHelper.lerp(ag, z, aa);
                            double ai = MathHelper.lerp(ag, ab, ac);

                            for(int aj = 0; aj < this.horizontalNoiseResolution; ++aj) {
                                int ak = l + p * this.horizontalNoiseResolution + aj;
                                int al = ak & 15;
                                double am = (double)aj / (double)this.horizontalNoiseResolution;
                                double an = MathHelper.lerp(am, ah, ai);
                                double ao = MathHelper.clamp(an / 200.0D, -1.0D, 1.0D);

                                int at;
                                int au;
                                int ar;
                                for(ao = ao / 2.0D - ao * ao * ao / 24.0D; objectListIterator.hasNext(); ao += method_16572(at, au, ar) * 0.8D) {
                                    StructurePiece structurePiece = (StructurePiece)objectListIterator.next();
                                    BlockBox blockBox = structurePiece.getBoundingBox();
                                    at = Math.max(0, Math.max(blockBox.minX - ae, ae - blockBox.maxX));
                                    au = v - (blockBox.minY + (structurePiece instanceof PoolStructurePiece ? ((PoolStructurePiece)structurePiece).getGroundLevelDelta() : 0));
                                    ar = Math.max(0, Math.max(blockBox.minZ - ak, ak - blockBox.maxZ));
                                }

                                objectListIterator.back(objectList.size());

                                while(objectListIterator2.hasNext()) {
                                    JigsawJunction jigsawJunction = (JigsawJunction)objectListIterator2.next();
                                    int as = ae - jigsawJunction.getSourceX();
                                    at = v - jigsawJunction.getSourceGroundY();
                                    au = ak - jigsawJunction.getSourceZ();
                                    ao += method_16572(as, at, au) * 0.4D;
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

            double[][] es = ds[0];
            ds[0] = ds[1];
            ds[1] = es;
        }

    }

    private static double method_16572(int i, int j, int k) {
        int l = i + 12;
        int m = j + 12;
        int n = k + 12;
        if (l >= 0 && l < 24) {
            if (m >= 0 && m < 24) {
                return n >= 0 && n < 24 ? (double)field_16649[n * 24 * 24 + l * 24 + m] : 0.0D;
            } else {
                return 0.0D;
            }
        } else {
            return 0.0D;
        }
    }

    private static double method_16571(int i, int j, int k) {
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
