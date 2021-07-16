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
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.world.features.FeatureHelper;

import java.util.Random;

public class GeyserPatchFeature extends Feature<DefaultFeatureConfig> {
    public GeyserPatchFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();

        for (int i = 0; i < 16; i++) {
            int dx = random.nextInt(8) - random.nextInt(8) + pos.getX();
            int dz = random.nextInt(8) - random.nextInt(8) + pos.getZ();
            int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR, dx, dz) - 1;
            BlockPos local = new BlockPos(dx, y, dz);

            if (world.getBlockState(local).isOpaque()) {
                double selection = random.nextDouble();
                if (selection < 0.15) {
                    boolean spawnInWater = true;
                    for (Direction direction : FeatureHelper.HORIZONTAL_AND_DOWN) {
                        BlockState state = world.getBlockState(local.offset(direction));

                        if (!(state.isOpaque() || state.isOf(Blocks.WATER))) {
                            spawnInWater = false;
                            break;
                        }
                    }

                    if (spawnInWater) {
                        world.setBlockState(local, Blocks.WATER.getDefaultState(), 3);
                        world.setBlockState(local.down(), EcotonesBlocks.GEYSER.getDefaultState(), 3);
                    } else {
                        world.setBlockState(local, EcotonesBlocks.GEYSER.getDefaultState(), 3);
                    }
                } else if (selection < 0.6) {
                    if (selection < 0.45) {
                        world.setBlockState(local, Blocks.STONE.getDefaultState(), 3);
                    } else {
                        world.setBlockState(local, Blocks.COBBLESTONE.getDefaultState(), 3);
                    }
                } else {
                    world.setBlockState(local, EcotonesBlocks.GEYSER.getDefaultState(), 3);
                }
            }
        }

        return false;
    }
}
