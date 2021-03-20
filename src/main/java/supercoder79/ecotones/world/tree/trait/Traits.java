package supercoder79.ecotones.world.tree.trait;

import com.google.common.collect.ImmutableList;
import supercoder79.ecotones.world.tree.trait.oak.DefaultOakTrait;
import supercoder79.ecotones.world.tree.trait.oak.SmallerOakTrait;
import supercoder79.ecotones.world.tree.trait.oak.ThinOakTrait;
import supercoder79.ecotones.world.tree.trait.oak.WarpedOakTrait;
import supercoder79.ecotones.world.tree.trait.poplar.DefaultPoplarTrait;
import supercoder79.ecotones.world.tree.trait.poplar.ShortPoplarTrait;
import supercoder79.ecotones.world.tree.trait.poplar.TaperedPoplarTrait;
import supercoder79.ecotones.world.tree.trait.poplar.WidePoplarTrait;
import supercoder79.ecotones.world.tree.trait.smallspruce.DefaultSmallSpruceTrait;
import supercoder79.ecotones.world.tree.trait.smallspruce.ShortSmallSpruceTrait;
import supercoder79.ecotones.world.tree.trait.smallspruce.WideSmallSpruceTrait;

import java.util.List;

public class Traits {
    public static final List<OakTrait> OAK = ImmutableList.of(new DefaultOakTrait(), new ThinOakTrait(), new WarpedOakTrait(), new SmallerOakTrait());
    public static final List<SmallSpruceTrait> SMALL_SPRUCE = ImmutableList.of(new DefaultSmallSpruceTrait(), new ShortSmallSpruceTrait(), new WideSmallSpruceTrait());
    public static final List<PoplarTrait> POPLAR = ImmutableList.of(new DefaultPoplarTrait(), new ShortPoplarTrait(), new WidePoplarTrait(), new TaperedPoplarTrait());

    public static <T> T get(List<T> list, long traits) {
        return list.get((int) Math.floorMod(traits, list.size()));
    }
}
