package supercoder79.ecotones.items;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.tag.BlockTags;

import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TurpentineItem extends Item {
    public TurpentineItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();

        if (world.isClient()) {
            return ActionResult.PASS;
        }

        BlockState state = world.getBlockState(pos);

        BlockState target = target(state);

        // Only try when we have a target and it isn't the existing block
        if (target != null && player != null && !(state.isOf(target.getBlock()))) {
            NbtCompound nbt = stack.getOrCreateNbt();
            int uses = nbt.contains("U") ? nbt.getInt("U") : 4;

            // Don't reduce in creative
            if (!player.getAbilities().creativeMode) {
                if (stack.getCount() > 1) {
                    // Make stack lower as we're going to make a new stack
                    stack.decrement(1);

                    if ((uses - 1) <= 0) { // If this jar is done, insert glass bottle
                        stack.decrement(1);
                        tryInsert(world, player, new ItemStack(EcotonesItems.JAR, 1));
                    } else {
                        // insert the new stack
                        ItemStack newStack = new ItemStack(this, 1);
                        newStack.getOrCreateNbt().put("U", NbtInt.of(uses - 1));

                        tryInsert(world, player, newStack);
                    }
                } else {
                    // Single stack, simple logic

                    if ((uses - 1) <= 0) { // If this jar is done, insert glass bottle
                        stack.decrement(1);
                        tryInsert(world, player, new ItemStack(EcotonesItems.JAR, 1));
                    } else {
                        // Reduce uses count
                        nbt.put("U", NbtInt.of(uses - 1));
                    }
                }
            }

            world.setBlockState(pos, target, 0);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private static void tryInsert(World world, PlayerEntity player, ItemStack stack) {
        boolean inserted = player.getInventory().insertStack(stack);

        if (!inserted) {
            world.spawnEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), stack));
        }
    }

    private static BlockState target(BlockState state) {
        if (state.isIn(BlockTags.WOOL)) {
            return Blocks.WHITE_WOOL.getDefaultState();
        }

        if (state.isIn(BlockTags.BEDS)) {
            return Blocks.WHITE_BED.getDefaultState();
        }

        if (state.isIn(BlockTags.CANDLES)) {
            return Blocks.CANDLE.getDefaultState();
        }

        // TODO: figure out inventory swapping!
        if (state.isIn(BlockTags.SHULKER_BOXES)) {
            return Blocks.WHITE_SHULKER_BOX.getDefaultState();
        }

        // TODO: wall banners and pattern copying!
        if (state.isIn(BlockTags.BANNERS)) {
            return Blocks.WHITE_BANNER.getDefaultState();
        }

        return null;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        int uses = nbt.contains("U") ? nbt.getInt("U") : 4;

        boolean f3h = context.isAdvanced();
        switch (uses) {
            case 4 -> tooltip.add(Text.literal("Full" + (f3h ? " (4/4)" : "")).formatted(Formatting.GRAY));
            case 3 -> tooltip.add(Text.literal("Mostly full" + (f3h ? " (3/4)" : "")).formatted(Formatting.GRAY));
            case 2 -> tooltip.add(Text.literal("Half full" + (f3h ? " (2/4)" : "")).formatted(Formatting.GRAY));
            case 1 -> tooltip.add(Text.literal("Mostly empty" + (f3h ? " (1/4)" : "")).formatted(Formatting.GRAY));
        }
    }


}
