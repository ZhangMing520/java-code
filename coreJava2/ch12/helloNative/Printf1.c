/***
 *
 * 手工实现C语言的printf函数
 *
 * gcc -fPIC -I $JAVA_HOME/include -I $JAVA_HOME/include/linux -shared -o libPrintf1.so Printf1.c
 */
#include "Printf1.h"
#include <stdio.h>

JNIEXPORT jint JNICALL Java_Printf1_print(JNIEnv *env, jclass jl,
                                          jint width, jint precision, jdouble x)
{
    char fmt[30];
    jint ret;
    // sprintf 返回以format为格式argument为内容组成的结果被写入string的字节数，结束字符‘\0’不计入内
    sprintf(fmt, "%%%d.%df", width, precision);
    // printf returns the number of characters written
    ret = printf(fmt, x);
    // fflush()会强迫将缓冲区内的数据写回参数stream 指定的文件中
    fflush(stdout);
    return ret;
}