package com.jaskarth.ecotones.util.book.books;

import com.google.common.collect.ImmutableList;
import com.jaskarth.ecotones.util.book.BookGenerator;

import java.util.List;
import java.util.Random;

public class FertilizerBookGenerator implements BookGenerator {
    @Override
    public String generateTitle(Random random) {
        return "Fertilizer Curiosities";
    }

    @Override
    public List<String> generatePages(Random random) {
        return ImmutableList.of(
                "My garden has been really struggling lately, and I think it has to do with how nutritious the soil is. I wonder if there's any way to add more more nutrients to the soil myself...\n\nAfter a good while of experimentation, I've found that",
                "a mixture of phosphorus, sulfur, and dirt can act as a crude fertilizer, to replenish the nutrients in the soil. It's not great, but it works fine enough. I need to find a way to automatically spread it, though...",
                "It seems the way forward is clear: I should make a device that can automatically spread fertilizer. This fertilizer dissolves easily in water, so I can make something that stores a pool of fertilizer and dissolves it into nearby water,",
                "which will then fertilize any crops nearby."
        );
    }
}
