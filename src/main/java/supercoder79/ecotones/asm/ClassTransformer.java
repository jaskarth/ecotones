package supercoder79.ecotones.asm;

public interface ClassTransformer {
    byte[] transform(String name, String transformedName, byte[] bytes);
}