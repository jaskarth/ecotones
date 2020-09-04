package supercoder79.ecotones.tree;

import com.google.common.collect.ImmutableList;
import supercoder79.ecotones.tree.oak.*;
import supercoder79.ecotones.tree.poplar.DefaultPoplarTrait;
import supercoder79.ecotones.tree.poplar.ShortPoplarTrait;
import supercoder79.ecotones.tree.poplar.TaperedPoplarTrait;
import supercoder79.ecotones.tree.poplar.WidePoplarTrait;
import supercoder79.ecotones.tree.smallspruce.DefaultSmallSpruceTrait;
import supercoder79.ecotones.tree.smallspruce.ShortSmallSpruceTrait;
import supercoder79.ecotones.tree.smallspruce.WideSmallSpruceTrait;

import java.util.List;

public class Traits {
    public static final List<OakTrait> OAK = ImmutableList.of(new DefaultOakTrait(), new ThinOakTrait(), new WarpedOakTrait(), new SmallerOakTrait(), new SparseOakTrait());
    public static final List<SmallSpruceTrait> SMALL_SPRUCE = ImmutableList.of(new DefaultSmallSpruceTrait(), new ShortSmallSpruceTrait(), new WideSmallSpruceTrait());
    public static final List<PoplarTrait> POPLAR = ImmutableList.of(new DefaultPoplarTrait(), new ShortPoplarTrait(), new WidePoplarTrait(), new TaperedPoplarTrait());

    public static <T> T get(List<T> list, long traits) {
        return list.get((int) Math.floorMod(traits, list.size()));
    }
}
