package com.jaskarth.ecotones.util.book;

import java.util.List;
import java.util.Random;

public interface BookGenerator {
    String generateTitle(Random random);

    List<String> generatePages(Random random);
}
