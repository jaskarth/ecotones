package supercoder79.ecotones.util.book;

import com.google.common.collect.ImmutableList;
import supercoder79.ecotones.util.ListHelper;
import supercoder79.ecotones.util.book.books.*;

import java.util.List;
import java.util.Random;

public final class BookList {
    private static final List<BookGenerator> BOOK_GENERATORS = ImmutableList.of(
            new BlueberryBookGenerator(),
            new RosemaryBookGenerator(),
            new MapleSyrupBookGenerator(),
            new OreVeinsBookGenerator(),
            new FertilizerBookGenerator()
    );

    public static BookGenerator get(Random random) {
        BookGenerator debug = debug();

        if (debug != null) {
            return debug;
        }

        return ListHelper.randomIn(BOOK_GENERATORS, random);
    }

    private static BookGenerator debug() {
        return null;
    }
}
