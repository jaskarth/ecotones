package supercoder79.ecotones.gen;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LangFileGen {
    private static final Path PATH = Paths.get("..", "src/main/resources/assets/ecotones/lang/en_us.json");
    private static final Map<Integer, String> LINES = new HashMap<>();
    private static int biomeEnd;
    private static int blockEnd;
    private static int itemblockEnd;
    private static int itemonlyEnd;
    private static int end;
    private static boolean initialized = false;

    private static final String BLOCK = "  \"block.ecotones.%%UNLOC%%\": \"%%LOC%%\",";
    private static final String ITEM = "  \"item.ecotones.%%UNLOC%%\": \"%%LOC%%\",";

    public static void init() throws IOException {
        initialized = true;

        List<String> strings = Files.readAllLines(PATH);

        end = strings.size() - 1;

        for (int i = 0; i < strings.size(); i++) {
            String string = strings.get(i);

            LINES.put(i, string);

            if (string.contains("ecotones.section.end.biomes")) {
                biomeEnd = i;
            }

            if (string.contains("ecotones.section.end.blocks")) {
                blockEnd = i;
            }

            if (string.contains("ecotones.section.end.itemblocks")) {
                itemblockEnd = i;
            }

            if (string.contains("ecotones.section.end.itemonly")) {
                itemonlyEnd = i;
            }
        }
    }

    public static void addBlock(String unloc, String localized) {
        int oldEnd = blockEnd;

        String content = BLOCK.replace("%%UNLOC%%", unloc).replace("%%LOC%%", localized);

        if (LINES.containsValue(content)) {
            return;
        }

        Map<Integer, String> updated = new HashMap<>();

        for (Map.Entry<Integer, String> entry : LINES.entrySet()) {
            updated.put(entry.getKey() >= oldEnd ? entry.getKey() + 1 : entry.getKey(), entry.getValue());
        }

        updated.put(oldEnd, content);
        DataGen.DATA.langs++;

        LINES.clear();
        LINES.putAll(updated);

        blockEnd++;
        itemblockEnd++;
        itemonlyEnd++;
        end++;
    }

    public static void addItemblock(String unloc, String localized) {
        int oldEnd = itemblockEnd;

        String content = ITEM.replace("%%UNLOC%%", unloc).replace("%%LOC%%", localized);

        if (LINES.containsValue(content)) {
            return;
        }

        Map<Integer, String> updated = new HashMap<>();

        for (Map.Entry<Integer, String> entry : LINES.entrySet()) {
            updated.put(entry.getKey() >= oldEnd ? entry.getKey() + 1 : entry.getKey(), entry.getValue());
        }

        updated.put(oldEnd, content);
        DataGen.DATA.langs++;

        LINES.clear();
        LINES.putAll(updated);

        itemblockEnd++;
        itemonlyEnd++;
        end++;
    }

    public static void addBlockItemBlock(String unloc, String localized) {
        addBlock(unloc, localized);
        addItemblock(unloc, localized);
    }

    public static void addItemOnly(String unloc, String localized) {
        int oldEnd = itemonlyEnd;

        String content = ITEM.replace("%%UNLOC%%", unloc).replace("%%LOC%%", localized);

        if (LINES.containsValue(content)) {
            return;
        }

        Map<Integer, String> updated = new HashMap<>();

        for (Map.Entry<Integer, String> entry : LINES.entrySet()) {
            updated.put(entry.getKey() >= oldEnd ? entry.getKey() + 1 : entry.getKey(), entry.getValue());
        }

        updated.put(oldEnd, content);
        DataGen.DATA.langs++;

        LINES.clear();
        LINES.putAll(updated);

        itemonlyEnd++;
        end++;
    }

    public static void commit() throws IOException {
        List<String> list = new ArrayList<>(end + 1);
        for (Map.Entry<Integer, String> entry : LINES.entrySet()) {
//            list.set(entry.getKey(), entry.getValue());
            list.add(entry.getValue());
        }

        Files.write(PATH, list, Charset.defaultCharset());
    }
}
