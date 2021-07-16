package supercoder79.ecotones.util.book;

import com.google.common.collect.ImmutableList;
import supercoder79.ecotones.util.ListHelper;

import java.util.List;
import java.util.Random;

public final class AuthorGenerator {
    private static final List<String> FRIENDS = ImmutableList.of("Aurora", "Thalia", "Emi", "Jennifer", "march", "Luna", "Lucy");

    public static String generate(Random random) {

        if (random.nextInt(4) == 0) {
            // TODO: create name generator
        }

        // Generate easter egg name
        if (random.nextInt(100) == 0) {
            return ListHelper.randomIn(FRIENDS, random);
        }

        // _/oᆺo\_
        if (random.nextInt(64) == 0) {
            return "an unknown bun";
        }

        // ^w^
        if (random.nextInt(64) == 0) {
            return "yip yip (seems to be a fox)";
        }

        // }:)
        if (random.nextInt(64) == 0) {
            return "an anonymous deer";
        }

        if (random.nextInt(8) == 0) {
            return "an unintelligible name";
        }

        return "an unknown author";
    }
}
