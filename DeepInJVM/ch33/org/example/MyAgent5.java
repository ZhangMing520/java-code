package org.example;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;


/***
 * 基于类加载事件的拦截功能，实现字节码注入（bytecode instrumentation），往正在被加载的类中插入额外的字节码。
 *
 * javac -encoding utf-8 -cp ".;asm-7.0-beta.jar;asm-tree-7.0-beta.jar" org/example/MyAgent.java
 * jar cvmf manifest.txt myagent.jar org/
 *
 * javac -encoding utf-8 HelloWorld.java
 * java -javaagent:myagent.jar -cp ".;asm-7.0-beta.jar;asm-tree-7.0-beta.jar" HelloWorld
 *
 * */
public class MyAgent5 {

    public static void premain(String args, Instrumentation instrumentation) {
        instrumentation.addTransformer(new MyASMTransformer());
    }

    /***
     * 面向对象的方式注入字节码
     */
    static class MyASMTransformer implements ClassFileTransformer, Opcodes {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            ClassReader classReader = new ClassReader(classfileBuffer);
            ClassNode classNode = new ClassNode(ASM7);
            classReader.accept(classNode, ClassReader.SKIP_FRAMES);

            // methods 包含 HelloWorld 类中的构造器和方法
            for (MethodNode method : classNode.methods) {
                if ("main".equals(method.name)) {
                    InsnList insnList = new InsnList();
                    insnList.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
                    insnList.add(new LdcInsnNode("Hello, Instrumentation!"));
                    insnList.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

                    // Inserts the given instructions at the beginning of this list.
                    method.instructions.insert(insnList);
                }
            }

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            classNode.accept(classWriter);

            return classWriter.toByteArray();
        }
    }

}
