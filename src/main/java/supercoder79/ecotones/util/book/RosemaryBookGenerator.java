package supercoder79.ecotones.util.book;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Random;

public class RosemaryBookGenerator implements BookGenerator {
    @Override
    public String generateTitle(Random random) {
        return "Observations of Rosemary";
    }

    @Override
    public List<String> generatePages(Random random) {
        return ImmutableList.of(
                "While I was exploring one day, I ran into a peculiar shrub that had a rather nice smell. Going through my notes, it seems to be called \"Rosemary\" and might have medicinal properties. I'll have to investigate that further.",
                "For now though, it has a great taste too and I can't wait to put it on some food!\n\nSo it seems like my notes were right- after eating food garnished with rosemary I could feel myself healing and regaining my health, which is exciting. I must plant some",
                "near my camp to get more, it'll be a great aid on my travels.\n\nAfter a few weeks of farming, I think I finally understand how it works. It seems like it grows better in certain areas, especially those with better drained soils.",
                "It also flowers on occasion, where it makes these oh-so-pretty blue flowers. I've also found that when the plant is flowering, it spreads to nearby areas on occasion. At the same time, it doesn't seem to spread if there are already",
                "a lot of plants nearby, which will help once I eventually settle down and build a farm."
        );
    }
}
