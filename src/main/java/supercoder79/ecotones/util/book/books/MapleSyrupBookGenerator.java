package supercoder79.ecotones.util.book.books;

import com.google.common.collect.ImmutableList;
import supercoder79.ecotones.util.book.BookGenerator;

import java.util.List;
import java.util.Random;

public class MapleSyrupBookGenerator implements BookGenerator {
    @Override
    public String generateTitle(Random random) {
        return "Notes on Maple Sap & Syrup";
    }

    @Override
    public List<String> generatePages(Random random) {
        return ImmutableList.of(
                "I made an interesting discovery while strolling through a maple forest once- The animals seem to love the sap coming out of the trees! I wonder if I can use the sap for myself in some way, I could really use a sweet treat...",
                "By golly I've done it! By heating up a large amount of sap in a bowl over a wood fire, I got a thick syrupy substance that is indeed very very sweet. Seems like I need 8 bits of sap to make 1 glass of maple syrup.",
                "Also! If I mix maple syrup and bread together, I get a very sweet food. I think I'll call them 'pancakes' because I made them in a pan and I really wanted to eat cake."
        );
    }
}
