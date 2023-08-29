package com.jaskarth.ecotones.world.worldgen.layers.system.layer.util;

import net.minecraft.util.math.noise.PerlinNoiseSampler;

public interface LayerRandomnessSource {
   int nextInt(int bound);

   PerlinNoiseSampler getNoiseSampler();
}
