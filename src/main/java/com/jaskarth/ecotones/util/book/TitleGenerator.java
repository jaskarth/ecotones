package com.jaskarth.ecotones.util.book;

import java.util.Random;

public final class TitleGenerator {
    public static String generate(BookGenerator generator, Random random) {
        String title = generator.generateTitle(random);

        if (title.length() > 32) {
            throw new IllegalStateException("Title length from " + generator.getClass().getSimpleName() + " cannot be longer than 31 characters!");
        }

        return title;
    }
}
