package supercoder79.ecotones.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class RosemaryBlock extends EcotonesPlantBlock {
    public static final BooleanProperty FLOWERING = BooleanProperty.of("flowering");

    protected RosemaryBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FLOWERING, false));
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Swap flowering states
        if (random.nextInt(20) == 0) {
            world.setBlockState(pos, state.with(FLOWERING, !state.get(FLOWERING)));
        }

        // Try to grow
        if (random.nextInt(10) == 0 && state.get(FLOWERING)) {
            int rosemaryBlocks = 0;

            // Check for neighbors in a 9x9x9
            for(int x = -4; x <= 4; x++) {
                for(int z = -4; z <= 4; z++) {
                    for(int y = -4; y <= 4; y++) {
                        // TODO: soil quality
                        if (rosemaryBlocks > 4) {
                            break;
                        }

                        if (world.getBlockState(pos.add(x, y, z)).isOf(EcotonesBlocks.ROSEMARY)) {
                            rosemaryBlocks++;
                        }
                    }
                }
            }

            // Try to place in a 4x4x4
            if (rosemaryBlocks < 4) {
                int x = random.nextInt(5) - random.nextInt(5);
                int y = random.nextInt(5) - random.nextInt(5);
                int z = random.nextInt(5) - random.nextInt(5);
                BlockPos local = pos.add(x, y, z);

                BlockState newState = EcotonesBlocks.ROSEMARY.getDefaultState();

                if (this.canPlaceAt(newState, world, local) && world.getBlockState(local).isAir()) {
                    world.setBlockState(local, newState);
                }
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FLOWERING);
    }
}
