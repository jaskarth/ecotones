package supercoder79.updater.util;

public record LineResult(String content, boolean autoIndent, int offset) {
    public LineResult(String content) {
        this(content, true, 0);
    }

    public LineResult(String content, boolean autoIndent) {
        this(content, autoIndent, 0);
    }
}
