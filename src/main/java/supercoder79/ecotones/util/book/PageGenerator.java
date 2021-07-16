package supercoder79.ecotones.util.book;

import java.util.List;
import java.util.Random;

public final class PageGenerator {
    public static List<String> generate(BookGenerator generator, Random random) {
        List<String> pages = generator.generatePages(random);

        // Ensure page size is good
        for (int i = 0; i < pages.size(); i++) {
            String page = pages.get(i);

            if (page.length() > 255) {
                throw new IllegalStateException("Page " + i + " from " + generator.getClass().getSimpleName() + " is too long! It can be at most 255 characters but it's " + page.length() + " characters!");
            }
        }

        return pages;
    }
}
