package supercoder79.ecotones.world.features.entity;

import com.mojang.serialization.Codec;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.blocks.NestBlock;
import supercoder79.ecotones.entity.DuckEntity;
import supercoder79.ecotones.entity.EcotonesEntities;
import supercoder79.ecotones.world.features.EcotonesFeature;

public class DuckNestFeature extends EcotonesFeature<DefaultFeatureConfig> {
    public DuckNestFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();

        BlockPos waterPos = locateWater(world, pos, 16, 12);
        if (waterPos == null || !world.getBlockState(pos).isAir()) {
            return false;
        }

        // TODO: entity simulation
        if (NestBlock.isValid(world.getBlockState(pos.down()).getBlock())) {
            world.setBlockState(pos, EcotonesBlocks.NEST.getDefaultState(), 3);

            int duckCount = 1 + random.nextInt(3);

            for (int i = 0; i < duckCount; i++) {
                int x = pos.getX() + (random.nextInt(16) - random.nextInt(16));
                int z = pos.getZ() + (random.nextInt(16) - random.nextInt(16));
                int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

                DuckEntity duck = new DuckEntity(EcotonesEntities.DUCK, world.toServerWorld());
                duck.refreshPositionAndAngles(x, y, z, 0, 0);
                duck.initialize(world, world.getLocalDifficulty(pos), SpawnReason.CHUNK_GENERATION, null, null);
                world.spawnEntity(duck);
            }
        }

        return true;
    }

    private static BlockPos locateWater(BlockView world, BlockPos pos, int rangeX, int rangeY) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int minimumDist = Integer.MAX_VALUE;
        BlockPos minPos = null;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int dx = x - rangeX; dx <= x + rangeX; ++dx) {
            for(int dy = y - rangeY; dy <= y + rangeY; ++dy) {
                for(int dz = z - rangeX; dz <= z + rangeX; ++dz) {
                    mutable.set(dx, dy, dz);
                    if (world.getFluidState(mutable).isIn(FluidTags.WATER)) {
                        int ax = dx - x;
                        int ay = dy - y;
                        int az = dz - z;

                        int dist = ax * ax + ay * ay + az * az;
                        if (dist < minimumDist) {
                            minimumDist = dist;
                            minPos = mutable.toImmutable();
                        }
                    }
                }
            }
        }

        return minPos;
    }
}
