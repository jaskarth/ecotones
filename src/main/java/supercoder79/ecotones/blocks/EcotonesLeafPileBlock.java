package supercoder79.ecotones.blocks;

import com.terraformersmc.terraform.block.LeafPileBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldView;

public class EcotonesLeafPileBlock extends LeafPileBlock {
    public EcotonesLeafPileBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState down = world.getBlockState(pos.down());
        return down.isOpaque();
    }
}
