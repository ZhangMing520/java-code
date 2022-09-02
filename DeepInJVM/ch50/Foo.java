public class Foo {

    private int tryBlock;
    private int catchBlock;
    private int finallyBlock;
    private int methodExit;

    public void test() {
        try {
            tryBlock = 0;
        } catch (Exception e) {
            catchBlock = 1;
        } finally {
            finallyBlock = 2;
        }
        methodExit = 3;
    }
}

//Classfile /E:/gitspace/java-code/DeepInJVM/ch50/Foo.class
//Last modified 2022-9-1; size 540 bytes
//        MD5 checksum 94653c5ce6ff91ecf010e65ed02a8aec
//        Compiled from "Foo.java"
//public class Foo
//  minor version: 0
//        major version: 52
//        flags: ACC_PUBLIC, ACC_SUPER
//        Constant pool:
//        #1 = Methodref          #8.#24         // java/lang/Object."<init>":()V
//        #2 = Fieldref           #7.#25         // Foo.tryBlock:I
//        #3 = Fieldref           #7.#26         // Foo.finallyBlock:I
//        #4 = Class              #27            // java/lang/Exception
//        #5 = Fieldref           #7.#28         // Foo.catchBlock:I
//        #6 = Fieldref           #7.#29         // Foo.methodExit:I
//        #7 = Class              #30            // Foo
//        #8 = Class              #31            // java/lang/Object
//        #9 = Utf8               tryBlock
//        #10 = Utf8               I
//        #11 = Utf8               catchBlock
//        #12 = Utf8               finallyBlock
//        #13 = Utf8               methodExit
//        #14 = Utf8               <init>
//  #15 = Utf8               ()V
//          #16 = Utf8               Code
//          #17 = Utf8               LineNumberTable
//          #18 = Utf8               test
//          #19 = Utf8               StackMapTable
//          #20 = Class              #27            // java/lang/Exception
//          #21 = Class              #32            // java/lang/Throwable
//          #22 = Utf8               SourceFile
//          #23 = Utf8               Foo.java
//          #24 = NameAndType        #14:#15        // "<init>":()V
//          #25 = NameAndType        #9:#10         // tryBlock:I
//          #26 = NameAndType        #12:#10        // finallyBlock:I
//          #27 = Utf8               java/lang/Exception
//          #28 = NameAndType        #11:#10        // catchBlock:I
//          #29 = NameAndType        #13:#10        // methodExit:I
//          #30 = Utf8               Foo
//          #31 = Utf8               java/lang/Object
//          #32 = Utf8               java/lang/Throwable
//          {
//private int tryBlock;
//        descriptor: I
//        flags: ACC_PRIVATE
//
//private int catchBlock;
//        descriptor: I
//        flags: ACC_PRIVATE
//
//private int finallyBlock;
//        descriptor: I
//        flags: ACC_PRIVATE
//
//private int methodExit;
//        descriptor: I
//        flags: ACC_PRIVATE
//
//public Foo();
//        descriptor: ()V
//        flags: ACC_PUBLIC
//        Code:
//        stack=1, locals=1, args_size=1
//        0: aload_0
//        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
//        4: return
//        LineNumberTable:
//        line 1: 0
//
//public void test();
//        descriptor: ()V
//        flags: ACC_PUBLIC
//        Code:
//        stack=2, locals=3, args_size=1
//        0: aload_0
//        1: iconst_0
//        2: putfield      #2                  // Field tryBlock:I
//        5: aload_0
//        6: iconst_2
//        7: putfield      #3                  // Field finallyBlock:I
//        10: goto          35
//        13: astore_1
//        14: aload_0
//        15: iconst_1
//        16: putfield      #5                  // Field catchBlock:I
//        19: aload_0
//        20: iconst_2
//        21: putfield      #3                  // Field finallyBlock:I
//        24: goto          35
//        27: astore_2
//        28: aload_0
//        29: iconst_2
//        30: putfield      #3                  // Field finallyBlock:I
//        33: aload_2
//        34: athrow
//        35: aload_0
//        36: iconst_3
//        37: putfield      #6                  // Field methodExit:I
//        40: return
//        Exception table:
//        from    to  target type
//        0     5    13   Class java/lang/Exception
//        0     5    27   any
//        13    19    27   any
//        LineNumberTable:
//        line 10: 0
//        line 14: 5
//        line 15: 10
//        line 11: 13
//        line 12: 14
//        line 14: 19
//        line 15: 24
//        line 14: 27
//        line 16: 35
//        line 17: 40
//        StackMapTable: number_of_entries = 3
//        frame_type = 77 /* same_locals_1_stack_item */
//        stack = [ class java/lang/Exception ]
//        frame_type = 77 /* same_locals_1_stack_item */
//        stack = [ class java/lang/Throwable ]
//        frame_type = 7 /* same */
//        }
//        SourceFile: "Foo.java"
