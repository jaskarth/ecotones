package supercoder79.ecotones.util;

import net.minecraft.util.math.BlockPos;
import supercoder79.ecotones.api.DrainageType;

public class DataPos extends BlockPos {
    public double drainage;
    public int maxShrubHeight;
    public boolean isLikelyInvalid;
    public DrainageType drainageType;

    public DataPos(int x, int y, int z) {
        super(x, y, z);
    }

    public DataPos setData(double drainage, int maxShrubHeight, boolean isLikelyInvalid) {
        this.drainage = drainage;
        this.maxShrubHeight = maxShrubHeight;
        this.isLikelyInvalid = isLikelyInvalid;
        return this;
    }

    public DataPos setDrainageType(DrainageType type) {
        drainageType = type;
        return this;
    }

    public DataPos setLikelyInvalid(boolean isLikelyInvalid) {
        this.isLikelyInvalid = isLikelyInvalid;
        return this;
    }
}
