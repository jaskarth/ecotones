package supercoder79.ecotones.util;

import net.minecraft.util.math.BlockPos;

public class DataPos extends BlockPos {
    public double drainage;
    public int maxShrubHeight;
    public boolean isLikelyInvalid;

    public DataPos(int x, int y, int z) {
        super(x, y, z);
    }

    public DataPos setData(double drainage, int maxShrubHeight, boolean isLikelyInvalid) {
        this.drainage = drainage;
        this.maxShrubHeight = maxShrubHeight;
        this.isLikelyInvalid = isLikelyInvalid;
        return this;
    }
}
