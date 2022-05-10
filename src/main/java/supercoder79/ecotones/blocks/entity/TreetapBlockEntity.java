package supercoder79.ecotones.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.blocks.TreetapBlock;
import supercoder79.ecotones.client.particle.EcotonesParticles;

import java.util.HashSet;
import java.util.Set;

public class TreetapBlockEntity extends BlockEntity {
    // 0 .. 8,000
    private int sapAmount = 0;
    private Direction direction;
    private boolean isValid;
    private boolean needsValidation = true;
    private long timeOffset;
    private boolean showMode = false;

    public TreetapBlockEntity(BlockPos pos, BlockState state) {
        super(EcotonesBlockEntities.TREETAP, pos, state);
        this.direction = state.get(TreetapBlock.FACING);

        // Time offset to make things more random, as well as spread out validation testing.
        // Uses Math.random because it doesn't need to be deterministic.
        this.timeOffset = (long) (Math.random() * 300);
    }

    public static void tick(World world, BlockPos pos, BlockState state, TreetapBlockEntity blockEntity) {
        if (blockEntity.isValid) {
            blockEntity.sapAmount++;

            // Used to lock the amount for display purposes
            // At this time, this was written for Blanketcon 2022. We'll see how long this code lasts!
            if (blockEntity.showMode) {
                blockEntity.sapAmount = 5000;
            }

            blockEntity.markDirty();
            blockEntity.sync();

            // Spawn drip particles every second
            if ((world.getTime() + blockEntity.timeOffset) % 40 == 0 && world.getRandom().nextInt(2) == 0) {
                ((ServerWorld)world).spawnParticles(EcotonesParticles.SAP_DRIP, pos.getX() + tapX(blockEntity.getDirection()), pos.getY() + 0.8, pos.getZ() + tapZ(blockEntity.getDirection()), 1, 0.0D, 0.0D, 0.0D, 1);
            }
        }

        if ((world.getTime() + blockEntity.timeOffset) % 300 == 0 || blockEntity.needsValidation) {
            if (blockEntity.needsValidation) {
                blockEntity.needsValidation = false;
            }

            blockEntity.checkEnvironment(world, pos, state);
        }
    }

    private void checkEnvironment(World world, BlockPos pos, BlockState state) {
        BlockPos logPos = pos.offset(state.get(TreetapBlock.FACING));

        if (!world.getBlockState(logPos).isOf(Blocks.OAK_LOG)) {
            this.isValid = false;
            return;
        }

        RecursionResults results = new RecursionResults();
        recursivelyFind(world, logPos, 0, results);

        // Initial validation check
        if (results.logs.size() >= 6 && results.leaves.size() >= 15 && results.treetaps.size() == 1) {
            int highestLogY = Integer.MIN_VALUE;

            for (BlockPos log : results.logs) {
                if (log.getY() > highestLogY) {
                    highestLogY = log.getY();
                }
            }

            // Needs 3 blocks of logs above this treetap
            if (highestLogY - pos.getY() >= 3) {
                this.isValid = true;
                return;
            }
        }

        this.isValid = false;

        // TODO: when treetaps > 1 it should slowly kill tree
        // TODO: notify other treetaps to stop producing when new treetap is placed
    }

    private void recursivelyFind(World world, BlockPos pos, int depth, RecursionResults results) {
        BlockState state = world.getBlockState(pos);

        boolean needsRecurse = false;
        if (state.isOf(Blocks.OAK_LOG)) {
            results.logs.add(pos);

            needsRecurse = true;
        }

        if (state.isOf(EcotonesBlocks.MAPLE_LEAVES) && !state.get(Properties.PERSISTENT)) {
            results.leaves.add(pos);
        }

        if (state.isOf(EcotonesBlocks.TREETAP)) {
            results.treetaps.add(pos);
        }

        results.visited.add(pos);

        if (needsRecurse && depth < 10) {
            for(int x = -1; x <= 1; x++) {
                for(int z = -1; z <= 1; z++) {
                    for(int y = -1; y <= 1; y++) {
                        BlockPos local = pos.add(x, y, z);

                        if (!results.visited.contains(local)) {
                            recursivelyFind(world, local, depth + 1, results);
                        }
                    }
                }
            }
        }
    }

    public boolean canDropSap() {
        return this.sapAmount >= 1000 && !this.showMode;
    }

    public void dropSap() {
        if (canDropSap()) {
            this.sapAmount -= 1000;

            markDirty();
            sync();
        }
    }

    public int getSapAmount() {
        return this.sapAmount;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.sapAmount = tag.getInt("SapAmt");
        this.showMode = tag.getBoolean("ShowMode");

        fromClientTag(tag);
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putInt("SapAmt", this.sapAmount);
        tag.putBoolean("ShowMode", this.showMode);
//        return tag;
    }

    public void fromClientTag(NbtCompound tag) {
        this.sapAmount = tag.getInt("sap_amount");
        this.direction = Direction.fromHorizontal(tag.getInt("direction"));
        this.isValid = tag.getBoolean("is_valid");
        this.needsValidation = tag.getBoolean("needs_validation");
        this.timeOffset = tag.getLong("time_offset");
    }

    public NbtCompound toClientTag(NbtCompound tag) {
        tag.putInt("sap_amount", this.sapAmount);
        tag.putInt("direction", this.direction.getHorizontal());
        tag.putBoolean("is_valid", this.isValid);
        tag.putBoolean("needs_validation", this.needsValidation);
        tag.putLong("time_offset", this.timeOffset);
        return tag;
    }

    private void sync() {
        ((ServerWorld)this.world).getChunkManager().markForUpdate(this.pos);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return toClientTag(new NbtCompound());
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    // TODO: these values are slightly wrong
    private static double tapX(Direction direction) {
        switch (direction) {
            case NORTH:
                return 7 / 16.0;
            case EAST:
                return 9 / 16.0;
            case SOUTH:
                return 8 / 16.0;
            case WEST:
                return 4 / 16.0;
            default:
                throw new RuntimeException("We've got a sticky situation here!");
        }
    }

    private static double tapZ(Direction direction) {
        switch (direction) {
            case NORTH:
                return 6 / 16.0;
            case EAST:
                return 8 / 16.0;
            case SOUTH:
                return 10 / 16.0;
            case WEST:
                return 8 / 16.0;
            default:
                throw new RuntimeException("We've got a sticky situation here!");
        }
    }

    private static final class RecursionResults {
        private final Set<BlockPos> visited = new HashSet<>();
        private final Set<BlockPos> logs = new HashSet<>();
        private final Set<BlockPos> leaves = new HashSet<>();
        private final Set<BlockPos> treetaps = new HashSet<>();
    }
}
