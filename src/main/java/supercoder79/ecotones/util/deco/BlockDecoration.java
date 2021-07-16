package supercoder79.ecotones.util.deco;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

public interface BlockDecoration {
    void generate(StructureWorldAccess world, BlockPos pos, Direction direction);
}
