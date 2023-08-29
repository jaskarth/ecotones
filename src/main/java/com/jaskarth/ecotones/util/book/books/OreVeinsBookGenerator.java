package com.jaskarth.ecotones.util.book.books;

import com.google.common.collect.ImmutableList;
import com.jaskarth.ecotones.util.book.BookGenerator;

import java.util.List;
import java.util.Random;

public class OreVeinsBookGenerator implements BookGenerator {
    @Override
    public String generateTitle(Random random) {
        return random.nextBoolean() ? "Ore Cluster Discovery" : "Ore Vein Discovery";
    }

    @Override
    public List<String> generatePages(Random random) {
        return ImmutableList.of(
                "When I was navigating a winding cave underground in search of metals, I happened across the most curious discovery- A cavern filled entirely with ores! I came out of it with enough materials to create supplies for months. \n\nAfter exploring the",
                "surface for more clues, I found that there were huge boulders made of the same rich metal bearing materials in the general vicinity. Perhaps this discovery will allow me to locate more veins like this in the future? I must process these",
                "ores to see what secrets they contain."
        );
    }
}
