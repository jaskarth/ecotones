package com.jaskarth.ecotones.util.book.books;

import com.google.common.collect.ImmutableList;
import com.jaskarth.ecotones.util.book.BookGenerator;

import java.util.List;
import java.util.Random;

public class BlueberryBookGenerator implements BookGenerator {
    @Override
    public String generateTitle(Random random) {
        return "Notes on Blueberries";
    }

    @Override
    public List<String> generatePages(Random random) {
        return ImmutableList.of(
                "Over the past few weeks, I've run across a lot of little blue berries that are very sweet to taste. However, when I tried to grow some in a different area, the bushes grew very slowly or not at all. I must get to the bottom of that, it's very peculiar...",
                "Update 2 weeks later\nI've got it! By growing the berries in different places, I was able to find out why my berries didn't grow. It seems like blueberries only grow in very acidic soil. From a rough observation blueberry bushes in the wild grow ",
                "mostly near acidic soil, making it easy to find good spots for farms. I've found that growing blueberry bushes in acidic soil will make them grow faster and produce much more fruit. Also, turns out that you can make amazing jam with them!"
        );
    }
}
