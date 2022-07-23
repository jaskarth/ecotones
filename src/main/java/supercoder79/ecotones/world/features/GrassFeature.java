package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.world.data.DataHolder;

import java.util.Random;

public class GrassFeature extends EcotonesFeature<GrassFeatureConfig> {
    public GrassFeature(Codec<GrassFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<GrassFeatureConfig> context) {
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        GrassFeatureConfig config = context.getConfig();
        StructureWorldAccess world = context.getWorld();
        ChunkGenerator generator = context.getGenerator();

        if (!(generator instanceof DataHolder)) {
            return false;
        }

        BlockState state = config.composer.select(pos.getX(), pos.getZ(), random, (DataHolder) generator);

        BlockPos.Mutable mutable = pos.mutableCopy();

        for (int i = 0; i < config.count; i++) {
            mutable.set(pos, random.nextInt(8) - random.nextInt(8), random.nextInt(8) - random.nextInt(8), random.nextInt(8) - random.nextInt(8));

            if (world.isAir(mutable) && state.canPlaceAt(world, mutable)) {
                world.setBlockState(mutable, state, 3);
            }
        }

        return false;
    }
}
