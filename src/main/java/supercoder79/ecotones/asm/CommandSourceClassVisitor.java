package supercoder79.ecotones.asm;

import net.fabricmc.loader.api.MappingResolver;
import org.objectweb.asm.*;

import java.io.FileOutputStream;

public final class CommandSourceClassVisitor extends ClassVisitor {
    private static final String COMMAND_SOURCE = "net.minecraft.class_2172";
    // forEachMatching(Ljava/lang/Iterable;Ljava/lang/String;Ljava/util/function/Function;Ljava/util/function/Consumer;)V
    private static final String FOREACH_MATCHING = "method_9268";
    private static final String GET_NAMESPACE = "getNamespace";
    private static final String GET_NAMESPACE_DESC = "()Ljava/lang/String;";
    private static final String FOREACH_MATCHING_DESC = "(Ljava/lang/Iterable;Ljava/lang/String;Ljava/util/function/Function;Ljava/util/function/Consumer;)V";
    private static final String IDENTIFIER = "net.minecraft.class_2960";

    private final String foreachMatching;
    private final String identifier;
    private final String getNamespace;
    private final String foreachMatchingDesc;

    public CommandSourceClassVisitor(ClassVisitor parent, String foreachMatching, String foreachMatchingDesc, String identifier, String getNamespace) {
        super(Opcodes.ASM9, parent);
        this.foreachMatching = foreachMatching;
        this.foreachMatchingDesc = foreachMatchingDesc;
        this.identifier = identifier;
        this.getNamespace = getNamespace;
    }

    static ClassTransformer createTransformer(MappingResolver mappings) {
        String commandSource = mappings.mapClassName("intermediary", COMMAND_SOURCE);
        String identifier = mappings.mapClassName("intermediary", IDENTIFIER);
        String foreachMatching = mappings.mapMethodName("intermediary", COMMAND_SOURCE, FOREACH_MATCHING, FOREACH_MATCHING_DESC);
        String getNamespace = mappings.mapMethodName("intermediary", IDENTIFIER, GET_NAMESPACE, GET_NAMESPACE_DESC);

        return (name, transformedName, bytes) -> {
            if (name.equals(commandSource)) {
                ClassReader reader = new ClassReader(bytes);
                ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);

                ClassVisitor visitor = new CommandSourceClassVisitor(writer, foreachMatching, FOREACH_MATCHING_DESC, identifier, getNamespace);
                reader.accept(visitor, 0);

                byte[] bytes1 = writer.toByteArray();

//                try (FileOutputStream fos = new FileOutputStream("CommandSource.class")) {
//                    fos.write(bytes1);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                return bytes1;
            }

            return bytes;
        };
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals(this.foreachMatching) && descriptor.equals(this.foreachMatchingDesc)) {
            return new MethodVisitor(this.api, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                @Override
                public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                    if (opcode == Opcodes.INVOKEVIRTUAL && name.equals("equals") && descriptor.equals("(Ljava/lang/Object;)Z")) {
                        // equals("minecraft")
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        // identifier2
                        super.visitVarInsn(Opcodes.ALOAD, 7);
                        // identifier2.getNamespace()
                        super.visitMethodInsn(opcode, identifier.replace(".", "/"), getNamespace, GET_NAMESPACE_DESC, false);
                        // identifier2.getNamespace();
                        // "ecotones";
                        super.visitLdcInsn("ecotones");
                        // identifier2.getNamespace().equals("ecotones");
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        // ||
                        super.visitInsn(Opcodes.IOR);

                        return;
                    }

                    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            };
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}