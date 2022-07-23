package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.world.features.config.DuckweedFeatureConfig;

public class DuckweedFeature extends EcotonesFeature<DuckweedFeatureConfig> {
    public DuckweedFeature(Codec<DuckweedFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DuckweedFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        ChunkGenerator generator = context.getGenerator();
        DuckweedFeatureConfig config = context.getConfig();

        int count = config.count.get(random);
        int spread = config.spread.get(random);

        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int i = 0; i < count; i++) {
            int dx = random.nextInt(spread) - random.nextInt(spread) + pos.getX();
            int dz = random.nextInt(spread) - random.nextInt(spread) + pos.getZ();
            int y = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, dx, dz);

            mutable.set(dx, y, dz);

            if (world.getBlockState(mutable).getMaterial().isReplaceable() && world.getFluidState(mutable.down()).isIn(FluidTags.WATER)) {
                world.setBlockState(mutable, EcotonesBlocks.DUCKWEED.getDefaultState(), 3);
            }
        }

        return true;
    }
}
