package supercoder79.ecotones.util;

import java.util.List;
import java.util.Random;

public final class ListHelper {
    public static <T> T randomIn(List<T> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }
}
