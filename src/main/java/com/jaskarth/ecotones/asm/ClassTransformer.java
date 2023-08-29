package com.jaskarth.ecotones.asm;

public interface ClassTransformer {
    byte[] transform(String name, String transformedName, byte[] bytes);
}