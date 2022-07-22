/**
 * @file HelloNative.c
 * @author your name (you@domain.com)
 * @brief
 * @version 0.1
 * @date 2022-07-17
 *
 * 将本地 C 代码编译到一个动态转载库中
 *
 * Linux:
 * gcc -fPIC -I jdk/include -I jdk/include/linux -shared -o libHelloNative.so HelloNative.c
 *
 * Windows:
 * cl -I jdk/include -I jdk/include/win32 -LD HelloNative.c -FeHelloNative.dll
 *
 * Cygwin
 * gcc -mno-cygwin -D __int64="long long" -I jdk/include -I jdk/include/win32 -shared -Wl,--add-stdcall-alias -O HelloNative.dll HelloNative.c
 *
 * 已经测试过的命令：
 * gcc -fPIC -I $JAVA_HOME/include -I $JAVA_HOME/include/linux -shared -o libHelloNative.so HelloNative.c
 */
#include "HelloNative.h"
#include <stdio.h>

// 使用 C++ 实现本地方法，必须将本地方法的函数声明为 extern "C" （可以阻止C++编译器混编方法名）
// extern "C"
JNIEXPORT void JNICALL Java_HelloNative_greeting(JNIEnv *env, jclass cl)
{
    printf("Hello Native World!");
}

JNIEXPORT jstring JNICALL Java_HelloNative_getGreeting(JNIEnv *env, jclass cl)
{
    jstring jstr;
    char greeting[] = "Hello Native World!";
    // NewStringUTF 函数会从包含ASCII字符的字符数组，或者更一般的“modified UTF-8”编码的字节序列中，创建一个新的 jstring 对象
    jstr = (*env)->NewStringUTF(env, greeting);
    return jstr;
}