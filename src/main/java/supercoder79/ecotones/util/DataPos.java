package supercoder79.ecotones.util;

import net.minecraft.util.math.BlockPos;
import supercoder79.ecotones.api.DrainageType;

public class DataPos extends BlockPos {
    public double drainage;
    public int maxHeight;
    public boolean isLikelyInvalid;
    public DrainageType drainageType;

    public DataPos(int x, int y, int z) {
        super(x, y, z);
    }

    public DataPos setData(double drainage, int maxHeight, boolean isLikelyInvalid) {
        this.drainage = drainage;
        this.maxHeight = maxHeight;
        this.isLikelyInvalid = isLikelyInvalid;
        return this;
    }

    public DataPos setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
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
