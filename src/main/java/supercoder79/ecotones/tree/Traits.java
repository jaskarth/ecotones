package supercoder79.ecotones.tree;

import com.google.common.collect.ImmutableList;
import supercoder79.ecotones.tree.oak.*;

import java.util.List;

public class Traits {
    public static final List<OakTrait> OAK = ImmutableList.of(new DefaultOakTrait(), new ThinOakTrait(), new WarpedOakTrait(), new SmallerOakTrait(), new SparseOakTrait());

    public static <T> T get(List<T> list, long traits) {
        return list.get((int) Math.floorMod(traits, list.size()));
    }
}
