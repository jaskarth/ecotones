package supercoder79.ecotones.util.book;

import com.google.common.collect.ImmutableList;
import supercoder79.ecotones.util.ListHelper;
import supercoder79.ecotones.util.book.books.BlueberryBookGenerator;
import supercoder79.ecotones.util.book.books.MapleSyrupBookGenerator;
import supercoder79.ecotones.util.book.books.OreVeinsBookGenerator;
import supercoder79.ecotones.util.book.books.RosemaryBookGenerator;

import java.util.List;
import java.util.Random;

public final class BookList {
    private static final List<BookGenerator> BOOK_GENERATORS = ImmutableList.of(new BlueberryBookGenerator(), new RosemaryBookGenerator(), new MapleSyrupBookGenerator(), new OreVeinsBookGenerator());

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
