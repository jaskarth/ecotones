package supercoder79.ecotones.world.features.tree;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.lwjgl.system.CallbackI;
import supercoder79.ecotones.world.data.DataHolder;
import supercoder79.ecotones.world.data.EcotonesData;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.FeatureHelper;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

import java.util.Random;

public class BranchedSpruceTreeFeature extends EcotonesFeature<SimpleTreeFeatureConfig> {
    public BranchedSpruceTreeFeature(Codec<SimpleTreeFeatureConfig> configCodec) {
        super(configCodec);
    }

    // TODO: impl in more places
    @Override
    public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = new Random(context.getRandom().nextLong());
        SimpleTreeFeatureConfig config = context.getConfig();

        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) {
            return false;
        }

        boolean angled = false;
        int height = 8;

        if (world.toServerWorld().getChunkManager().getChunkGenerator() instanceof DataHolder data) {
            height += (int) (data.get(EcotonesData.SOIL_QUALITY).get(pos.getX(), pos.getZ()) * 10);
        }

        height += random.nextInt(3);

        for (int i = 0; i < height; i++) {
            world.setBlockState(pos.up(i), config.woodState, 3);

            double progressUp = ((double) i) / height;

            if (i > height * 0.3 && i % 3 == 0) {

                double branchLength = scaleComplexity(progressUp, height);

                int base = (int) branchLength;

                if (random.nextDouble() < branchLength - base) {
                    base++;
                }

                double smAdd = random.nextDouble(Math.PI / 6);

                for (int j = 0; j < 4; j++) {
                    double theta = j * Math.PI / 2 + (angled ? Math.PI / 4 : 0) + smAdd + random.nextDouble(Math.PI / 8);

                    for (int k = 1; k <= base; k++) {
                        int dx = (int) (Math.cos(theta) * k);
                        int dz = (int) (Math.sin(theta) * k);
                        int dy = -(k + 1) / 2;

                        BlockPos local = pos.up(i).add(dx, dy, dz);
                        world.setBlockState(local, config.woodState, 3);

                        for (int x1 = -1; x1 <= 1; x1++) {
                            for (int z1 = -1; z1 <= 1; z1++) {
                                for (int y1 = -1; y1 <= 1; y1++) {
                                    if ((k == 1) && k != base) {
                                        if (Math.abs(x1) == 1 && Math.abs(z1) == 1 && Math.abs(y1) == 1) {
                                            continue;
                                        }
                                    } else {
                                        if (Math.abs(x1) + Math.abs(z1) + Math.abs(y1) > 1) {
                                            continue;
                                        }
                                    }

                                    BlockPos llocal = local.add(x1, z1, y1);

                                    if (world.getBlockState(llocal).isAir()) {
                                        world.setBlockState(llocal, config.leafState, 3);
                                    }
                                }
                            }
                        }
                    }
                }

                angled = !angled;
            }
        }

        int next = 2 - ((height - 1) % 3);

        for (int i = 0; i < next; i++) {
            world.setBlockState(pos.up(height + i), config.leafState, 3);
        }

        for (int x1 = -1; x1 <= 1; x1++) {
            for (int z1 = -1; z1 <= 1; z1++) {
                if (Math.abs(x1) == 1 && Math.abs(z1) == 1) {
                    continue;
                }

                world.setBlockState(pos.up(height + next).add(x1, 0, z1), config.leafState, 3);
            }
        }

        return true;
    }

    //2-2x^{3}+0.8x^{2}-x+\frac{h}{6}\left\{0\le x\le1\right\}
    private double scaleComplexity(double progress, int height) {
        return 2 - (2 * progress * progress * progress) + (0.8 * progress * progress) - progress + (height / 6.0);
    }
}
