package supercoder79.ecotones.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.ecotones.blocks.BlueberryBushBlock;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import java.util.Random;

public class BlueberryBushFeature extends Feature<DefaultFeatureConfig> {
    public BlueberryBushFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getPos();
        Random random = context.getRandom();
        ChunkGenerator generator = context.getGenerator();

        BlockState state = EcotonesBlocks.BLUEBERRY_BUSH.getDefaultState().with(BlueberryBushBlock.AGE, 4);

        int extra = 0;
        if (generator instanceof EcotonesChunkGenerator) {
            extra = Math.max((int) (-((EcotonesChunkGenerator)generator).getSoilPhAt(pos.getX(), pos.getZ()) * 6), 0);
            extra += (int) ((EcotonesChunkGenerator)generator).getSoilQualityAt(pos.getX(), pos.getZ() * 6);
        }

        int count = 4 + extra;

        for (int i = 0; i < count; i++) {
            int x = random.nextInt(6) - random.nextInt(6) + pos.getX();
            int z = random.nextInt(6) - random.nextInt(6) + pos.getZ();
            int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
            BlockPos local = new BlockPos(x, y, z);

            if (state.canPlaceAt(world, local) && world.getBlockState(local).isAir()) {
                world.setBlockState(local, state, 3);
            }
        }

        return true;
    }
}
