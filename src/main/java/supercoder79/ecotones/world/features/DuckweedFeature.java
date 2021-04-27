package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.world.features.config.DuckweedFeatureConfig;

import java.util.Random;

public class DuckweedFeature extends Feature<DuckweedFeatureConfig> {
    public DuckweedFeature(Codec<DuckweedFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DuckweedFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getPos();
        Random random = context.getRandom();
        ChunkGenerator generator = context.getGenerator();
        DuckweedFeatureConfig config = context.getConfig();

        int count = config.count.getValue(random);
        int spread = config.spread.getValue(random);

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
