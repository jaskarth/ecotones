package supercoder79.ecotones.blocks;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.blocks.entity.EcotonesBlockEntities;
import supercoder79.ecotones.blocks.entity.ExcursionFunnelBlockEntity;

// Now you're thinking with asbestos!
public class ExcursionFunnelBlock extends BlockWithEntity {
    public ExcursionFunnelBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ExcursionFunnelBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, EcotonesBlockEntities.EXCURSION_FUNNEL, world.isClient ? ExcursionFunnelBlockEntity::clientTick : ExcursionFunnelBlockEntity::serverTick);
    }
}
