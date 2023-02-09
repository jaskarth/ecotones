package supercoder79.ecotones.world.gen.caves;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import org.jetbrains.annotations.Nullable;

public class EcotonesAquifer implements AquiferSampler {
    @Nullable
    @Override
    public BlockState apply(DensityFunction.NoisePos noisePos, double density) {
        return null;
    }

    @Override
    public boolean needsFluidTick() {
        return false;
    }
}
