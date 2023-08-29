package com.jaskarth.ecotones.client.render.magnifying;

import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class MagnifyingGlassRenderer {
    public abstract void render(BlockPos pos, ClientPlayerEntity player);
}
