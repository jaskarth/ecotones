package com.jaskarth.ecotones.world.blocks.entity.types;

import net.minecraft.util.math.Direction;

public interface SmokeEmitter {
    Direction smokeFacing();

    boolean smokeEnabled();
}
