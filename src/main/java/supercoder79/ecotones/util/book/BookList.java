package supercoder79.ecotones.util.book;

import com.google.common.collect.ImmutableList;
import supercoder79.ecotones.util.ListHelper;

import java.util.List;
import java.util.Random;

public final class BookList {
    private static final List<BookGenerator> BOOK_GENERATORS = ImmutableList.of(new BlueberryBookGenerator(), new RosemaryBookGenerator());

    public static BookGenerator get(Random random) {
        return ListHelper.randomIn(BOOK_GENERATORS, random);
    }
}
