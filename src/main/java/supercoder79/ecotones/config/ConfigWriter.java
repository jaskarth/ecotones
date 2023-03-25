package supercoder79.ecotones.config;

import org.spongepowered.include.com.google.common.base.Charsets;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigWriter {
    public static void write(ConfigSpec spec) {
        StringBuilder builder = new StringBuilder();
        try {
            writeClass(builder, spec.getClass(), spec, 0);

            Files.writeString(Paths.get(".", "config", "ecotones.json"), builder.toString(), Charsets.UTF_8);
        } catch (IllegalAccessException | IOException e) {
            throw new RuntimeException("Exception writing config file: ", e);
        }
    }

    private static void writeClass(StringBuilder builder, Class<?> clazz, Object inst, int indent) throws IllegalAccessException {
        writeComments(builder, clazz.getAnnotationsByType(Comment.class), indent);
        writeLine(builder, "{", indent);

        Field[] fields = clazz.getFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            boolean needsComma = i != fields.length - 1;
            String comma = needsComma ? "," : "";

            writeComments(builder, f.getAnnotationsByType(Comment.class), indent + 1);
            Class<?> type = f.getType();
            if (type == int.class) {
                writeLine(builder, "\"" + f.getName() + "\": " + f.getInt(inst) + comma, indent + 1);
            } else if (type == boolean.class) {
                writeLine(builder, "\"" + f.getName() + "\": " + f.getBoolean(inst) + comma, indent + 1);
            } else if (type == double.class) {
                writeLine(builder, "\"" + f.getName() + "\": " + f.getInt(inst) + comma, indent + 1);
            } else if (type == String.class) {
                writeLine(builder, "\"" + f.getName() + "\": \"" + f.get(inst) + "\"" + comma, indent + 1);
            } else {
                if (type.isPrimitive()) {
                    throw new RuntimeException("Unsupported primitive type " + type + " for field " + f.getName());
                }

                writeLine(builder, "\"" + f.getName() + "\": ", indent + 1);
                writeClass(builder, type, f.get(inst), indent + 1);
            }

            if (needsComma) {
                builder.append("\n");
            }
        }

        writeLine(builder, "}", indent);
    }

    private static void writeLine(StringBuilder builder, String line, int indent) {
        builder.append("    ".repeat(Math.max(0, indent)));

        builder.append(line);
        builder.append("\n");
    }

    private static void writeComments(StringBuilder builder, Comment[] comments, int indent) {
        for (Comment comment : comments) {
            for (String s : comment.value().split("\n")) {
                writeLine(builder, "// " + s, indent);
            }
        }
    }
}
