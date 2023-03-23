package supercoder79.ecotones.world.features.rock;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.world.features.EcotonesFeature;

import java.util.Random;

public class PebblesFeature extends EcotonesFeature<DefaultFeatureConfig> {
    public PebblesFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());

        BlockState down = world.getBlockState(pos.down());
        if (!(down.isOf(Blocks.GRASS_BLOCK) || down.isOf(Blocks.STONE))) {
            return false;
        }

        int count = 2 + random.nextInt(4);
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (int i = 0; i < count; i++) {
            mutable.set(mutable.getX() + random.nextInt(6) - random.nextInt(6), mutable.getY(), mutable.getZ() + random.nextInt(6) - random.nextInt(6));
            int height = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, mutable.getX(), mutable.getZ());

            mutable.setY(height);

            world.setBlockState(mutable, Blocks.STONE.getDefaultState(), 3);
            int nextChance = 2;

            for (Direction direction : Direction.values()) {
                if (random.nextInt(nextChance) == 0) {
                    world.setBlockState(mutable.offset(direction), Blocks.STONE.getDefaultState(), 3);
                     nextChance += 2;
                }
            }
        }

        return true;
    }
}
