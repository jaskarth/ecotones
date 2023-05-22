package supercoder79.ecotones.world.structure.gen.layout.building;

import net.minecraft.block.BlockState;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.util.Vec2i;

import java.util.Random;

public abstract class Building {
    // Attachment point of building to other things (such as roads)
    private final BlockPos weldingPoint;
    private final Direction facing;
    private final BlockMirror mirror;
    private final BlockRotation rotation;

    protected Building(BlockPos weldingPoint, Direction direction) {
        this.weldingPoint = weldingPoint;

        // Set direction
        this.facing = direction;
        if (direction == null) {
            this.rotation = BlockRotation.NONE;
            this.mirror = BlockMirror.NONE;
        } else {
            switch(direction) {
                case SOUTH:
                    this.mirror = BlockMirror.LEFT_RIGHT;
                    this.rotation = BlockRotation.NONE;
                    break;
                case WEST:
                    this.mirror = BlockMirror.LEFT_RIGHT;
                    this.rotation = BlockRotation.CLOCKWISE_90;
                    break;
                case EAST:
                    this.mirror = BlockMirror.NONE;
                    this.rotation = BlockRotation.CLOCKWISE_90;
                    break;
                default:
                    this.mirror = BlockMirror.NONE;
                    this.rotation = BlockRotation.NONE;
            }
        }
    }

    protected void setBlockState(StructureWorldAccess world, BlockState state, BlockPos pos) {
        // Not replaceable
        if (!world.getBlockState(pos).isReplaceable()) {
            return;
        }

        if (this.mirror != BlockMirror.NONE) {
            state = state.mirror(this.mirror);
        }

        if (this.rotation != BlockRotation.NONE) {
            state = state.rotate(this.rotation);
        }

        world.setBlockState(transform(pos, this.mirror, this.rotation, this.weldingPoint), state, 3);
    }

    public static BlockPos transform(BlockPos pos, BlockMirror mirror, BlockRotation direction, BlockPos pivot) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        boolean bl = true;
        switch(mirror) {
            case LEFT_RIGHT:
                k = -k;
                break;
            case FRONT_BACK:
                i = -i;
                break;
            default:
                bl = false;
        }

        int l = pivot.getX();
        int m = pivot.getZ();
        switch(direction) {
            case COUNTERCLOCKWISE_90:
                return new BlockPos(l - m + k, j, l + m - i);
            case CLOCKWISE_90:
                return new BlockPos(l + m - k, j, m - l + i);
            case CLOCKWISE_180:
                return new BlockPos(l + l - i, j, m + m - k);
            default:
                return bl ? new BlockPos(i, j, k) : pos;
        }
    }

    public abstract void generate(StructureWorldAccess world, BlockPos pos, ChunkGenerator chunkGenerator, Random random);

    public BlockPos getWeldingPoint() {
        return weldingPoint;
    }

    public Direction getFacing() {
        return facing;
    }

    public BlockMirror getMirror() {
        return mirror;
    }

    public BlockRotation getRotation() {
        return rotation;
    }
}
