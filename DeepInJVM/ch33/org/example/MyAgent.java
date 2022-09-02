package org.example;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;


/***
 *
 * 我们会截获正在加载的类，并且在每条new字节码之后插入对fireAllocationEvent方法的调用，以表示当前正在新建某个类的实例。
 *
 * javac -encoding utf-8  org/example/MyProfiler.java
 * javac -encoding utf-8 -cp ".;asm-7.0-beta.jar;asm-tree-7.0-beta.jar" org/example/MyAgent.java
 * jar cvmf manifest.txt myagent.jar org/
 *
 * javac -encoding utf-8 HelloWorld.java
 * java -javaagent:myagent.jar -cp ".;asm-7.0-beta.jar;asm-tree-7.0-beta.jar" HelloWorld
 *
 */
public class MyAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        instrumentation.addTransformer(new MyProfilerTransformer());
    }

    static class MyProfilerTransformer implements ClassFileTransformer, Opcodes {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            if (className.startsWith("java") ||
                    className.startsWith("javax") ||
                    className.startsWith("jdk") ||
                    className.startsWith("sun") ||
                    className.startsWith("com/sun") ||
                    className.startsWith("org/example")) {
                // Skip JDK classes and profiler classes
                return null;
            }

            ClassReader classReader = new ClassReader(classfileBuffer);
            ClassNode classNode = new ClassNode(ASM7);
            classReader.accept(classNode, ClassReader.SKIP_FRAMES);

            for (MethodNode methodNode : classNode.methods) {
                for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (insnNode.getOpcode() == NEW) {
                        TypeInsnNode typeInsnNode = (TypeInsnNode) insnNode;

                        InsnList insnList = new InsnList();
                        insnList.add(new LdcInsnNode(Type.getObjectType(typeInsnNode.desc)));
                        insnList.add(new MethodInsnNode(INVOKESTATIC, "org/example/MyProfiler",
                                "fireAllocationEvent", "(Ljava/lang/Class;)V", false));

                        methodNode.instructions.insert(insnNode, insnList);
                    }
                }
            }

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
    }

}
