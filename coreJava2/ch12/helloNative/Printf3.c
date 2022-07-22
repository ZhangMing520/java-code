#include "Printf3.h"
#include <string.h>
#include <stdlib.h>
#include <float.h>

char *find_format(const char format[])
{
    char *p;
    char *q;

    p = strchr(format, '%');
    while (p != NULL && *(p + 1) == '%')
    {
        // skip %%
        p = strchr(p + 2, '%');
    }
    // %w 之后还有 d
    if (p == NULL)
        return NULL;
    // now check that % is unique
    p++;
    q = strchr(p, '%');
    while (q != NULL && *(q + 1) == '%')
    {
        // skip %%
        q = strchr(q + 2, '%');
    }
    if (q != NULL)
        // not unique
        return NULL;
    // skip past flags
    q = p + strspn(p, " -0+#");
    // skip past field width
    q += strspn(q, "0123456789");
    if (*q == '.')
    {
        // skip past precision
        q++;
        q += strspn(q, "0123456789");
    }

    // not a floating-point format
    if (strchr("eEfFgG", *q) == NULL)
        return NULL;

    return p;
}

JNIEXPORT void JNICALL Java_Printf3_fprint(JNIEnv *env, jclass cl, jobject out, jstring format, jdouble x)
{
    const char *cformat;
    char *fmt;
    jstring str;
    jclass class_PrintWriter;
    jmethodID id_print;

    // 返回指向字符串的“modified UTF-8”编码的指针
    cformat = (*env)->GetStringUTFChars(env, format, NULL);
    // 从打印的字符串中找出格式化宽度
    // fmt = 8.2f
    fmt = find_format(cformat);
    if (fmt == NULL)
    {
        // 无格式化字符串，直接返回
        str = format;
    }
    else
    {
        char *cstr;
        // 获取格式化宽度，以便获取整个 cstr 长度
        // width = 8
        int width = atoi(fmt);
        if (width == 0)
            width = DBL_DIG + 10;
        cstr = (char *)malloc(strlen(cformat) + width);
        // format = "Amount due = %8.2f"
        sprintf(cstr, cformat, x);
        // 返回一个新的 Java 字符串对象
        str = (*env)->NewStringUTF(env, cstr);
        free(cstr);
    }
    // 释放 GetStringUTFChars 返回的指针
    (*env)->ReleaseStringUTFChars(env, format, cformat);

    // now call ps.print(str)
    class_PrintWriter = (*env)->GetObjectClass(env, out);

    id_print = (*env)->GetMethodID(env, class_PrintWriter, "print", "(Ljava/lang/String;)V");

    (*env)->CallVoidMethod(env, out, id_print, str);
}

JNIEXPORT jstring JNICALL Java_Printf3_getProperty(JNIEnv *env, jclass cl, jstring key)
{
    // 获取类
    jclass class_System = (*env)->FindClass(env, "java/lang/System");
    // 获取方法ID
    jmethodID id_getProperty = (*env)->GetStaticMethodID(env, class_System, "getProperty",
                                                         "(Ljava/lang/String;)Ljava/lang/String;");

    jobject obj_ret = (*env)->CallStaticObjectMethod(env, class_System, id_getProperty, key);

    jstring str_ret = (jstring)obj_ret;
    return str_ret;
}

JNIEXPORT jobject JNICALL Java_Printf3_getFileOutputStream(JNIEnv *env, jclass cl, jstring fileName)
{
    jclass class_FileOutputStream = (*env)->FindClass(env, "java/io/FileOutputStream");
    // 指定方法名为“<init>”，返回值为void
    jmethodID id_FileOutputStream = (*env)->GetMethodID(env, class_FileOutputStream, "<init>",
                                                        "(Ljava/lang/String;)V");

    // 创建对象
    jobject obj_stream = (*env)->NewObject(env, class_FileOutputStream, id_FileOutputStream, fileName);
    return obj_stream;
}

JNIEXPORT jdoubleArray JNICALL Java_Printf3_multiScaleFactor(JNIEnv *env, jclass cl,
                                                             jdoubleArray array_a, jdouble scaleFactor)
{
    // 返回一个指向数组起始元素的 C 指针；Double 不能是 Object
    double *a = (*env)->GetDoubleArrayElements(env, array_a, NULL);
    jsize length = (*env)->GetArrayLength(env, array_a);
    for (int i = 0; i < length; i++)
    {
        a[i] = a[i] * scaleFactor;
    }
    // 只有调用 ReleaseDoubleArrayElements 函数时，所做的改变才能保证在源数组里得到反映
    (*env)->ReleaseDoubleArrayElements(env, array_a, a, 0);
    
    return array_a;
}